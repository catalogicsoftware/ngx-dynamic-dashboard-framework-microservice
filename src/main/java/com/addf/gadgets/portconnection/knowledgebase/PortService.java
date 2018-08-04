package com.addf.gadgets.portconnection.knowledgebase;

public class PortService {


    String serviceName;
    String portNumber;
    String transportProtocol;

    public String getServiceName() {
        return serviceName;
    }

    public void setServiceName(String serviceName) {
        this.serviceName = serviceName;
    }

    public String getPortNumber() {
        return portNumber;
    }

    public void setPortNumber(String portNumber) {
        this.portNumber = portNumber;
    }

    public String getTransportProtocol() {
        return transportProtocol;
    }

    public void setTransportPortocol(String transportProtocol) {
        this.transportProtocol = transportProtocol;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    String description;

}
