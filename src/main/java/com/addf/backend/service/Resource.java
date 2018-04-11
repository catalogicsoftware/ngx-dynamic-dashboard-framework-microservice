package com.addf.backend.service;

import java.util.List;

public class Resource {
    String name;
    String value;
    List<ResourceDetail> detail;

    Resource( String name, String value, List<ResourceDetail> detail ){
        this.name = name;
        this.value = value;
        this.detail = detail;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public List<ResourceDetail> getDetail() {
        return detail;
    }

    public void setDetail(List<ResourceDetail> detail) {
        this.detail = detail;
    }




}
