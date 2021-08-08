package com.ivan.polovyi.examples;

import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.client.builder.AwsClientBuilder;
import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.amazonaws.services.lambda.runtime.events.SQSEvent;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.amazonaws.util.IOUtils;

import java.io.ByteArrayInputStream;

public class SQSHandler implements RequestHandler<SQSEvent, String> {

    private static final String BUCKET_ENDPOINT = System.getenv("BUCKET_ENDPOINT");
    private static final String BUCKET_NAME = System.getenv("BUCKET_NAME");
    private static final String REGION = System.getenv("REGION");

    private static AmazonS3 s3Client = getAmazonS3();

    @Override
    public String handleRequest(SQSEvent sqsEvent, Context context) {

        sqsEvent.getRecords().forEach(message -> {
            context.getLogger()
                    .log("\nMessage id " + message.getMessageId() + "\n\nMessage body " + message.getBody()
                            + "\n\nUploading file to S3 bucket..\n");

            boolean isFileUploadedToS3 = uploadFileDirectlyToS3(message.getBody(),
                    "test-file" + message.getMessageId() + ".txt");
            context.getLogger()
                    .log(isFileUploadedToS3 ?
                            "\nFile uploaded successfully\n\n" :
                            "\nThere was an error on file upload\n\n");

        });
        return "HandleRequest is done!";
    }

    public boolean uploadFileDirectlyToS3(String fileContent, String prefix) {
        ObjectMetadata metadata = new ObjectMetadata();

        try {
            byte[] bytes = IOUtils.toByteArray(new ByteArrayInputStream(fileContent.getBytes()));
            metadata.setContentType("text/plain");
            metadata.setContentLength(bytes.length);

            ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(bytes);
            PutObjectRequest putObjectRequest = new PutObjectRequest(BUCKET_NAME, prefix, byteArrayInputStream,
                    metadata);
            s3Client.putObject(putObjectRequest);

            return true;
        } catch (Exception e) {
            return false;
        }
    }

    private static AmazonS3 getAmazonS3() {
        BasicAWSCredentials awsCredentials = new BasicAWSCredentials("access_key", "secret_key");
        return AmazonS3ClientBuilder.standard()
                .withCredentials(new AWSStaticCredentialsProvider(awsCredentials))
                .withEndpointConfiguration(new AwsClientBuilder.EndpointConfiguration(BUCKET_ENDPOINT, REGION))
                .withPathStyleAccessEnabled(true)
                .build();
    }
}
