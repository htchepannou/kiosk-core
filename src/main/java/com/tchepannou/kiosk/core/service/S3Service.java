package com.tchepannou.kiosk.core.service;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.GetObjectRequest;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.amazonaws.services.s3.model.S3Object;
import org.apache.commons.io.IOUtils;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class S3Service implements UrlService {
    private final String S3_PREFIX = "s3://";
    private final AmazonS3 s3;
    private final String bucket;

    public S3Service(final String bucket, final AmazonS3 s3) {
        this.bucket = bucket;
        this.s3 = s3;
    }

    @Override
    public void put(final String url, final InputStream content) throws IOException {
        final String key = key(url);

        final ObjectMetadata meta = new ObjectMetadata();
        meta.setContentEncoding("utf-8");

        final PutObjectRequest request = new PutObjectRequest(bucket, key, content, meta);
        s3.putObject(request);
    }

    @Override
    public void get(final String url, final OutputStream out) throws IOException {
        final String key = key(url);

        final GetObjectRequest request = new GetObjectRequest(bucket, key);
        try (S3Object object = s3.getObject(request)) {
            IOUtils.copy(object.getObjectContent(), out);
        }
    }

    private String key(final String url) {
        if (url.startsWith(S3_PREFIX)) {
            return url.substring(S3_PREFIX.length());
        }
        return null;
    }
}
