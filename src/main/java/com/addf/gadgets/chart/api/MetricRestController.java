package com.addf.gadgets.chart.api;

import com.addf.gadgets.chart.domain.MetricModel;
import com.addf.gadgets.chart.service.memory.MemoryMetric;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.List;

@RestController
@CrossOrigin
public class MetricRestController {

    @Autowired
    MemoryMetric memoryMetricService;

    @GetMapping(path = "/metric")
    public List<MetricModel> getMetricData(@RequestParam("measure") String measure) {

        switch (measure) {

            case "memory":
                return memoryMetricService.process();
            default: {
            }
        }
        return Collections.EMPTY_LIST;
    }
}




