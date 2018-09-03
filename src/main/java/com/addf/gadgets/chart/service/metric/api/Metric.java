package com.addf.gadgets.chart.service.metric.api;

import com.addf.gadgets.chart.domain.MetricModel;

import java.util.List;

public interface Metric {

    List<MetricModel> process();
}
