package com.addf.gadgets.chart.service.job;

import com.addf.gadgets.chart.api.DetailController;
import com.addf.gadgets.chart.api.MetricController;
import com.addf.gadgets.chart.service.job.api.Detail;
import com.addf.gadgets.chart.service.job.domain.DetailResource;
import com.jayway.jsonpath.DocumentContext;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.hateoas.Link;
import org.springframework.stereotype.Service;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.*;

import java.util.*;

@Service
public class DetailImpl implements Detail {

    @Value("${experimental.data.file}")
    String jsonPath;

    @Override
    public List<DetailResource> getByType(String type, String metric) {

        return getDataByType(JsonFileData.readFile(jsonPath), type, metric);

    }
    @Override
    public List<DetailResource> getById(String jobid) {

        return getDataById(JsonFileData.readFile(jsonPath), jobid);

    }

    private List<DetailResource> getDataByType(DocumentContext jsonContext, String type, String metric) {
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

    private List<DetailResource> getDataById(DocumentContext jsonContext, String id) {
        List<Map<String, Object>> data = null;

        try {
            data = jsonContext.read("$.[*][?(@['Job ID'] == '" + id  + "')]", List.class);

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        if (data != null) {
            return convert(data);
        }

        return Collections.EMPTY_LIST;
    }

    private List<DetailResource> convert(List<Map<String, Object>> jsonData) {

        List<DetailResource> data = new ArrayList<>();

        for (Map<String, Object> item : jsonData) {

            DetailResource detail = new DetailResource();

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

            setHateoasLinks(detail, item);

            data.add(detail);

        }

        return data;
    }

    private void setHateoasLinks(DetailResource detail, Map<String, Object> item) {

        //the following link will mimic providing a link to the the root of the application since there is no class request mapping
        Link link = linkTo(MetricController.class).withRel("up");
        detail.add(link);

        link = linkTo(DetailController.class).withRel("detail");
        detail.add(link);

        link = linkTo(DetailController.class).slash(item.get("Job ID")).withSelfRel();
        detail.add(link);

    }

}

