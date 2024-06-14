package com.hivenote.backend.config;

import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import software.amazon.awssdk.auth.credentials.AwsBasicCredentials;
import software.amazon.awssdk.auth.credentials.StaticCredentialsProvider;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.textract.TextractClient;

@Getter
@Configuration
@ConditionalOnProperty(value = "aws.isDisabled", havingValue = "false", matchIfMissing = true)
public class AWSClientConfig {
  private final Region region = Region.EU_CENTRAL_1;

  @Value("${aws.accessKey}")
  private String accessKey;

  @Value("${aws.secretAccessKey}")
  private String secretAccessKey;

  @Value("${aws.bucketName}")
  private String bucketName;

  @Bean
  public AmazonS3 amazonS3() {
    BasicAWSCredentials awsCredentials = new BasicAWSCredentials(accessKey, secretAccessKey);
    if ((accessKey.equals("null") || secretAccessKey.equals("null"))) {
      throw new RuntimeException("AWS credentials are not set");
    }

    return AmazonS3ClientBuilder.standard()
        .withCredentials(new AWSStaticCredentialsProvider(awsCredentials))
        .withRegion(region.toString())
        .build();
  }

  @Bean
  public TextractClient textractClient() {
    if ((accessKey.equals("null") || secretAccessKey.equals("null"))) {
      throw new RuntimeException("AWS credentials are not set");
    }

    return TextractClient.builder()
        .credentialsProvider(
            StaticCredentialsProvider.create(
                AwsBasicCredentials.create(accessKey, secretAccessKey)))
        .region(region)
        .build();
  }
}
