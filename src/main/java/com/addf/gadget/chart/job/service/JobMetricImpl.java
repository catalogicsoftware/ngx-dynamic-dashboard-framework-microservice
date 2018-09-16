package com.addf.gadget.chart.job.service;

import com.addf.gadget.chart.common.MetricModel;
import com.addf.gadget.chart.common.Series;
import com.addf.gadget.chart.common.Metric;
import com.addf.gadget.chart.job.domain.JobAggregate;
import com.addf.gadget.chart.job.repository.JsonFileData;
import com.jayway.jsonpath.DocumentContext;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import java.util.*;

@Service("job")
public class JobMetricImpl implements Metric {
    @Value("${experimental.data.file}")
    String jsonPath;

    @Override
    public List<MetricModel> process() {

        List<MetricModel> metricData;

        metricData = new ArrayList<>();

        List<JobAggregate> jobAggregate = getData(JsonFileData.readFile(jsonPath));

        for(JobAggregate result : jobAggregate){

            metricData.add(new MetricModel(result.getType(), getSeriesList(result)));
        }

        return metricData;

    }

    private List<JobAggregate> getData(DocumentContext jsonContext) {

        List<JobAggregate> jobStats = new ArrayList<JobAggregate>();

        try {
            List<String> data = jsonContext.read("$..Type");
            Set<String> distinctJobTypes = new HashSet<String>();

            for (int x = 0; x < data.size(); x++) {
                distinctJobTypes.add(data.get(x));
            }

            Iterator setIterator = distinctJobTypes.iterator();
            while (setIterator.hasNext()) {

                String distinctJobTypeValue = (String) setIterator.next();

                List<String> total = jsonContext.read("$.[*][?(@.Type == '" + distinctJobTypeValue + "')]");
                List<String> success = jsonContext.read("$.[*][?(@.Type == '" + distinctJobTypeValue + "' && @.Status == 'Completed' )]");

                jobStats.add(new JobAggregate(distinctJobTypeValue, success.size(), total.size()));
            }

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        return jobStats;
    }

    private List<Series> getSeriesList(JobAggregate result){

        Series a = new Series();
        a.setName("success");
        a.setValue(Double.valueOf(result.getTotal()));

        Series b = new Series();
        b.setName("failed");
        b.setValue(Double.valueOf(result.getTotal()) - Double.valueOf(result.getCompleted()));


        List<Series> chartVal = new ArrayList<>();
        chartVal.add(a);
        chartVal.add(b);

        return chartVal;

    }


}
