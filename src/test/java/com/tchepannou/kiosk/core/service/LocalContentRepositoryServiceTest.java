package com.tchepannou.kiosk.core.service;

import org.apache.commons.io.IOUtils;
import org.junit.Test;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Files;

import static org.assertj.core.api.Assertions.assertThat;

public class LocalContentRepositoryServiceTest {

    @Test
    public void shouldPutFile() throws Exception {
        // Given
        final File home = Files.createTempDirectory("test").toFile();
        final ContentRepositoryService repo = new LocalContentRepositoryService(home);

        final InputStream in = new ByteArrayInputStream("hello world".getBytes());

        // When
        repo.put("foo/bar.txt", in);

        // Then
        final File fout = new File(home.getAbsolutePath() + "/foo/bar.txt");
        assertThat(fout).exists();
        assertThat(fout).hasContent("hello world");
    }

    @Test
    public void shouldGetFromFile () throws Exception {
        // Given
        final File home = Files.createTempDirectory("test").toFile();
        final ContentRepositoryService repo = new LocalContentRepositoryService(home);

        try (OutputStream fout = new FileOutputStream(new File(home, "test.txt"))){
            final InputStream in = new ByteArrayInputStream("hello world".getBytes());
            IOUtils.copy(in, fout);
        }

        // When
        final ByteArrayOutputStream out = new ByteArrayOutputStream();
        repo.get("test.txt", out);

        // Then
        assertThat(out.toString()).isEqualTo("hello world");

    }

    @Test(expected = ContentRepositoryException.class)
    public void shouldThrowContentRepositoryExceptionnWhenReadInvalidFile () throws Exception {
        // Given
        final File home = Files.createTempDirectory("test").toFile();
        final ContentRepositoryService repo = new LocalContentRepositoryService(home);

        // When
        final ByteArrayOutputStream out = new ByteArrayOutputStream();
        repo.get("unknown.txt", out);

    }
}
