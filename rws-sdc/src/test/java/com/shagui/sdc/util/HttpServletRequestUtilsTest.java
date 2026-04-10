package com.shagui.sdc.util;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

class HttpServletRequestUtilsTest {

    @AfterEach
    void cleanup() {
        RequestContextHolder.resetRequestAttributes();
    }

    @Test
    void getAuthorizationSidAndWorkflowHeadersReturnEmptyWhenNoRequest() {
        RequestContextHolder.resetRequestAttributes();

        assertEquals("", HttpServletRequestUtils.getAuthorizationHeader());
        assertEquals("", HttpServletRequestUtils.getSIDHeader());
        assertEquals("", HttpServletRequestUtils.getWorkfowIdHeader());
    }

    @Test
    void getHeadersFromCurrentRequest() {
        MockHttpServletRequest request = new MockHttpServletRequest();
        request.addHeader(Ctes.HEADER_AUTHORIZATION, "Bearer token");
        request.addHeader(Ctes.HEADER_SESSION_ID, "session-123");
        request.addHeader(Ctes.HEADER_WORKFLOW_ID, "workflow-xyz");

        RequestContextHolder.setRequestAttributes(new ServletRequestAttributes(request));

        assertEquals("Bearer token", HttpServletRequestUtils.getAuthorizationHeader());
        assertEquals("session-123", HttpServletRequestUtils.getSIDHeader());
        assertEquals("workflow-xyz", HttpServletRequestUtils.getWorkfowIdHeader());
    }
}
