package za.co.pifarm.automate.powerchecker.utils;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;
import za.co.pifarm.automate.powerchecker.enums.RemoteLocation;
import za.co.pifarm.automate.powerchecker.model.LocationData;

import static org.junit.Assert.assertTrue;


@RunWith(SpringRunner.class)
//@ContextConfiguration(classes = PowerDataMapperTest.Config.class)
@TestPropertySource(locations = "classpath:application.properties")
public class PowerDataMapperTest {

//    @Configuration
//    static class Config {
//
//        @Bean
//        public PowerDataMapper createPowerDataMapper() {
//            return new PowerDataMapper();
//        }
//
//    }

    @Autowired
    PowerDataMapper powerDataMapper;

    @Test
    public void getLocationDataTest() {

        LocationData locationData = powerDataMapper.getLocationData(RemoteLocation.HOME);
        assertTrue(locationData != null);
        assertTrue(locationData.getRemoteLocation().equals(RemoteLocation.HOME));
//        assertTrue(locationData.getRemoteUrl().equals("http://pifarm.co.za"));
    }
}