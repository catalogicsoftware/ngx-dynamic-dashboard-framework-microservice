package com.addf.backend.service;

import com.google.gson.Gson;
import com.ibm.watson.developer_cloud.natural_language_classifier.v1.NaturalLanguageClassifier;
import com.ibm.watson.developer_cloud.natural_language_classifier.v1.model.Classification;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketAddress;
import java.util.ArrayList;
import java.util.List;


@RestController
@SpringBootApplication
public class DashboardService {

    public static void main(String[] args) {
        SpringApplication.run(DashboardService.class, args);
    }


    @CrossOrigin
    @RequestMapping("/classify")
    Classification processWatson(@RequestParam(value = "userid", defaultValue = "data") String userid,
                                 @RequestParam(value = "password", defaultValue = "data") String password,
                                 @RequestParam(value = "classifier_id", defaultValue = "data") String classifierId,
                                 @RequestParam(value = "data", defaultValue = "data") String data) {

        NaturalLanguageClassifier service = new NaturalLanguageClassifier();
        service.setUsernameAndPassword(userid, password);

        Classification classification = service.classify(classifierId, data).execute();
        return classification;
    }


    @CrossOrigin
    @RequestMapping("/connectTest")
    ResponseObject processConnectionTest(@RequestParam(value = "host") String host,
                                         @RequestParam(value = "port") int port) {

        // getDonutDetails();
        String result = "";
        try (Socket socket = new Socket();) {

            SocketAddress socketAddress = new InetSocketAddress(host, port);
            socket.connect(socketAddress, 10000);
            result = "successful";


        } catch (Exception e) {
            result = e.toString();
        }

        return new ResponseObject(result);
    }


    @CrossOrigin
    @RequestMapping("/donutDetail")
    ResponseObject getDonutDetails() {


        Tag tag = new Tag("VM", "staging");
        List<Tag> tags = new ArrayList<>();
        ResourceDetail resourceDetail = new ResourceDetail("0", "vm 1", "vm",  tags);
        List<ResourceDetail> resourceDetailList = new ArrayList<>();
        tags.add(tag);
        resourceDetailList.add(resourceDetail);


        tag = new Tag("Site A", "department 1");
        tags = new ArrayList<>();
        resourceDetail = new ResourceDetail("1", "vm 1", "vm", tags);
        tags.add(tag);
        resourceDetailList.add(resourceDetail);

        tag = new Tag("Site A", "department 1");
        tags = new ArrayList<>();
        resourceDetail = new ResourceDetail("2", "vm 2", "vm", tags);
        tags.add(tag);
        resourceDetailList.add(resourceDetail);

        tag = new Tag("Site A", "department 1");
        tags = new ArrayList<>();
        resourceDetail = new ResourceDetail("3", "vm 3", "vm", tags);
        tags.add(tag);
        resourceDetailList.add(resourceDetail);

        tag = new Tag("Site B", "department 2");
        tags = new ArrayList<>();
        resourceDetail = new ResourceDetail("4", "vm 4", "vm", tags);
        tags.add(tag);
        resourceDetailList.add(resourceDetail);

        tag = new Tag("Site B", "department 2");
        tags = new ArrayList<>();
        resourceDetail = new ResourceDetail("5", "vm 5", "vm", tags);
        tags.add(tag);
        resourceDetailList.add(resourceDetail);

        tag = new Tag("Site B", "department 2");
        tags = new ArrayList<>();
        resourceDetail = new ResourceDetail("6", "vm 6", "vm", tags);
        tags.add(tag);
        resourceDetailList.add(resourceDetail);


        Resource resource = new Resource("compliant", "" + resourceDetailList.size(),resourceDetailList );
        List<Resource> resourceList = new ArrayList<>();
        resourceList.add(resource);



        tag = new Tag("Site A", "department 2");
        tags = new ArrayList<>();
        resourceDetail = new ResourceDetail("7", "vm 7", "vm", tags);
        tags.add(tag);
        resourceDetailList.add(resourceDetail);

        tag = new Tag("Site A", "department 2");
        tags = new ArrayList<>();
        resourceDetail = new ResourceDetail("8", "vm 8", "vm", tags);
        tags.add(tag);
        resourceDetailList.add(resourceDetail);

        tag = new Tag("Site A", "department 1");
        tags = new ArrayList<>();
        resourceDetail = new ResourceDetail("9", "vm 9", "vm", tags);
        tags.add(tag);
        resourceDetailList.add(resourceDetail);

        tag = new Tag("Site C", "department 1");
        tags = new ArrayList<>();
        resourceDetail = new ResourceDetail("10", "vm 10", "vm", tags);
        tags.add(tag);
        resourceDetailList.add(resourceDetail);



        resource = new Resource("staging", "" + resourceDetailList.size(),resourceDetailList );
        resourceList = new ArrayList<>();
        resourceList.add(resource);




        tag = new Tag("Site A", "department 2");
        tags = new ArrayList<>();
        resourceDetail = new ResourceDetail("11", "vm 11", "vm", tags);
        tags.add(tag);
        resourceDetailList.add(resourceDetail);

        tag = new Tag("Site A", "department 2");
        tags = new ArrayList<>();
        resourceDetail = new ResourceDetail("12", "vm 12", "vm", tags);
        tags.add(tag);
        resourceDetailList.add(resourceDetail);



        resource = new Resource("non-compliant", "" + resourceDetailList.size(),resourceDetailList );
        resourceList = new ArrayList<>();
        resourceList.add(resource);



        Gson g = new Gson();
        g.toJson(resourceList);

        System.out.println(g.toString());



        return null;
    }


}


class ResponseObject {

    ResponseObject(String _data) {
        setData(_data);
    }

    String data;

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }
}



