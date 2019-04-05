package za.co.pifarm.automate.powerchecker.services.power;

import za.co.pifarm.automate.powerchecker.enums.AlertClient;
import za.co.pifarm.automate.powerchecker.enums.PowerStatus;
import za.co.pifarm.automate.powerchecker.enums.RemoteLocation;

public interface PowerChecker {

    PowerStatus checkPower(RemoteLocation remoteLocation, Boolean adhocCheck);

    void triggerAlert(AlertClient alertclient, String message);

}
