package com.hivenote.backend.aws;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.hivenote.backend.aws.dto.FileUploadResponse;
import com.hivenote.backend.aws.dto.FileUrlResponse;
import com.hivenote.backend.config.AWSClientConfig;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.util.Objects;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class DocumentService {
  private final AmazonS3 amazonS3;
  private final AWSClientConfig awsClientConfig;

  @Value("${aws.bucketName}")
  private String bucketName;

  public DocumentService(AmazonS3 amazonS3, AWSClientConfig awsClientConfig) {
    this.amazonS3 = amazonS3;
    this.awsClientConfig = awsClientConfig;
  }

  public FileUploadResponse upload(MultipartFile file) {
    try {

      File localFile = convertMultipartFileToFile(file);

      amazonS3.putObject(
          new PutObjectRequest(
              awsClientConfig.getBucketName(), file.getOriginalFilename(), localFile));

      String url = amazonS3.getUrl(bucketName, file.getOriginalFilename()).toString();
      FileUrlResponse fileUrlResponse = new FileUrlResponse(url);
      localFile.delete();

      return new FileUploadResponse(1, fileUrlResponse);
    } catch (Exception e) {
      FileUrlResponse fileUrlResponse = new FileUrlResponse("error");
      return new FileUploadResponse(0, fileUrlResponse);
    }
  }

  private File convertMultipartFileToFile(MultipartFile file) {
    File convertedFile = new File(Objects.requireNonNull(file.getOriginalFilename()));
    try {

      if (file.getSize() > 40000000) {
        throw new RuntimeException("File size is too large");
      }

      Files.copy(
          file.getInputStream(), convertedFile.toPath(), StandardCopyOption.REPLACE_EXISTING);
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
    return convertedFile;
  }
}
