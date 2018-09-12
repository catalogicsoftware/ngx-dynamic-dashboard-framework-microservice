package com.addf.gadgets.chart.api;

import com.addf.gadgets.chart.domain.MetricModel;
import com.addf.gadgets.chart.service.metric.api.Metric;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.List;

@RestController
@CrossOrigin
public class MetricController {

    @Autowired
    @Qualifier("memory")
    Metric memoryMetricImpl;

    @Autowired
    @Qualifier("job")
    Metric jobMetricImpl;

    @GetMapping(path = "/metric")
    public HttpEntity<List<MetricModel>> getMetricData(@RequestParam("measure") String measure) {

        switch (measure) {

            case "memory":
                return new ResponseEntity<>(memoryMetricImpl.process(), HttpStatus.OK);
            case "job":
                return new ResponseEntity<>(jobMetricImpl.process(), HttpStatus.OK);
            default: {
            }
        }
        return new ResponseEntity<>(Collections.EMPTY_LIST, HttpStatus.NO_CONTENT);
    }

}




