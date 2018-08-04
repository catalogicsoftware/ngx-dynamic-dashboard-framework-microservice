package com.addf.about.api;

import com.addf.about.domain.VersionResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin
public class VersionController {

    @Value("${version.number}")
    private String versionNumber;


    @RequestMapping("/version")
    public VersionResponse getVersion() {
        return new VersionResponse(this.versionNumber + "");
    }
}
