package com.tchepannou.kiosk.core.service;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.GetObjectRequest;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.amazonaws.services.s3.model.S3Object;
import org.apache.commons.io.IOUtils;

import javax.activation.MimetypesFileTypeMap;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class S3Service extends FileService {
    private final AmazonS3 s3;

    public S3Service(final String bucket, final AmazonS3 s3) {
        super(bucket);

        this.s3 = s3;
    }

    @Override
    public void put(final String url, final InputStream content) throws IOException {
        final String key = key(url);
        final String bucket = getBucket();
        final MimetypesFileTypeMap mimeTypesMap = new MimetypesFileTypeMap();
        final String contentType = mimeTypesMap.getContentType(key);

        final ObjectMetadata meta = new ObjectMetadata();
        meta.setContentEncoding("utf-8");
        meta.setContentType(contentType);

        final PutObjectRequest request = new PutObjectRequest(bucket, key, content, meta);
        s3.putObject(request);
    }

    @Override
    public void get(final String url, final OutputStream out) throws IOException {
        final String key = key(url);
        final String bucket = getBucket();

        final GetObjectRequest request = new GetObjectRequest(bucket, key);
        try (S3Object object = s3.getObject(request)) {
            IOUtils.copy(object.getObjectContent(), out);
        }
    }

    private String getBucket() {
        return super.home;
    }

    private String key(final String url) {
        if (url.startsWith(S3_PREFIX)) {
            return url.substring(S3_PREFIX.length());
        } else if (url.startsWith("/")) {
            return url.substring(1);
        }
        return url;
    }
}
