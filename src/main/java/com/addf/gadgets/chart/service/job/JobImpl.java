package com.addf.gadgets.chart.service.job;

import com.addf.gadgets.chart.service.job.api.Job;
import com.addf.gadgets.chart.service.job.domain.JobTaskDetail;
import com.jayway.jsonpath.DocumentContext;
import com.jayway.jsonpath.JsonPath;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.File;
import java.util.*;

@Service
public class JobImpl implements Job {

    @Value("${experimental.data.file}")
    String jsonPath;
    @Override
    public List<JobTaskDetail> getJobByType(String type) {

        return getData(parseJsonFile(jsonPath), type);

    }

    private List<JobTaskDetail> getData(DocumentContext jsonContext, String type) {
        List<Map<String, Object>> data = null;

        try {
            data = jsonContext.read("$.[*][?(@.Type == '" + type + "')]", List.class);

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        if (data != null) {
            return convert(data);
        }


        return Collections.EMPTY_LIST;
    }

    public static DocumentContext parseJsonFile(String jsonPath) {

        try {

            File jsonFile = new File(jsonPath);
            DocumentContext context = JsonPath.parse(jsonFile);

            return context;

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return null;

    }

    public List<JobTaskDetail> convert(List<Map<String, Object>> jsonData) {

        List<JobTaskDetail> data = new ArrayList<>();

        for (Map<String, Object> item : jsonData) {

            JobTaskDetail detail = new JobTaskDetail();

            detail.setMaster((String) item.get("Master"));
            detail.setJobName((String) item.get("Job Name"));
            detail.setType((String) item.get("Type"));
            detail.setSubType((String) item.get("Subtype"));
            detail.setJobId((String) item.get("Job ID"));
            detail.setTaskId((String) item.get("Task ID"));
            detail.setNode((String) item.get("Node"));
            detail.setDisk((String) item.get("Disk"));
            detail.setTaskStart((String) item.get("Task Start"));
            detail.setTaskEnd((String) item.get("Task End"));
            detail.setStatus((String) item.get("Status"));
            detail.setState((String) item.get("State"));
            detail.setElapsed((String) item.get("Elapsed (seconds)"));
            detail.setDataRate((String) item.get("Data Rate"));

            data.add(detail);

        }

        return data;
    }

}

