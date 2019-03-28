package za.co.pifarm.automate.powerchecker.messaging;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import za.co.pifarm.automate.powerchecker.model.LocationData;
import za.co.pifarm.automate.powerchecker.model.RequestMessage;

@Component
public class HttpRequestSender implements RequestSender {

    @Autowired
    private RestTemplate restTemplate;

    @Override
    public Boolean send(RequestMessage requestMessage, LocationData locationData) {

        HttpEntity<RequestMessage> request = new HttpEntity(requestMessage);

        //handle exceptions
        return restTemplate.exchange(locationData.getRemoteUrl(), locationData.getHttpMethod(), request, Boolean.class).getBody();
    }
}
