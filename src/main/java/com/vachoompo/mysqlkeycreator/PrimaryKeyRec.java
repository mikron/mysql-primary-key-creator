package com.vachoompo.mysqlkeycreator;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class PrimaryKeyRec {

    private String schemaName;
    private String tableName;
    private String keyName;
    private List<String> colNames;

    private boolean createIdCol;

    public PrimaryKeyRec() {
    }

    public PrimaryKeyRec(String schemaName, String tableName) {
        this.schemaName = schemaName;
        this.tableName = tableName;
    }

    public String getSchemaName() {
        return schemaName;
    }

    public void setSchemaName(String schemaName) {
        this.schemaName = schemaName;
    }

    public String getTableName() {
        return tableName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    public String getKeyName() {
        return keyName;
    }

    public void setKeyName(String keyName) {
        this.keyName = keyName;
    }

    public List<String> getColNames() {
        return colNames;
    }

    public String getColNamesAsString() {
        return getColNames().stream().collect(Collectors.joining(","));
    }

    public void setColNames(List<String> colNames) {
        this.colNames = colNames;
    }

    public void setColNames(String  colNames) {
        this.colNames = Arrays.asList(colNames.split(","));
    }

    public boolean isCreateIdCol() {
        return createIdCol;
    }

    public void setCreateIdCol(boolean createIdCol) {
        this.createIdCol = createIdCol;
    }

    public String mySQLCreateSQL() {
        // ALTER TABLE SCHEMA_NAME.TABLE_NAME ADD PRIMARY KEY(LIST_OF_COLUMNS)
        return new StringBuilder("alter table ")
                .append(getSchemaName())
                .append(".")
                .append(getTableName())
                .append(" add primary key (")
                .append(getColNamesAsString())
                .append(")")
                .toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;

        if (o == null || getClass() != o.getClass()) return false;

        PrimaryKeyRec primaryKeyRec = (PrimaryKeyRec) o;

        return new org.apache.commons.lang3.builder.EqualsBuilder()
                .append(schemaName, primaryKeyRec.schemaName)
                .append(tableName, primaryKeyRec.tableName)
                .append(keyName, primaryKeyRec.keyName)
                .append(colNames, primaryKeyRec.colNames)
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new org.apache.commons.lang3.builder.HashCodeBuilder(17, 37)
                .append(schemaName)
                .append(tableName)
                .append(keyName)
                .append(colNames)
                .toHashCode();
    }

    @Override
    public String toString() {
        return new org.apache.commons.lang3.builder.ToStringBuilder(this)
                .append("schemaName", schemaName)
                .append("tableName", tableName)
                .append("keyName", keyName)
                .append("colNames", colNames)
                .toString();
    }
}
