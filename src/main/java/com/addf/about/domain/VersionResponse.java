package com.addf.about.domain;

public class VersionResponse {

    String version;
    public VersionResponse(String version) {
        this.version = version;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }
}
