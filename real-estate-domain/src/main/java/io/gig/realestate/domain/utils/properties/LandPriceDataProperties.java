package io.gig.realestate.domain.utils.properties;

import io.gig.realestate.domain.utils.YamlPropertySourceFactory;
import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

/**
 * @author : JAKE
 * @date : 2023/12/24
 */
@Component
@Getter
@Setter
@ConfigurationProperties(prefix = "data.land-price-data-api")
@PropertySource(value="classpath:/application-credentials.yml", factory = YamlPropertySourceFactory.class)
public class LandPriceDataProperties {
    String url;
    String serviceKey;
}
