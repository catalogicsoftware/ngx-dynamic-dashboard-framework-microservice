package com.addf.gadgets.chart.service.memory;

import com.addf.gadgets.chart.domain.MetricModel;
import com.addf.gadgets.chart.domain.Series;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

@Service
public class MemoryMetricService implements MemoryMetric {

    private final RestTemplate restTemplate;

    public MemoryMetricService(RestTemplateBuilder restTemplateBuilder) {
        this.restTemplate = restTemplateBuilder.build();
    }

    public List<MetricModel> process() {

        List<MetricModel> data;

        ResponseEntity<String> series1 = getSeries("jvm.memory.committed");
        ResponseEntity<String> series2 = getSeries("jvm.memory.used");

        if (series1.getStatusCode() == HttpStatus.OK && series2.getStatusCode() == HttpStatus.OK) {

            data = new ArrayList<>();
            data.add(new MetricModel("memory", processResults(series1.getBody(), series2.getBody())));

            return data;

        } else {
            return Collections.EMPTY_LIST;
        }
    }

    private ResponseEntity<String> getSeries(String endpoint) {

        HttpHeaders headers = new HttpHeaders();
        headers.set("Accept", "application/json");

        HttpEntity entity = new HttpEntity(headers);
        return this.restTemplate.exchange("http://localhost:8080/actuator/metrics/" + endpoint, HttpMethod.GET, entity, String.class);
    }

    private List<Series> processResults(String result1, String result2) {

        //use GSON to parse the json and fill in the Array of series objects

        Gson g = new Gson();

        Type type = new TypeToken<Map<String, Object>>() {
        }.getType();
        Map<String, Object> myMap1 = g.fromJson(result1, type);
        Map<String, Object> myMap2 = g.fromJson(result2, type);


        List<Map<String, ?>> data = (List<Map<String, ?>>) myMap1.get("measurements");
        List<Map<String, ?>> data2 = (List<Map<String, ?>>) myMap2.get("measurements");

        Double val1 = (Double) data.get(0).get("value");
        Double val2 = (Double) data2.get(0).get("value");

        Series a = new Series();
        a.setName("used");
        a.setValue((val2 / val1) * 100);

        Series b = new Series();
        b.setName("committed");
        b.setValue((1 - val2 / val1) * 100);


        List<Series> chartVal = new ArrayList<>();
        chartVal.add(a);
        chartVal.add(b);

        return chartVal;

    }

}
