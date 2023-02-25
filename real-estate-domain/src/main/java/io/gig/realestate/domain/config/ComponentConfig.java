package io.gig.realestate.domain.config;

import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

/**
 * @author : JAKE
 * @date : 2023/02/25
 */
@Configuration
@ComponentScan(basePackages ="io.gig.realestate.domain")
@EntityScan(basePackages = "io.gig.realestate.domain")
@EnableJpaRepositories(basePackages = "io.gig.realestate.domain")
public class ComponentConfig {
}
