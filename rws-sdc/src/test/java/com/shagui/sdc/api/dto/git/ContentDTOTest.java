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
    void testGetName() {
        assertEquals("test.txt", contentDTO.getName());
    }

    @Test
    void testGetPath() {
        assertEquals("/test.txt", contentDTO.getPath());
    }

    @Test
    void testGetSha() {
        assertEquals("1234567890abcdef", contentDTO.getSha());
    }

    @Test
    void testGetSize() {
        assertEquals(10, contentDTO.getSize());
    }

    @Test
    void testGetUrl() {
        assertEquals("https://api.github.com/repos/octocat/Hello-World/contents/test.txt", contentDTO.getUrl());
    }

    @Test
    void testGetHtmlUrl() {
        assertEquals("https://github.com/octocat/Hello-World/blob/master/test.txt", contentDTO.getHtmlUrl());
    }

    @Test
    void testGetDownloadUrl() {
        assertEquals("https://raw.githubusercontent.com/octocat/Hello-World/master/test.txt",
                contentDTO.getDownloadUrl());
    }

    @Test
    void testGetType() {
        assertEquals("file", contentDTO.getType());
    }

    @Test
    void testGetContent() {
        assertEquals("VGhpcyBpcyBhIHRlc3Q=", contentDTO.getContent());
    }

    @Test
    void testGetEncoding() {
        assertEquals("base64", contentDTO.getEncoding());
    }

    @Test
    void testGetDecodedContent() {
        assertEquals("This is a test", contentDTO.getDecodedContent());
    }

    @Test
    void testContentDTO() {
        assertNotNull(contentDTO);
    }
}