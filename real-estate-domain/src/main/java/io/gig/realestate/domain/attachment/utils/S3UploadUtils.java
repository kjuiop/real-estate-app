package io.gig.realestate.domain.attachment.utils;

import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.PutObjectRequest;
import io.gig.realestate.domain.attachment.utils.properties.S3Properties;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.io.File;
import java.net.URL;

/**
 * @author : JAKE
 * @date : 2022/12/13
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class S3UploadUtils {

    private final S3Properties s3Properties;

    public String upload(File uploadFile, String filePath, String saveFileName) {

        AmazonS3 s3Client = getS3Client(s3Properties);
        String fileName = filePath + File.separator + saveFileName;
        s3Client.putObject(new PutObjectRequest(s3Properties.getBucketName(), fileName, uploadFile)
                .withCannedAcl(CannedAccessControlList.PublicRead));

        log.info("============= Upload custom file - Done!! ===============");
        URL url = s3Client.getUrl(s3Properties.getBucketName(), fileName);

        log.info(String.format("============= Upload URL %s ===============", url.toString()));

        return url.toString();
    }

    private AmazonS3 getS3Client(S3Properties s3Properties) {

        if (s3Properties == null) {
            return null;
        }

        AWSCredentials credentials = new BasicAWSCredentials(s3Properties.getAccessKey(), s3Properties.getSecretKey());

        return AmazonS3ClientBuilder.standard()
                .withRegion(s3Properties.getRegion())
                .withCredentials(new AWSStaticCredentialsProvider(credentials))
                .withForceGlobalBucketAccessEnabled(true)
                .build();
    }
}
