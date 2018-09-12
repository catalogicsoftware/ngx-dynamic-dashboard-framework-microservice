package com.addf.gadgets.chart.api;


import com.addf.gadgets.chart.service.job.api.Job;

import com.addf.gadgets.chart.service.job.domain.JobTaskDetail;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin
@RequestMapping("/detail")
public class JobController {

    @Autowired
    Job job;
    @RequestMapping(method = RequestMethod.GET)
    public List<JobTaskDetail> getMetricData(@RequestParam("detailParam") String jobType,
                                             @RequestParam("detailMetric") String metric) {

       return job.getJobByType(jobType, metric);

    }

}




