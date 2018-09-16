package com.addf.gadget.chart.job.service;

import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;

import java.net.MalformedURLException;
import java.nio.file.FileSystems;
import java.nio.file.Path;

@Service
public class FileStorage {

    public Resource loadFileAsResource(String fileName) {

        try {

            Path filePath = FileSystems.getDefault().getPath("logs", "data.log");

            Resource resource = new UrlResource(filePath.toUri());
            if (resource.exists()) {
                return resource;
            } else {
                System.out.println("Throw file not found " + fileName);
            }
        } catch (MalformedURLException ex) {
            System.out.println("Throw file not found " + fileName);
        }
        return null;
    }
}
