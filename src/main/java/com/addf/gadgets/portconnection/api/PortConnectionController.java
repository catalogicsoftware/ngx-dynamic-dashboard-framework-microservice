package com.addf.gadgets.portconnection.api;

import com.addf.gadgets.portconnection.service.NetworkConnectionTester;
import com.addf.gadgets.portconnection.domain.RequestObject;
import com.addf.gadgets.portconnection.domain.ResponseObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

import java.util.List;

@RestController
@CrossOrigin
public class PortConnectionController {
    @Value("${connection.timeout}")
    private int connectionTimeout;

    @PostMapping(path = "/connectTest")
    public Flux<ResponseObject> processConnectionTest(@RequestBody List<RequestObject> connectionTestRequest) {

        return Flux.create(sink -> {
            NetworkConnectionTester.testConnection(sink,
                    connectionTestRequest, this.connectionTimeout);
        });

    }


}
