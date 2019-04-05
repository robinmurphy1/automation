package za.co.pifarm.automate.powerchecker.messaging;

import org.springframework.http.ResponseEntity;
import za.co.pifarm.automate.powerchecker.enums.PowerStatus;
import za.co.pifarm.automate.powerchecker.model.LocationData;
import za.co.pifarm.automate.powerchecker.model.RequestMessage;

public interface RequestSender {

    ResponseEntity send(RequestMessage requestMessage, LocationData locationData);
}
