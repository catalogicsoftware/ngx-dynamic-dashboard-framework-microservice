package com.addf.backend.service;

import java.util.List;

public class ResourceDetail {

    String id;
    String name;
    String type;
    List<Tag> tags;

    ResourceDetail( String id, String name, String type,  List<Tag> tags){
        this.id = id;
        this.name = name;
        this.tags = tags;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public List<Tag> getTags() {
        return tags;
    }

    public void setTags(List<Tag> tags) {
        this.tags = tags;
    }



}
