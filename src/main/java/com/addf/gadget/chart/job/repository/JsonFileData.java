package com.addf.gadget.chart.job.repository;

import com.jayway.jsonpath.DocumentContext;
import com.jayway.jsonpath.JsonPath;

import java.io.File;

public class JsonFileData {
    private static DocumentContext dataFile;

    private JsonFileData() {
    }

    public static DocumentContext readFile(String filePath) {
        if (dataFile == null) {
            File jsonFile = new File(filePath);
            try{
                dataFile = JsonPath.parse(jsonFile);
            }catch (Exception e){
                System.out.println(e.getMessage());
            }
        }
        return dataFile;

    }

}
