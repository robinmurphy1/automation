package za.co.pifarm.automate.powerchecker.services.power;

import za.co.pifarm.automate.powerchecker.enums.AlertClient;
import za.co.pifarm.automate.powerchecker.enums.RemoteLocation;

public interface PowerChecker {

    Boolean checkPower(RemoteLocation remoteLocation, Boolean adhocCheck);

    void triggerAlert(AlertClient alertclient);

    void runPowerCheck(RemoteLocation remoteLocation, AlertClient alertClient);

}
