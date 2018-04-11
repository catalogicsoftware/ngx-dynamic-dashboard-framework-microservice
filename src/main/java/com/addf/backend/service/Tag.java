package com.addf.backend.service;

public class Tag {

    String facet;
    String name;

    Tag(String facet, String name){
        this.facet = facet;
        this.name = name;
    }

    public String getFacet() {
        return facet;
    }

    public void setFacet(String facet) {
        this.facet = facet;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

}
