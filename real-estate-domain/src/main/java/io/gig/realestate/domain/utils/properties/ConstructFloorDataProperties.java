package io.gig.realestate.domain.utils.properties;

import io.gig.realestate.domain.utils.YamlPropertySourceFactory;
import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

/**
 * @author : JAKE
 * @date : 2023/09/28
 */
@Component
@Getter
@Setter
@ConfigurationProperties(prefix = "data.construct-floor-data-api")
@PropertySource(value="classpath:/application-credentials.yml", factory = YamlPropertySourceFactory.class)
public class ConstructFloorDataProperties {
    String url;
    String serviceKey;
}
