package com.addf.gadget.chart.job.domain;

public class JobAggregate {

    private String type;
    private int completed;
    private int total;

    public JobAggregate(String type, int completed, int total) {
        this.type = type;
        this.completed = completed;
        this.total = total;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int getCompleted() {
        return completed;
    }

    public void setCompleted(int completed) {
        this.completed = completed;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }
}
