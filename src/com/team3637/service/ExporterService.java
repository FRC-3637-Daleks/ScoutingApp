package com.team3637.service;

import java.util.List;

public interface ExporterService {

    boolean exportCSV(String outputFile, List<Object> data, Class objectClass);
}
