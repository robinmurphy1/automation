package za.co.pifarm.automate.powerchecker.messaging;

import za.co.pifarm.automate.powerchecker.model.LocationData;
import za.co.pifarm.automate.powerchecker.model.RequestMessage;

public interface RequestSender {

    Boolean send(RequestMessage requestMessage, LocationData locationData);
}
