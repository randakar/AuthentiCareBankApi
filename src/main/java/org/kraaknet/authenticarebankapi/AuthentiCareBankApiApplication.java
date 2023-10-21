package org.kraaknet.authenticarebankapi;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;

@SpringBootApplication
@ConfigurationPropertiesScan
public class AuthentiCareBankApiApplication {

    public static void main(String[] args) {
        SpringApplication.run(AuthentiCareBankApiApplication.class, args);
    }

}
