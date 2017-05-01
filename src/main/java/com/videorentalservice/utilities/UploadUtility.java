package com.videorentalservice.utilities;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.*;
import com.videorentalservice.models.Disc;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;

/**
 * Created by Rave on 01.05.2017.
 */
public class UploadUtility {
    public static String extension(MultipartFile file){
        if (file.getContentType().toLowerCase().equals("image/jpg")) {
            return ".jpg";
        } else if (file.getContentType().toLowerCase().equals("image/jpeg")) {
            return ".jpeg";
        } else {
            return ".png";
        }
    }

    public static String uploadToS3(String bucket, MultipartFile file, String name, AmazonS3 s3client, Disc disc){
        try {
            InputStream inputStream = file.getInputStream();
            s3client.putObject(
                    new PutObjectRequest(bucket, name, inputStream, new ObjectMetadata()).withCannedAcl(CannedAccessControlList.PublicRead)
            );
            S3Object s3ObjectCover = s3client.getObject(new GetObjectRequest(bucket, name));
            return s3ObjectCover.getObjectContent().getHttpRequest().getURI().toString();
        } catch (IOException e) {
            return "error";
        }
    }

    public static void deleteFromS3(String bucket, String name, AmazonS3 s3client){
        s3client.deleteObject(new DeleteObjectRequest(bucket,name));
    }
}
