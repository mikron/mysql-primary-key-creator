package com.vachoompo.mysqlkeycreator;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;

public class Importer {

    private static final Logger LOGGER = LoggerFactory.getLogger(Importer.class);

    public static void parseCsv() {
        LOGGER.info("Parsing csv");
        Reader in = null;
        try {
            in = new FileReader(PathUtils.getJarPath() + File.separator + "pkeylist.csv");

            Iterable<CSVRecord> records = CSVFormat.DEFAULT
                    .withRecordSeparator(CSVUtils.NEW_LINE_SEPARATOR)
                    .withHeader(CSVUtils.FILE_HEADER_MAPPING)
                    .parse(in);
            for (CSVRecord record : records) {

                PrimaryKeyRec primaryKeyRec = new PrimaryKeyRec();
                primaryKeyRec.setSchemaName(record.get("schemaName"));
                primaryKeyRec.setTableName(record.get("tableName"));
                primaryKeyRec.setKeyName(record.get("keyName"));
                primaryKeyRec.setColNames(record.get("keyColName"));
                primaryKeyRec.setCreateIdCol(Boolean.valueOf(record.get("createIdCol")));

                LOGGER.info(primaryKeyRec.mySQLCreateSQL());
            }
        } catch (FileNotFoundException e) {
            LOGGER.error("Can not parse csv");
            throw new RuntimeException("Can not parse csv", e);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
