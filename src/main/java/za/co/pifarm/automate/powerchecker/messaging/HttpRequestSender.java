package za.co.pifarm.automate.powerchecker.messaging;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import za.co.pifarm.automate.powerchecker.enums.PowerStatus;
import za.co.pifarm.automate.powerchecker.model.LocationData;
import za.co.pifarm.automate.powerchecker.model.RequestMessage;

@Component
public class HttpRequestSender implements RequestSender {

    @Autowired
    private RestTemplate restTemplate;

    @Override
    public ResponseEntity send(RequestMessage requestMessage, LocationData locationData) {

        //handle exceptions
        return ResponseEntity.ok(restTemplate.getForObject(locationData.getRemoteUrl(), String.class));

    }
}
