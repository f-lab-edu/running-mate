package com.runningmate.runningmate.image.service;

import static com.runningmate.runningmate.image.domain.entity.ImageStatus.*;

import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.*;
import com.runningmate.runningmate.image.domain.entity.Image;
import com.runningmate.runningmate.common.exception.ImageUploadException;
import com.runningmate.runningmate.image.domain.repository.ImageRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class AwsS3ImageUploadService implements ImageUploadService {

    private final ImageRepository mybatisImageRepository;

    @Value("${cloud.aws.credentials.access-key}")
    private String accessKey;

    @Value("${cloud.aws.credentials.secret-key}")
    private String secretKey;

    @Value("${cloud.aws.s3.bucket.image}")
    private String bucket;

    @Value("${cloud.aws.region.static}")
    private String region;

    private AmazonS3 amazonS3;

    @PostConstruct
    public void amazonS3() {
        AWSCredentials awsCredentials = new BasicAWSCredentials(accessKey, secretKey);

        amazonS3 = AmazonS3ClientBuilder.standard()
            .withRegion(region)
            .withCredentials(new AWSStaticCredentialsProvider(awsCredentials))
            .build();
    }

    @Override
    @Transactional
    public Image upload(MultipartFile file) {
        Image image = Image.builder()
            .status(BEING_USED)
            .originalFileName(file.getOriginalFilename())
            .storageFileName(file.getOriginalFilename())
            .createDate(LocalDateTime.now())
            .updateDate(LocalDateTime.now())
            .build();

        mybatisImageRepository.save(image);

        uploadToStorage(image.getStorageFileName(), file);

        return image;
    }

    @Override
    @Transactional
    public void delete(long imageId) {
        Image image = mybatisImageRepository.findById(imageId);
        image.delete();

        mybatisImageRepository.update(image);
        amazonS3.deleteObject(bucket, image.getStorageFileName());
    }

    private void uploadToStorage(String storageFileName, MultipartFile file) {
        try {
            ObjectMetadata objectMetadata = new ObjectMetadata();

            objectMetadata.setContentLength(file.getSize());
            objectMetadata.setContentType(file.getContentType());

            amazonS3.putObject(new PutObjectRequest(bucket, storageFileName, file.getInputStream(),
                objectMetadata));
        } catch (IOException e) {
            throw new ImageUploadException();
        }
    }
}
