package com.shagui.sdc.api.dto.git;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class ContentDTOTest {

    private ContentDTO contentDTO;

    @BeforeEach
    void init() {
        contentDTO = new ContentDTO();
        contentDTO.setName("test.txt");
        contentDTO.setPath("/test.txt");
        contentDTO.setSha("1234567890abcdef");
        contentDTO.setSize(10);
        contentDTO.setUrl("https://api.github.com/repos/octocat/Hello-World/contents/test.txt");
        contentDTO.setHtmlUrl("https://github.com/octocat/Hello-World/blob/master/test.txt");
        contentDTO.setDownloadUrl("https://raw.githubusercontent.com/octocat/Hello-World/master/test.txt");
        contentDTO.setType("file");
        contentDTO.setContent("VGhpcyBpcyBhIHRlc3Q=");
        contentDTO.setEncoding("base64");
    }

    @Test
    void getName() {
        assertEquals("test.txt", contentDTO.getName());
    }

    @Test
    void getPath() {
        assertEquals("/test.txt", contentDTO.getPath());
    }

    @Test
    void getSha() {
        assertEquals("1234567890abcdef", contentDTO.getSha());
    }

    @Test
    void getSize() {
        assertEquals(10, contentDTO.getSize());
    }

    @Test
    void getUrl() {
        assertEquals("https://api.github.com/repos/octocat/Hello-World/contents/test.txt", contentDTO.getUrl());
    }

    @Test
    void getHtmlUrl() {
        assertEquals("https://github.com/octocat/Hello-World/blob/master/test.txt", contentDTO.getHtmlUrl());
    }

    @Test
    void getDownloadUrl() {
        assertEquals("https://raw.githubusercontent.com/octocat/Hello-World/master/test.txt",
                contentDTO.getDownloadUrl());
    }

    @Test
    void getType() {
        assertEquals("file", contentDTO.getType());
    }

    @Test
    void getContent() {
        assertEquals("VGhpcyBpcyBhIHRlc3Q=", contentDTO.getContent());
    }

    @Test
    void getEncoding() {
        assertEquals("base64", contentDTO.getEncoding());
    }

    @Test
    void getDecodedContent() {
        assertEquals("This is a test", contentDTO.getDecodedContent());
    }

    @Test
    void contentDTO() {
        assertNotNull(contentDTO);
    }
}