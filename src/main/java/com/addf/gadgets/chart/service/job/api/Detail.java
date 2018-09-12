package com.addf.gadgets.chart.service.job.api;


import com.addf.gadgets.chart.service.job.domain.DetailResource;

import java.util.List;

public interface Detail {

     List<DetailResource> getByType(String type, String metric);
     List<DetailResource> getById(String id);
}
