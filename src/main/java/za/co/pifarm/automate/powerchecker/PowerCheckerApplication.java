package za.co.pifarm.automate.powerchecker;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.telegram.telegrambots.ApiContextInitializer;

@SpringBootApplication
public class PowerCheckerApplication {

    public static void main(String[] args) {

        ApiContextInitializer.init();
        SpringApplication.run(PowerCheckerApplication.class, args);


    }

}
