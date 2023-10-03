package io.gig.realestate.domain.attachment.utils.properties;

import io.gig.realestate.domain.utils.YamlPropertySourceFactory;
import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

/**
 * @author : JAKE
 * @date : 2023/08/15
 */
@Component
@Getter
@Setter
@ConfigurationProperties(prefix = "aws.s3")
@PropertySource(value="classpath:/application-credentials.yml", factory = YamlPropertySourceFactory.class)
public class S3Properties {
    String region;
    String bucketName;
    String accessKey;
    String secretKey;
}