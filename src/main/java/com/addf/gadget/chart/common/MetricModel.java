package com.addf.gadget.chart.common;

import java.util.List;

public class MetricModel {

    public MetricModel(String name, List<Series> series) {
        this.name = name;
        this.series = series;
    }

    String name;
    List<Series> series;

    public List<Series> getSeries() {
        return series;
    }

    public void setSeries(List<Series> series) {
        this.series = series;
    }

    public void setName(String name){
        this.name = name;
    }

    public String getName(){
        return this.name;
    }


}

