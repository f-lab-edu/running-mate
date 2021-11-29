package com.runningmate.runningmate.image.service;

import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.runningmate.runningmate.image.domain.entity.Image;
import com.runningmate.runningmate.common.exception.ImageUploadException;
import com.runningmate.runningmate.image.domain.repository.ImageRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class AwsS3ImageUploadService implements ImageUploadService {

    private final ImageRepository mybatisImageRepository;

    @Value("${cloud.aws.credentials.access-key}")
    private String accessKey;

    @Value("${cloud.aws.credentials.secret-key}")
    private String secretKey;

    @Value("${cloud.aws.s3.bucket}")
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
    public Image upload(MultipartFile file) {
        String storeName = UUID.randomUUID().toString().replace("-", "");
        String extension = StringUtils.getFilenameExtension(file.getOriginalFilename());
        String path = storeName + "." + extension;

        Image image = Image.builder()
                .originalName(file.getOriginalFilename())
                .storeName(storeName)
                .extension(extension)
                .path(path)
                .createDate(LocalDateTime.now())
                .build();

        mybatisImageRepository.save(image);

        uploadToStorage(path, file);

        return image;
    }

    @Override
    public void delete(String path) {
        amazonS3.deleteObject(bucket, path);
    }

    public void uploadToStorage(String path, MultipartFile file) {
        try {
            ObjectMetadata objectMetadata = new ObjectMetadata();

            objectMetadata.setContentLength(file.getSize());
            objectMetadata.setContentType(file.getContentType());

            amazonS3.putObject(new PutObjectRequest(bucket, path, file.getInputStream(), objectMetadata)
                    .withCannedAcl(CannedAccessControlList.PublicRead));
        } catch (IOException e) {
            throw new ImageUploadException();
        }
    }
}
