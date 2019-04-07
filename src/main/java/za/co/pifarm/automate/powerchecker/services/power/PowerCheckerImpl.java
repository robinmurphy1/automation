package za.co.pifarm.automate.powerchecker.services.power;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;
import za.co.pifarm.automate.powerchecker.data.PowerData;
import za.co.pifarm.automate.powerchecker.enums.AlertClient;
import za.co.pifarm.automate.powerchecker.enums.PowerStatus;
import za.co.pifarm.automate.powerchecker.enums.RemoteLocation;
import za.co.pifarm.automate.powerchecker.messaging.RequestSender;
import za.co.pifarm.automate.powerchecker.messaging.TelegramCommunicator;
import za.co.pifarm.automate.powerchecker.model.LocationData;
import za.co.pifarm.automate.powerchecker.model.RequestMessage;
import za.co.pifarm.automate.powerchecker.repo.PowerDataRepository;
import za.co.pifarm.automate.powerchecker.repo.PowerNotificationRepository;
import za.co.pifarm.automate.powerchecker.utils.PowerDataMapper;

import java.util.Date;

@Service
public class PowerCheckerImpl implements PowerChecker {

    private static final Logger logger = LoggerFactory.getLogger(PowerCheckerImpl.class);

    @Autowired
    private PowerDataMapper powerDataMapper;

    @Autowired
    private RequestSender requestSender;

    @Value("${power.status.ok.msg}")
    private String statusOkMsg;


    @Autowired
    private TelegramCommunicator telegramCommunicator;

    @Autowired
    private PowerDataRepository powerDataRepository;

    @Override
    public PowerStatus checkPower(RemoteLocation remoteLocation, Boolean adhocCheck) {

        LocationData locationData = powerDataMapper.getLocationData(remoteLocation);

        try {
            return logPowerStatus(requestSender.send(new RequestMessage(), locationData), adhocCheck, remoteLocation);
        } catch (Exception e) {
            powerDataRepository.save(new PowerData(new Date(), remoteLocation, PowerStatus.ERR));
            return PowerStatus.ERR;
        }
    }

    PowerStatus logPowerStatus(ResponseEntity responseEntity, Boolean adhocCheck, RemoteLocation remoteLocation) {

        if (ObjectUtils.isEmpty(responseEntity)
                || ObjectUtils.isEmpty(responseEntity.getBody())
                || !responseEntity.getBody().toString().contains(statusOkMsg.trim())) {

            if (!adhocCheck) {
                powerDataRepository.save(new PowerData(new Date(), remoteLocation, PowerStatus.ERR));
            }
            return PowerStatus.ERR;
        }
        return PowerStatus.OK;
    }

    @Override
    public void triggerAlert(AlertClient alertClient, String message) {

        if (AlertClient.TELEGRAM == alertClient) {
            telegramCommunicator.sendMessage(message);
        }
    }

    @Scheduled(fixedRateString = "${power.checker.schedule.period}", initialDelayString = "${power.checker.schedule.delay}")
    public void runScheduledPowerCheck() {

        checkPower(RemoteLocation.HOME, Boolean.FALSE);
    }


}
