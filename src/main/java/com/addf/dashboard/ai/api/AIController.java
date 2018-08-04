package com.addf.dashboard.ai.api;

import com.ibm.watson.developer_cloud.natural_language_classifier.v1.NaturalLanguageClassifier;
import com.ibm.watson.developer_cloud.natural_language_classifier.v1.model.Classification;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


@RestController
@CrossOrigin
public class AIController {

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



}
