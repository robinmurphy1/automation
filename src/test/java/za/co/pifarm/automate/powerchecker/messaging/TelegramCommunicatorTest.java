package za.co.pifarm.automate.powerchecker.messaging;

import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.client.RestTemplate;
import za.co.pifarm.automate.config.PowerCheckerConfig;

import static org.junit.Assert.*;


@RunWith(SpringRunner.class)
@ContextConfiguration(classes = {TelegramCommunicatorTest.Config.class, PowerCheckerConfig.class})
@TestPropertySource(locations = "classpath:application.properties")
public class TelegramCommunicatorTest {

    @Configuration
    static class Config {

        @Bean
        public TelegramCommunicator createTelegramCommunicator(){
            return new TelegramCommunicator();
        }


    }

    @Autowired
    TelegramCommunicator telegramCommunicator;

    @Test
    public void sendMessage() {
        telegramCommunicator.sendMessage("test");
    }

    @Test
    @Ignore
    public void buildUrl() {
    }


}