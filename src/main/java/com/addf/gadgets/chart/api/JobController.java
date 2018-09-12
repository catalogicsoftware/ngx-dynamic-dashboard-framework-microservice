package com.addf.gadgets.chart.api;


import com.addf.gadgets.chart.service.job.api.Job;

import com.addf.gadgets.chart.service.job.domain.JobTaskDetail;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@CrossOrigin
public class JobController {

    @Autowired
    Job job;

    @GetMapping(path = "/detail")
    public List<JobTaskDetail> getMetricData(@RequestParam("detailParam") String jobType,
                                             @RequestParam("detailMetric") String metric) {

       return job.getJobByType(jobType, metric);

    }

}




