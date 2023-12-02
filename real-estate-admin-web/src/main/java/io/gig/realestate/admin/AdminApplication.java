package io.gig.realestate.admin;

import io.gig.realestate.domain.config.ComponentConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;
import org.springframework.scheduling.annotation.EnableAsync;

/**
 * @author : JAKE
 * @date : 2023/02/25
 */
@EnableAsync
@Import(ComponentConfig.class)
@SpringBootApplication
public class AdminApplication {

    public static void main(String[] args) {
        SpringApplication.run(AdminApplication.class, args);
    }

}
