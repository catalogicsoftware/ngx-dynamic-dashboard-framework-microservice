package com.addf.dashboard.ai.api;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin
public class DashboardServiceController {

    @Value("${version.number}")
    private String versionNumber;


    @RequestMapping("/version")
    public String getVersion() {
        return this.versionNumber;
    }
}
