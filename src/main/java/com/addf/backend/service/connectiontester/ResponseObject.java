package com.addf.backend.service.connectiontester;

import com.addf.backend.service.knowledgebase.KB;

public class ResponseObject {

    ResponseObject(String status, String exception,  RequestObject endPoint, KB knowledgeBaseArticle) {
        setStatus(status);
        setHost(endPoint.getHost());
        setPort(endPoint.getPort());
        setKnowledgeBaseArticle(knowledgeBaseArticle);
        setException(exception);
    }

    private String status;
    private String host;
    private String port;
    private KB knowledgeBaseArticle;
    private String exception;



    public String getHost() {
        return host;
    }

    private void setHost(String host) {
        this.host = host;
    }

    public String getPort() {
        return port;
    }

    private void setPort(String port) {
        this.port = port;
    }

    public String getStatus() {
        return status;
    }

    private void setStatus(String status) {
        this.status = status;
    }

    public KB getKnowledgeBaseArticle() {
        return knowledgeBaseArticle;
    }

    private void setKnowledgeBaseArticle(KB knowledgeBaseArticle) {
        this.knowledgeBaseArticle = knowledgeBaseArticle;
    }

    public String getException(){
        return this.exception;
    }

    private void setException(String exception){
        this.exception = exception;
    }
}