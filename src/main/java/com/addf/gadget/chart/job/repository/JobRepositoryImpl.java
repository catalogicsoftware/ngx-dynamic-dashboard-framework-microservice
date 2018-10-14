package com.addf.gadget.chart.job.repository;

import com.addf.gadget.chart.MetricController;
import com.addf.gadget.chart.job.JobController;
import com.addf.gadget.chart.job.domain.Job;
import com.jayway.jsonpath.DocumentContext;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.hateoas.Link;
import org.springframework.stereotype.Repository;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.*;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.util.*;

@Repository
public class JobRepositoryImpl implements JobRepository {

    @Value("${experimental.data.file}")
    String jsonPath;

    @Override
    public List<Job> getByType(String type, String metric) {

        return getDataByType(JsonFileData.readFile(jsonPath), type, metric);

    }

    @Override
    public List<Job> getById(String id) {

        return getDataById(JsonFileData.readFile(jsonPath), id);

    }

    private List<Job> getDataByType(DocumentContext jsonContext, String type, String metric) {
        List<Map<String, Object>> data = null;

        String filter = "'";

        switch (metric) {
            case "all":
                break;
            case "success":
                filter = "' && @.Status == 'Completed'";
                break;
            case "failed":
                filter = "' && @.Status != 'Completed'";
                break;
            default:
        }

        try {
            data = jsonContext.read("$.[*][?(@.Type == '" + type + filter + ")]", List.class);

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        if (data != null) {
            return convert(data);
        }

        return Collections.EMPTY_LIST;
    }

    private List<Job> getDataById(DocumentContext jsonContext, String id) {
        List<Map<String, Object>> data = null;

        try {
            data = jsonContext.read("$.[*][?(@['Job ID'] == '" + id + "')]", List.class);

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        if (data != null) {
            return convert(data);
        }

        return Collections.EMPTY_LIST;
    }

    private List<Job> convert(List<Map<String, Object>> jsonData) {

        List<Job> data = new ArrayList<>();

        for (Map<String, Object> item : jsonData) {

            Job job = new Job();

            job.setMaster((String) item.get("Master"));
            job.setJobName((String) item.get("Job Name"));
            job.setType((String) item.get("Type"));
            job.setSubType((String) item.get("Subtype"));
            job.setJobId((String) item.get("Job ID"));
            job.setTaskId((String) item.get("Task ID"));
            job.setNode((String) item.get("Node"));
            job.setDisk((String) item.get("Disk"));
            job.setTaskStart((String) item.get("Task Start"));
            job.setTaskEnd((String) item.get("Task End"));
            job.setStatus((String) item.get("Status"));
            job.setState((String) item.get("State"));
            job.setElapsed((String) item.get("Elapsed (seconds)"));
            job.setDataRate((String) item.get("Data Rate"));
            job.setData((String) item.get("Data"));
            job.setFiles((String) item.get("#File"));
            job.setFailedFile((String) item.get("#Failed Files"));
            job.setMedia((String) item.get("Media"));
            job.setSecondary((String) item.get("Secondary"));
            job.setSecondaryVolume((String) item.get("Secondary Volume"));
            job.setRetention((String) item.get("Retention"));
            job.setExpired((String) item.get("Expired"));
            job.setBase((String) item.get("Base"));
            job.setTransport((String) item.get("Transport"));
            job.setLog((String) item.get("Job Log"));

            setHateoasLinks(job, item);

            data.add(job);

        }

        try (BufferedWriter file = new BufferedWriter(new FileWriter("streameddata.csv"))) {

            for (Job j : data) {

                file.write(j.toString());
            }

        } catch (Exception e) {


            System.out.println("Problem writing file! " + e.getLocalizedMessage() );
        }


        return data;
    }

    private void setHateoasLinks(Job job, Map<String, Object> item) {

        //the following link will mimic providing a link to the the root of the application since there is no class request mapping
        Link link = linkTo(MetricController.class).withRel("up");
        job.add(link);

        link = linkTo(JobController.class).withRel("job");
        job.add(link);

        link = linkTo(JobController.class).slash(item.get("Job ID")).withSelfRel();
        job.add(link);

    }

}

