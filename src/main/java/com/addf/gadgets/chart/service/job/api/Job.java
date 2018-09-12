package com.addf.gadgets.chart.service.job.api;


import com.addf.gadgets.chart.service.job.domain.JobTaskDetail;

import java.util.List;

public interface Job {

     List<JobTaskDetail> getJobByType(String type, String metric);
}
