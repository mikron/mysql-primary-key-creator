package com.vachoompo.mysqlkeycreator;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVPrinter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Exporter {

    private static final Logger LOGGER = LoggerFactory.getLogger(Exporter.class);

    public static void exportCsv() {
        LOGGER.info("Starting exporting process");
        DBHelper dbHelper = new DBHelper();
        List<PrimaryKeyRec> primaryKeyRecList = dbHelper.getPkeyRows("select t.table_schema schemaName,t.table_name tableName\n" +
                "from information_schema.tables t \n" +
                "    inner join information_schema .columns c  \n" +
                "        on t.table_schema=c.table_schema and t.table_name=c.table_name \n" +
                "where t.table_schema not in ('information_schema', 'sys', 'performance_schema', 'mysql')\n" +
                "group by t.table_schema,t.table_name   \n" +
                "having sum(if(column_key in ('PRI','UNI'), 1,0)) =0\n" +
                "order by 2");
        LOGGER.info("Generating list of missing primary keys csv file");
        FileWriter fileWriter = null;
        CSVPrinter csvFilePrinter = null;
        //Create the CSVFormat object with "\n" as a record delimiter
        CSVFormat csvFileFormat = CSVFormat.DEFAULT.withRecordSeparator(CSVUtils.NEW_LINE_SEPARATOR);

        try {
            //initialize FileWriter object
            fileWriter = new FileWriter(PathUtils.getJarPath() + File.separator  + "pkeylist.csv");
            //initialize CSVPrinter object
            csvFilePrinter = new CSVPrinter(fileWriter, csvFileFormat);
            //Create CSV file header
            csvFilePrinter.printRecord(CSVUtils.FILE_HEADER);
            //Write a new student object list to the CSV file
            for (PrimaryKeyRec pKey : primaryKeyRecList) {
                List<String> csvRecords = new ArrayList<>();
                csvRecords.add(pKey.getSchemaName());
                csvRecords.add(pKey.getTableName());
                csvRecords.add(pKey.getKeyName());
                csvRecords.add(pKey.getColNamesAsString());
                csvRecords.add(String.valueOf(pKey.isCreateIdCol()));
                csvFilePrinter.printRecord(csvRecords);
            }

            LOGGER.info("CSV file was created successfully !!!");

        } catch (Exception e) {
            LOGGER.error("Error in CsvFileWriter !!!");
            e.printStackTrace();
        } finally {
            try {
                fileWriter.flush();
                fileWriter.close();
                csvFilePrinter.close();
            } catch (IOException e) {
                LOGGER.error("Error while flushing/closing fileWriter/csvPrinter !!!");
                e.printStackTrace();
            }
        }

    }

}
