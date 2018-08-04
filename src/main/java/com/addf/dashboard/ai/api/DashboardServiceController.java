package com.addf.dashboard.ai.api;

import com.google.gson.Gson;
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
    public VersionResponse getVersion() {
        return new VersionResponse(this.versionNumber + "");
    }
}
 class VersionResponse{

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