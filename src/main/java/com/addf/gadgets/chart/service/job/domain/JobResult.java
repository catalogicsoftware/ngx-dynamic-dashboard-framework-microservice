package com.addf.gadgets.chart.service.job.domain;

public class JobResult {

    String jobType;
    int completed;
    int total;

    public JobResult(String jobType, int completed, int total) {
        this.jobType = jobType;
        this.completed = completed;
        this.total = total;
    }

    public String getJobType() {
        return jobType;
    }

    public void setJobType(String jobType) {
        this.jobType = jobType;
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
