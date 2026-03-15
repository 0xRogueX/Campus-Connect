package com.campusconnect.eventsservice.service;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.UUID;

@Service
public class S3StorageService {

    private final AmazonS3 amazonS3Client;

    @Value("${aws.s3.bucket-name}")
    private String bucketName;

    public S3StorageService(AmazonS3 amazonS3Client) {
        this.amazonS3Client = amazonS3Client;
    }

    /**
     * Upload an image file to AWS S3
     *
     * @param file The image file to upload
     * @return The public URL of the uploaded image
     * @throws IOException If there's an error reading the file
     */
    public String uploadImage(MultipartFile file) throws IOException {
        // Generate a unique filename
        String fileName = "images/" + UUID.randomUUID().toString() + "_" + file.getOriginalFilename();

        // Prepare metadata
        ObjectMetadata metadata = new ObjectMetadata();
        metadata.setContentType(file.getContentType());
        metadata.setContentLength(file.getSize());

        // Upload file to S3
        amazonS3Client.putObject(
                new PutObjectRequest(bucketName, fileName, file.getInputStream(), metadata)
        );


        // Return the public URL
        return amazonS3Client.getUrl(bucketName, fileName).toString();
    }

    /**
     * Delete an image from AWS S3
     *
     * @param imageUrl The full URL of the image to delete
     * @return true if deletion was successful, false otherwise
     */
    public boolean deleteImage(String imageUrl) {
        try {
            // Extract the key (filename) from URL
            String key = extractKeyFromUrl(imageUrl);

            // Check if object exists
            if (amazonS3Client.doesObjectExist(bucketName, key)) {
                amazonS3Client.deleteObject(bucketName, key);
                return true;
            }
            return false;
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * Extract the S3 object key from a full URL
     *
     * @param url Full S3 URL
     * @return Object key
     */
    private String extractKeyFromUrl(String url) {
        // The URL format is typically: https://bucket-name.s3.region.amazonaws.com/key
        // or https://s3.region.amazonaws.com/bucket-name/key
        String[] parts = url.split(bucketName + "\\.");
        if (parts.length > 1) {
            // Format: https://bucket-name.s3.region.amazonaws.com/key
            return url.substring(url.indexOf(bucketName + ".") + bucketName.length() + 1)
                    .replaceFirst("s3\\.[^\\/]+\\.amazonaws\\.com\\/", "");
        } else {
            // Format: https://s3.region.amazonaws.com/bucket-name/key
            return url.substring(url.indexOf(bucketName + "/") + bucketName.length() + 1);
        }
    }
}