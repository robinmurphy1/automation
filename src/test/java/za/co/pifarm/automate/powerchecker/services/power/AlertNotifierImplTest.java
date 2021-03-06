package za.co.pifarm.automate.powerchecker.services.power;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.AutoConfigurationPackage;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.client.RestTemplate;
import za.co.pifarm.automate.config.PowerCheckerConfig;
import za.co.pifarm.automate.powerchecker.Data;
import za.co.pifarm.automate.powerchecker.data.PowerData;
import za.co.pifarm.automate.powerchecker.data.PowerNotification;
import za.co.pifarm.automate.powerchecker.enums.PowerStatus;
import za.co.pifarm.automate.powerchecker.enums.RemoteLocation;
import za.co.pifarm.automate.powerchecker.repo.PowerDataRepository;
import za.co.pifarm.automate.powerchecker.repo.PowerNotificationRepository;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@DataJpaTest
@AutoConfigurationPackage
@TestPropertySource(locations = "classpath:application.properties")
@ContextConfiguration(classes = {PowerCheckerConfig.class, AlertNotifierImplTest.Config.class})
@ComponentScan("za.co.pifarm.automate.powerchecker")
public class AlertNotifierImplTest {

    private static final Logger logger = LoggerFactory.getLogger(AlertNotifierImplTest.class);

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private AlertNotifierImpl alertNotifier;

    @Autowired
    RestTemplate restTemplate;

    @Autowired
    PowerDataRepository powerDataRepository;

    @Autowired
    PowerNotificationRepository powerNotificationRepository;

//    @Mock
//    TelegramCommunicator telegramCommunicator;

    private List<PowerData> powerDataList;

    @Before
    public void setUp() throws Exception {

    }

    @Test
    @Rollback
    @Transactional
    public void whenNoEntriesWithinTheNotificationPeriod_thenNoNotification() {

        //given - a list with no records within the threshold
        Data.createPowerDataNotWithinThreshold().forEach(data -> entityManager.persistAndFlush(data));

        // when - check that no records are returned
        List<PowerData> powerDataList = alertNotifier.getLastRecordsForPeriod();
        PowerStatus powerStatus = alertNotifier.checkRecordedPowerTimes(powerDataList);

        //then - do not send notifications
        assertThat(powerStatus).isEqualTo(PowerStatus.UNKNOWN);

    }

    @Test
    public void whenOnlyFirstEntryWithinTheNotificationPeriod_thenStatusUnkown() {

        //given - a list with no records within the threshold
        Data.createPowerDataFirstEntryWithinThreshold().forEach(data -> entityManager.persistAndFlush(data));

        // when - check that no records are returned
        List<PowerData> powerDataList = alertNotifier.getLastRecordsForPeriod();
        powerDataList.forEach(data -> logger.info("powerDataList  :  {}", data));
        PowerStatus powerStatus = alertNotifier.checkRecordedPowerTimes(powerDataList);

        logger.info("should be unknown status  : {}", powerStatus);

        //then - do not send notifications
        assertThat(powerStatus).isEqualTo(PowerStatus.UNKNOWN);
    }

    @Test
    public void whenThreeEntryWithinTheNotificationPeriod_thenStatusErr() {

        //given - a list with all records within the threshold
        Data.createPowerDataThreeEntryErrWithinThreshold().forEach(data -> entityManager.persistAndFlush(data));

        // when - check that records are returned
        List<PowerData> powerDataList = alertNotifier.getLastRecordsForPeriod();
        powerDataList.forEach(data -> logger.info("powerDataList  :  {}", data));
        PowerStatus powerStatus = alertNotifier.checkRecordedPowerTimes(powerDataList);

        //then - do not send notifications
        assertThat(powerStatus).isEqualTo(PowerStatus.ERR);
    }

    @Test
    public void whenFirstAfterRuntimePeriodAndOthersWithinTheNotificationPeriod_thenStatusOK() {

        //given - a list where last entry out of runtime period and others within threshold
        Data.createPowerDataFirstAfterRuntimePeriodOthersEntryErrWithinThreshold().forEach(data -> entityManager.persistAndFlush(data));

        // when - check that no records are returned
        List<PowerData> powerDataList = alertNotifier.getLastRecordsForPeriod();
        PowerStatus powerStatus = alertNotifier.checkRecordedPowerTimes(powerDataList);

        //then - do not send notifications
        assertThat(powerStatus).isEqualTo(PowerStatus.OK);
    }

    @Test
    @Transactional
    @Rollback
    public void whenFirstEntryOnly_thenUpdateNotificationRecord_andSendNoNotification() {

        Data.createPowerDataSingleEntry().forEach(data -> entityManager.persistAndFlush(data));

        alertNotifier.sendNotificatons();

        Optional<PowerNotification> notification = powerNotificationRepository.findById(RemoteLocation.HOME);

        if (notification.isPresent()) {
            assertThat(notification.get().getStatus()).isEqualTo(PowerStatus.OK);
        } else {
            throw new RuntimeException();
        }


    }

    @Test
    public void whenFirstEntryAndOthersWithinTheNotificationPeriod_thenSendNotification() {

        Data.createPowerDataWithinThreshold().forEach(data -> entityManager.persistAndFlush(data));
        //Notification not yet been set
        entityManager.persistAndFlush(Data.createPowerNotificationOk());

        alertNotifier.sendNotificatons();

        Optional<PowerNotification> notification = powerNotificationRepository.findById(RemoteLocation.HOME);

        if (notification.isPresent()) {
            assertThat(notification.get().getStatus()).isEqualTo(PowerStatus.ERR);
        } else {
            throw new RuntimeException();
        }

    }

    @Test
    public void whenFirstEntryAndOthersWithinTheNotificationPeriodAndNotificationSent_thenNoNotification() {

        Data.createPowerDataWithinThreshold().forEach(data -> entityManager.persistAndFlush(data));
        //ERR notification already sent
        entityManager.persistAndFlush(Data.createPowerNotificationErr());

        alertNotifier.sendNotificatons();

        Optional<PowerNotification> notification = powerNotificationRepository.findById(RemoteLocation.HOME);

        if (notification.isPresent()) {
            assertThat(notification.get().getStatus()).isEqualTo(PowerStatus.ERR);
        } else {
            throw new RuntimeException();
        }

    }

    @Test
    public void whenNotificationSentAndFirstEntryOutsidePeriod_thenSendOkNotification() {

        Data.createPowerDataFirstAfterRuntimePeriodOthersEntryErrWithinThreshold().forEach(data -> entityManager.persistAndFlush(data));
        entityManager.persistAndFlush(Data.createPowerNotificationErr());

        alertNotifier.sendNotificatons();

        Optional<PowerNotification> notification = powerNotificationRepository.findById(RemoteLocation.HOME);

        if (notification.isPresent()) {
            assertThat(notification.get().getStatus()).isEqualTo(PowerStatus.OK);
        } else {
            throw new RuntimeException();
        }
    }

    @Test
    @Transactional
    @Rollback
    public void getLastRecords_withinThresholdPeriod() {

//        given - a list with all values within threshold
        Data.createPowerDataWithinThreshold().forEach(data -> entityManager.persistAndFlush(data));

//        when - check for records within threshold
        List<PowerData> powerDataList = alertNotifier.getLastRecordsForPeriod();

//        then - return only the records that are valid
        assertThat(powerDataList).isNotNull();
        assertThat(powerDataList.size()).isEqualTo(Data.createPowerDataWithinThreshold().size());
    }

    @Test
    public void getLastRecords_outsideThresholdPeriod() {
    }

    @Test
    @Rollback
    public void getLastRecords_withinAndOutThresholdPeriod() {

        //        given - a list
        Data.createPowerDataWithinThreshold().forEach(data -> entityManager.persistAndFlush(data));

//        when
        List<PowerData> powerDataList = alertNotifier.getLastRecordsForPeriod();

//        then
        assertThat(powerDataList).isNotNull();
        assertThat(powerDataList.size()).isEqualTo(4);
//
    }


    @Test
    public void sendPowerAlert() {
    }

    @Test
    public void dateToLocalDateTime() {
    }

    public static class Config {


    }


}