package com.addf.gadgets.chart.service.job;

import com.addf.gadgets.chart.domain.MetricModel;
import com.addf.gadgets.chart.domain.Series;
import com.addf.gadgets.chart.service.metric.api.Metric;
import com.addf.gadgets.chart.service.job.domain.JobResult;
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

        List<JobResult> jobResults = getData(JobImpl.parseJsonFile(jsonPath));

        for(JobResult result :  jobResults){

            metricData.add(new MetricModel(result.getJobType(), getSeriesList(result)));
        }

        return metricData;

    }

    private List<JobResult> getData(DocumentContext jsonContext) {

        //"$..Type" represents the jobTypes within the json document

        List<JobResult> jobStats = new ArrayList<JobResult>();

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

                jobStats.add(new JobResult(distinctJobTypeValue, success.size(), total.size()));
            }

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        return jobStats;
    }

    private List<Series> getSeriesList(JobResult result){

        Series a = new Series();
        a.setName("total");
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
