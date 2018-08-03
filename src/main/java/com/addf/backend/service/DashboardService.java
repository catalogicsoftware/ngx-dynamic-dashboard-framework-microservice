package com.addf.backend.service;

import com.google.gson.Gson;
import com.ibm.watson.developer_cloud.natural_language_classifier.v1.NaturalLanguageClassifier;
import com.ibm.watson.developer_cloud.natural_language_classifier.v1.model.Classification;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import com.addf.backend.service.connectiontester.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Properties;


@RestController
@SpringBootApplication
public class DashboardService {
    @Value("${version.number}")
    private String versionNumber;

    public static void main(String[] args) {
        SpringApplication.run(DashboardService.class, args);
    }


    @CrossOrigin
    @RequestMapping("/classify")
    public Classification processWatson(@RequestParam(value = "userid", defaultValue = "data") String userid,
                                        @RequestParam(value = "password", defaultValue = "data") String password,
                                        @RequestParam(value = "classifier_id", defaultValue = "data") String classifierId,
                                        @RequestParam(value = "data", defaultValue = "data") String data) {

        NaturalLanguageClassifier service = new NaturalLanguageClassifier();
        service.setUsernameAndPassword(userid, password);

        Classification classification = service.classify(classifierId, data).execute();
        return classification;
    }


    @CrossOrigin
    @PostMapping(path = "/connectTest")
    public Flux<ResponseObject> processConnectionTest(@RequestBody List<RequestObject> connectionTestRequest) {


        Flux<ResponseObject> portTextFluxResults = Flux.create(sink -> {
            NetworkConnectionTester.testConnection(sink,
                    connectionTestRequest);
        });

        return portTextFluxResults;
    }


    @CrossOrigin
    @RequestMapping("/version")
    public String getVersion() {

        return this.versionNumber;
    }

}



