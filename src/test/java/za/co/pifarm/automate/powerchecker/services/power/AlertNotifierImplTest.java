package za.co.pifarm.automate.powerchecker.services.power;

import org.junit.Before;
import org.junit.Test;
import za.co.pifarm.automate.powerchecker.data.PowerData;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class AlertNotifierImplTest {

    List<PowerData> powerDataList;

    @Before
    public void setUp() throws Exception {

        powerDataList = new ArrayList<>();
    }

    @Test
    public void whenNoEntriesWithinTheNotificationPeriod_thenNoNotification() {
    }

     @Test
    public void whenOnlyFirstEntryWithinTheNotificationPeriod_thenNoNotification() {
    }

      @Test
    public void whenFirstEntryAndOthersWithinTheNotificationPeriod_thenSendFailNotification() {
    }

       @Test
    public void whenFirstEntryAndOthersWithinTheNotificationPeriodAndNotificationSent_thenNoNotification() {
    }

       @Test
    public void whenNotificatioSentAndFirstEntryOutsidePeriod_thenSendOkNotification() {
    }

    @Test
    public void getLastRecords() {
    }

    @Test
    public void sendPowerAlert() {
    }

    @Test
    public void dateToLocalDateTime() {
    }

    Date createPastDatedEntries(Long period, Boolean futureDated) {

        if (futureDated) {
            return Date.from(LocalDateTime.now().atZone(ZoneId.systemDefault()).plusMinutes(period).toInstant());
        } else {
            return Date.from(LocalDateTime.now().atZone(ZoneId.systemDefault()).minusMinutes(period).toInstant());
        }
    }

}