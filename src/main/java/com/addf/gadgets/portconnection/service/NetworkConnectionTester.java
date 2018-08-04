package com.addf.gadgets.portconnection.service;

import com.addf.gadgets.portconnection.domain.RequestObject;
import com.addf.gadgets.portconnection.domain.ResponseObject;
import com.addf.gadgets.portconnection.knowledgebase.KB;
import com.addf.gadgets.portconnection.knowledgebase.PortService;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import reactor.core.publisher.FluxSink;

import java.io.IOException;
import java.io.InputStream;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketAddress;
import java.util.List;

public class NetworkConnectionTester {

    private static String SUCCESS = "success";
    private static String TIMEOUTERROR = "time-out";
    private static String REFUSEERROR = "connection-refuse";
    private static String UNKNOWNERROR = "unknown-host";
    private static String ROUTEERROR = "no-route";
    private static String UNDETERMINEDERROR = "undetermined";


    private NetworkConnectionTester() {
    }

    public static void testConnection(FluxSink<ResponseObject> sink, List<RequestObject> requestData, int connectionTimeout) {

        NetworkTestRunnable tester = new NetworkTestRunnable(sink, requestData, connectionTimeout);
        Thread t = new Thread(tester);
        t.start();

    }

    public static class NetworkTestRunnable implements Runnable {

        FluxSink<ResponseObject> sink;
        List<RequestObject> requestData;
        int connectionTimeout;

        NetworkTestRunnable(FluxSink<ResponseObject> sink, List<RequestObject> requestData, int connectionTimeout) {
            this.sink = sink;
            this.requestData = requestData;
            this.connectionTimeout = connectionTimeout;
        }

        public void run() {

            for (RequestObject endPoint : this.requestData) {

                ResponseObject response = testEndPoint(endPoint);

                sink.next(response);
            }
            sink.complete();

        }

        private ResponseObject testEndPoint(RequestObject endPoint) {

            String result;
            String exceptionString = "";
            int _port = Integer.parseInt(endPoint.getPort().trim());
            try (Socket socket = new Socket()) {

                SocketAddress socketAddress = new InetSocketAddress(endPoint.getHost(), _port);
                socket.connect(socketAddress, this.connectionTimeout);
                result = SUCCESS;


            } catch (Exception e) {
                exceptionString = e.toString();
                result = getProcessedResult(exceptionString);
            }

            return new ResponseObject(result, exceptionString, endPoint, getPortDescription(endPoint.getPort()), getKnowledgeBaseArticle(result));

        }

        private String getProcessedResult(String exception) {

            exception = exception.toLowerCase();

            if (exception.contains("success")) {
                return SUCCESS;
            } else if (exception.contains("time")) {
                return TIMEOUTERROR;
            } else if (exception.contains("refuse")) {
                return REFUSEERROR;
            } else if (exception.contains("unknown")) {
                return UNKNOWNERROR;
            } else if (exception.contains("route")) {
                return ROUTEERROR;
            }
            return UNDETERMINEDERROR;
        }

        /**
         * todo - move the file read code into an init method. No sense in doing this for every request
         *
         * @param result
         * @return
         */
        private KB getKnowledgeBaseArticle(String result) {

            //get knowledge base articles from a file
            ObjectMapper mapper = new ObjectMapper();
            TypeReference<List<KB>> kbTypeReference = new TypeReference<List<KB>>() {
            };
            InputStream inputStream = TypeReference.class.getResourceAsStream("/static/assets/api/connection-model.json");
            try {
                List<KB> kbs = mapper.readValue(inputStream, kbTypeReference);

                for (KB kb : kbs) {

                    if (kb.getEvent().contains(result)) {
                        return kb;
                    }
                }
            } catch (IOException e) {
                System.out.println("Unable to load kbs: " + e.getMessage());
            }

            return null;
        }


        /**
         * todo - move this file read code into an init method. No sense in doing this for every request
         *
         * @param port
         * @return
         */

        private String getPortDescription(String port) {

            //get knowledge base articles from a file
            ObjectMapper mapper = new ObjectMapper();
            TypeReference<List<PortService>> portTypeReference = new TypeReference<List<PortService>>() {
            };
            InputStream inputStream = TypeReference.class.getResourceAsStream("/static/assets/api/port-model.json");
            try {
                List<PortService> ports = mapper.readValue(inputStream, portTypeReference);

                for (PortService _port : ports) {

                    if (_port.getPortNumber().contains(port)) {
                        return _port.getDescription();
                    }
                }
            } catch (IOException e) {
                System.out.println("Unable to load kbs: " + e.getMessage());
            }

            return null;

        }

    }
}
