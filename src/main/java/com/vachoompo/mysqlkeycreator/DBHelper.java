package com.vachoompo.mysqlkeycreator;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

public class DBHelper {

    private static final Logger LOGGER = LoggerFactory.getLogger(DBHelper.class);

    private Properties getDbProperties() {
        LOGGER.info("Getting database properties");
        try {
            Properties properties = new Properties();
            File file = new File(PathUtils.getDbPropertiesPath());
            FileInputStream fileInput = new FileInputStream(file);
            properties.load(fileInput);
            fileInput.close();
            return properties;
        } catch (FileNotFoundException e) {
            LOGGER.error("FileNotFoundException: Please provide db.properties file in classpath");
            throw new RuntimeException("FileNotFoundException: Please provide db.properties file in classpath", e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private Connection getConnection(Properties info) {
        LOGGER.info("Getting database connection");
        String url = info.getProperty("url");
        try {
            Class.forName("com.mysql.jdbc.Driver");
            return DriverManager.getConnection(url, info);
        } catch (SQLException e) {
            LOGGER.error("Can not get database connection");
            throw new RuntimeException("Can not get database connection", e);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    private boolean hasColumn(ResultSet rs, String columnName) throws SQLException {
        ResultSetMetaData rsmd = rs.getMetaData();
        int columns = rsmd.getColumnCount();
        for (int x = 1; x <= columns; x++) {
            if (columnName.equals(rsmd.getColumnName(x))) {
                return true;
            }
        }
        return false;
    }

    public List<PrimaryKeyRec> getPkeyRows(String sqlText) {
        LOGGER.info("Getting the list of the missing primary keys");
        Connection conn = null;
        Properties info = null;
        try {
            info = getDbProperties();
            conn = getConnection(info);
            Statement stmt = conn.createStatement();

            List<PrimaryKeyRec> result = new ArrayList<>();
            ResultSet rs = stmt.executeQuery(sqlText);
            while (rs.next()) {
                PrimaryKeyRec primaryKeyRec = new PrimaryKeyRec(rs.getString("schemaName"),
                        rs.getString("tableName"));
                if (hasColumn(rs, "pKeyName")) {
                    primaryKeyRec.setKeyName(rs.getString("pKeyName"));
                } else {
                    primaryKeyRec.setKeyName("PK_" + primaryKeyRec.getTableName() + "_ID");
                }
                if (hasColumn(rs, "pKeyCols")) {
                    primaryKeyRec.setColNames(rs.getString("pKeyCols"));
                    primaryKeyRec.setCreateIdCol(false);
                } else {
                    primaryKeyRec.setColNames("ID");
                    primaryKeyRec.setCreateIdCol(true);
                }

                result.add(primaryKeyRec);
            }
            rs.close();
            return result;
        } catch (SQLException e) {
            throw new RuntimeException("Wrong SQl", e);
        } finally {
            if (conn != null) {
                try {
                    conn.close();
                } catch (Exception e) {

                }
            }
        }
    }

}
