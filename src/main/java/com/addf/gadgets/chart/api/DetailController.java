package com.addf.gadgets.chart.api;


import com.addf.gadgets.chart.service.job.api.Detail;

import com.addf.gadgets.chart.service.job.domain.DetailResource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin
@RequestMapping("/detail")
public class DetailController {

    @Autowired
    Detail detail;

    @RequestMapping(method = RequestMethod.GET)
    public List<DetailResource> getMetricData(@RequestParam("detailParam") String type,
                                             @RequestParam("detailMetric") String metric) {
       return detail.getByType(type, metric);
    }

    @RequestMapping(value= "/{id}", method = RequestMethod.GET)
    public List<DetailResource> getMetricData(@PathVariable("id") String id) {
        return detail.getById(id);

    }

}




