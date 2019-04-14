package za.co.pifarm.automate.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;
import za.co.pifarm.automate.powerchecker.utils.PowerDataMapper;

@Configuration
public class PowerCheckerConfig {

    @Bean
    public RestTemplate createRestTemplate(){
        return new RestTemplate();
    }

    @Bean
    public PowerDataMapper createPowerDataMapper() {
        return new PowerDataMapper();
    }
}
