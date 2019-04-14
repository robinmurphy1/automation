package za.co.pifarm.automate.powerchecker.services.power;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;
import za.co.pifarm.automate.powerchecker.data.PowerData;
import za.co.pifarm.automate.powerchecker.data.PowerNotification;
import za.co.pifarm.automate.powerchecker.enums.PowerStatus;
import za.co.pifarm.automate.powerchecker.enums.RemoteLocation;
import za.co.pifarm.automate.powerchecker.messaging.TelegramCommunicator;
import za.co.pifarm.automate.powerchecker.repo.PowerDataRepository;
import za.co.pifarm.automate.powerchecker.repo.PowerNotificationRepository;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class AlertNotifierImpl {

    @Autowired
    private PowerDataRepository powerDataRepository;

    @Autowired
    private PowerNotificationRepository powerNotificationRepository;

    @Autowired
    private TelegramCommunicator telegramCommunicator;

    @Value("${power.threshhold.period.minutes}")
    private int threshHoldPeriod;

    @Value("${power.checker.schedule.period}")
    private int runPeriodSeconds;

    private static final int MIN_SEC = 60;

    @Scheduled(fixedRateString = "${power.notification.schedule.period}", initialDelayString = "${power.notification.schedule.delay}")
    public void sendNotificatons() {

        List<PowerData> powerDataList = getLastRecordsForPeriod();

        PowerStatus powerStatus = checkRecordedPowerTimes(powerDataList);
        Optional<PowerNotification> optNotification = powerNotificationRepository.findById(RemoteLocation.HOME);

        if (ObjectUtils.isEmpty(powerDataList) && optNotification.isPresent() && optNotification.get().getStatus() != PowerStatus.ERR) {
            return;
        }

        PowerNotification powerNotification;
        if (optNotification.isPresent()) {
            powerNotification = optNotification.get();
        } else {
            powerNotification = new PowerNotification(RemoteLocation.HOME, PowerStatus.OK);
            powerNotificationRepository.saveAndFlush(powerNotification);
        }

        if (powerStatus == PowerStatus.ERR
                && powerNotification.getStatus() == PowerStatus.OK) {
            telegramCommunicator.sendMessage(String.format("Power failure : %s", new Date()));
            powerNotificationRepository.saveAndFlush(new PowerNotification(powerNotification.getRemoteLocation(), powerStatus));
        } else if (powerStatus == PowerStatus.OK
                && powerNotification.getStatus() == PowerStatus.ERR) {
            telegramCommunicator.sendMessage(String.format("Power restored : %s", new Date()));
            powerNotificationRepository.saveAndFlush(new PowerNotification(powerNotification.getRemoteLocation(), powerStatus));
        } else if (powerStatus == PowerStatus.UNKNOWN) {
//TODO: check for this condition
        }
    }

    PowerStatus checkRecordedPowerTimes(List<PowerData> powerDataList) {

        List<LocalDateTime> failures = powerDataList.stream()
                .map(x -> dateToLocalDateTime(x.getTimestamp()))
                .sorted()
                .collect(Collectors.toList());

        if (!ObjectUtils.isEmpty(failures) && failures.size() >= 3) {

            LocalDateTime firstEntry = failures.get(0);
            LocalDateTime lastEntry = failures.get(failures.size() - 1);

            if (lastEntry.plusSeconds(runPeriodSeconds / 1000).isAfter(LocalDateTime.now())
                    && (Duration.between(firstEntry, lastEntry).getSeconds() / MIN_SEC) <= threshHoldPeriod) {
                return PowerStatus.ERR;
            } else if (lastEntry.plusSeconds(runPeriodSeconds / 1000).isBefore(LocalDateTime.now())) {
                return PowerStatus.OK;
            } else if (Duration.between(firstEntry, lastEntry).getSeconds() / MIN_SEC > threshHoldPeriod) {
                return PowerStatus.OK;
            }
        }

        return PowerStatus.UNKNOWN;
    }

    List<PowerData> getLastRecordsForPeriod() {

        Date startTime = Date.from(LocalDateTime.now().atZone(ZoneId.of("Africa/Johannesburg"))
                .toInstant().minusSeconds(MIN_SEC * threshHoldPeriod));
        return powerDataRepository.findPowerDataByTimestampBetweenOrderByTimestampDesc(startTime, new Date());
    }

    LocalDateTime dateToLocalDateTime(Date recordedDate) {

        return LocalDateTime.ofInstant(recordedDate.toInstant(), ZoneId.systemDefault());
    }
}
