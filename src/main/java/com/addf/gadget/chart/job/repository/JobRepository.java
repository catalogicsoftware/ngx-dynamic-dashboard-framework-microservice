package com.addf.gadget.chart.job.repository;


import com.addf.gadget.chart.job.domain.Job;

import java.util.List;

public interface JobRepository {

     List<Job> getByType(String type, String metric);
     List<Job> getById(String id);
}
