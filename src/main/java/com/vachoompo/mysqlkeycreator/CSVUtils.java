package com.vachoompo.mysqlkeycreator;

public class CSVUtils {

    //Delimiter used in CSV file
    public static final String NEW_LINE_SEPARATOR = "\n";
    //CSV file header
    public static final String[] FILE_HEADER = {"Schema name", "Table name", "Primary key name", "Primary key columns", "Create primary key column"};
    //CSV file header mapping
    public static final String[] FILE_HEADER_MAPPING = {"schemaName", "tableName", "keyName", "keyColName", "createIdCol"};

}
