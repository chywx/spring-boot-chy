package cn.chendahai.chy.storage.s3.utils;

import software.amazon.awssdk.auth.credentials.AwsBasicCredentials;
import software.amazon.awssdk.auth.credentials.StaticCredentialsProvider;
import software.amazon.awssdk.core.ResponseBytes;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.core.waiters.WaiterResponse;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.S3Configuration;
import software.amazon.awssdk.services.s3.model.*;
import software.amazon.awssdk.services.s3.waiters.S3Waiter;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.URL;

public class TestUpload {

    public static void main(String[] args) throws IOException {
        String accessKeyId = "*";
        String secretAccessKey = "*";


        Region region = Region.US_EAST_1;

        AwsBasicCredentials awsBasicCredentials = AwsBasicCredentials.create(accessKeyId, secretAccessKey);
        S3Configuration s3Config = S3Configuration.builder().pathStyleAccessEnabled(true).build();

        // 构建
        S3Client s3 = S3Client.builder()
                .credentialsProvider(StaticCredentialsProvider.create(awsBasicCredentials))
                .region(region)
                .serviceConfiguration(s3Config)
                .build();

        System.out.println(s3);

//        createBucket(s3, "gbank-admin-upload");
//        if (true) {
//            return;
//        }

        // 上传
        String bucket = "gbank-admin-upload";
        String contentType = "image/png";
        File file = new File("C:\\Users\\cob\\Pictures\\baobao\\baobao1.png");
        String fileName = file.getName();
        String strs[] = fileName.split("\\.");
        String keyName = "test/" + strs[0] + System.currentTimeMillis() + "." + strs[1];
        System.out.println(keyName);
        PutObjectRequest putOb = PutObjectRequest.builder().bucket(bucket).key(keyName).contentType(contentType).build();
//        s3.putObject(putOb, RequestBody.fromBytes(file.getBytes()));
        s3.putObject(putOb, RequestBody.fromFile(file));
        System.out.println("/" + bucket + "/" + keyName);

        // 获取url
        GetUrlRequest request = GetUrlRequest.builder().bucket(bucket).key(keyName).build();
        URL url = s3.utilities().getUrl(request);
        System.out.println("The URL for " + keyName + " is " + url);


        // 获取对象
        GetObjectRequest getObjectRequest = GetObjectRequest.builder().bucket(bucket).key(keyName).build();
        ResponseBytes<GetObjectResponse> objectBytes = s3.getObjectAsBytes(getObjectRequest);
        byte[] data = objectBytes.asByteArray();

        File myFile = new File("C:\\Users\\cob\\Pictures\\baobao\\baobao2.png");
        OutputStream os = new FileOutputStream(myFile);
        os.write(data);
        System.out.println("Successfully obtained bytes from an S3 object");
        os.close();


    }

    public static void createBucket(S3Client s3Client, String bucketName) {

        try {
            S3Waiter s3Waiter = s3Client.waiter();
            CreateBucketRequest bucketRequest = CreateBucketRequest.builder()
                    .bucket(bucketName)
                    .build();

            s3Client.createBucket(bucketRequest);
            HeadBucketRequest bucketRequestWait = HeadBucketRequest.builder()
                    .bucket(bucketName)
                    .build();

            // Wait until the bucket is created and print out the response.
            WaiterResponse<HeadBucketResponse> waiterResponse = s3Waiter.waitUntilBucketExists(bucketRequestWait);
            waiterResponse.matched().response().ifPresent(System.out::println);
            System.out.println(bucketName + " is ready");

        } catch (S3Exception e) {
            System.err.println(e.awsErrorDetails().errorMessage());
            System.exit(1);
        }
    }
    // snippet-end:[s3.java2.create_bucket_waiters.main]

}
