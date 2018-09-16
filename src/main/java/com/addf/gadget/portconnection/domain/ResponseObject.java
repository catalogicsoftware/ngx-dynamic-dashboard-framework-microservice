package com.addf.gadget.portconnection.domain;

import com.addf.gadget.portconnection.knowledgebase.KB;

public class ResponseObject {

   public ResponseObject(String status, String exception, RequestObject endPoint, String portDescription, KB knowledgeBaseArticle) {
        setStatus(status);
        setHost(endPoint.getHost());
        setPort(endPoint.getPort());
        setPortDescription(portDescription);
        setKnowledgeBaseArticle(knowledgeBaseArticle);
        setException(exception);
    }

    private String status;
    private String host;
    private String port;
    private KB knowledgeBaseArticle;
    private String exception;

    public String getPortDescription() {
        return portDescription;
    }

    public void setPortDescription(String portDescription) {
        this.portDescription = portDescription;
    }

    private String portDescription;


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

    public String getException() {
        return this.exception;
    }

    private void setException(String exception) {
        this.exception = exception;
    }
}