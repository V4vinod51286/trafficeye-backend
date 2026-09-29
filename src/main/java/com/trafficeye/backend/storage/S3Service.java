package com.trafficeye.backend.storage;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import software.amazon.awssdk.services.s3.presigner.S3Presigner;
import software.amazon.awssdk.services.s3.presigner.model.PresignedPutObjectRequest;
import software.amazon.awssdk.services.s3.presigner.model.PutObjectPresignRequest;

import java.time.Duration;
import java.util.UUID;

@Service
public class S3Service {

    private final S3Presigner s3Presigner;

    @Value("${aws.s3.bucket}")
    private String bucketName;

    public S3Service(S3Presigner s3Presigner) {
        this.s3Presigner = s3Presigner;
    }

    public PresignedUpload generateUploadUrl(
            String fileName,
            String contentType
    ) {

        String extension = getFileExtension(fileName);

        String objectKey = "complaints/"
                + UUID.randomUUID()
                + "/video"
                + extension;

        PutObjectPresignRequest presignRequest =
                PutObjectPresignRequest.builder()
                        .signatureDuration(Duration.ofMinutes(15))
                        .putObjectRequest(
                                software.amazon.awssdk.services.s3.model.PutObjectRequest.builder()
                                        .bucket(bucketName)
                                        .key(objectKey)
                                        .contentType(contentType)
                                        .build()
                        )
                        .build();

        PresignedPutObjectRequest presignedRequest =
                s3Presigner.presignPutObject(presignRequest);

        return new PresignedUpload(
                presignedRequest.url().toString(),
                objectKey
        );
    }

    private String getFileExtension(String fileName) {

    if (fileName == null || fileName.isBlank()) {
        return ".mp4";
    }

    int lastDot = fileName.lastIndexOf('.');

    if (lastDot == -1 || lastDot == fileName.length() - 1) {
        return ".mp4";
    }

    String extension = fileName.substring(lastDot).toLowerCase();

    // Only allow common video extensions
    return switch (extension) {
        case ".mp4", ".mov", ".m4v", ".avi", ".webm", ".3gp" -> extension;
        default -> ".mp4";
    };
}

    public record PresignedUpload(
            String uploadUrl,
            String objectKey
    ) {
    }
}