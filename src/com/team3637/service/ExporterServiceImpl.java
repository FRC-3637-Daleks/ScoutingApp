package com.team3637.service;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVPrinter;

import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

public class ExporterServiceImpl implements ExporterService {

    @Override
    public boolean exportCSV(String outputFile, List<Object> data, Class objectClass) {
        FileWriter fileWriter = null;
        CSVPrinter csvFilePrinter = null;
        boolean success = true;
        try {
            fileWriter = new FileWriter(outputFile);
            csvFilePrinter = new CSVPrinter(fileWriter, CSVFormat.DEFAULT.withRecordSeparator("\n"));
            for(Object object : data) {
                List<Object> line = new ArrayList<>();
                for(Field field : objectClass.getDeclaredFields()) {
                    field.setAccessible(true);
                    Object value = field.get(object);
                    data.add(value);
                }
                csvFilePrinter.printRecord(line);
            }
        } catch (IOException | IllegalAccessException e) {
            e.printStackTrace();
            success = false;
        } finally {
            try {
                if (fileWriter != null) {
                    fileWriter.flush();
                    fileWriter.close();
                }
                if (csvFilePrinter != null) {
                    csvFilePrinter.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
                success = false;
            }
        }
        return success;
    }
}
