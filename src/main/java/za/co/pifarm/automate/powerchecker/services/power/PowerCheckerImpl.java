package za.co.pifarm.automate.powerchecker.services.power;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import za.co.pifarm.automate.powerchecker.enums.AlertClient;
import za.co.pifarm.automate.powerchecker.enums.RemoteLocation;
import za.co.pifarm.automate.powerchecker.messaging.RequestSender;
import za.co.pifarm.automate.powerchecker.model.LocationData;
import za.co.pifarm.automate.powerchecker.model.RequestMessage;
import za.co.pifarm.automate.powerchecker.utils.PowerDataMapper;

@Service
public class PowerCheckerImpl implements PowerChecker {

    @Autowired
    private PowerDataMapper powerDataMapper;

    @Autowired
    private RequestSender requestSender;

    @Override
    public Boolean checkPower(RemoteLocation remoteLocation, Boolean adhocCheck) {

        LocationData locationData = powerDataMapper.getLocationData(remoteLocation);

        Boolean powerOn = requestSender.send(new RequestMessage(), locationData);

        if (powerOn && !adhocCheck) {
            //login in db
        } else {
            //send meesage to user
        }

        return null;
    }

    @Override
    public void triggerAlert(AlertClient alertClient) {

    }

    @Override
    public void runPowerCheck(RemoteLocation remoteLocation, AlertClient alertClient) {

    }

    @Scheduled(fixedRateString = "${power.checker.schedule.period}", initialDelayString = "${power.checker.schedule.delay}")
    public void runScheduledPowerCheck() {

        checkPower(RemoteLocation.HOME, Boolean.FALSE);
        //checkpower
        //log in db
        //trigger alert
    }


}
