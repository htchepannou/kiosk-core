package com.tchepannou.kiosk.core.service;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.GetObjectRequest;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.amazonaws.services.s3.model.S3Object;
import com.amazonaws.services.s3.model.S3ObjectInputStream;
import org.apache.commons.io.IOUtils;
import org.apache.http.client.methods.HttpGet;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class S3ServiceTest {
    @Mock
    AmazonS3 s3;

    S3Service service;

    @Before
    public void setUp(){
        service = new S3Service("bucket", s3);
    }

    @Test
    public void testPut() throws Exception {
        // Given
        final ByteArrayInputStream in = new ByteArrayInputStream("hello world".getBytes());

        // When
        service.put("s3://foo/bar.txt", in);

        // Then
        ArgumentCaptor<PutObjectRequest> request = ArgumentCaptor.forClass(PutObjectRequest.class);
        verify(s3).putObject(request.capture());

        assertThat(request.getValue().getBucketName()).isEqualTo("bucket");
        assertThat(request.getValue().getKey()).isEqualTo("foo/bar.txt");
        assertThat(request.getValue().getInputStream()).isEqualTo(in);
        assertThat(request.getValue().getMetadata().getContentEncoding()).isEqualTo("utf-8");
        assertThat(request.getValue().getMetadata().getContentType()).isEqualTo("text/plain");
    }

    @Test
    public void testGet() throws Exception {
        // Given
        final ByteArrayOutputStream out = new ByteArrayOutputStream();

        final S3ObjectInputStream in = new S3ObjectInputStream(IOUtils.toInputStream("TXT"), new HttpGet());
        final S3Object obj = mock(S3Object.class);
        when(obj.getObjectContent()).thenReturn(in);

        when(s3.getObject(any())).thenReturn(obj);

        // When
        service.get("s3://foo/bar.txt", out);

        // Then
        ArgumentCaptor<GetObjectRequest> request = ArgumentCaptor.forClass(GetObjectRequest.class);
        verify(s3).getObject(request.capture());

        assertThat(request.getValue().getBucketName()).isEqualTo("bucket");
        assertThat(request.getValue().getKey()).isEqualTo("foo/bar.txt");
    }
}
