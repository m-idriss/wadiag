package com.dime.wadiag.configuration;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoInteractions;
import static org.mockito.Mockito.when;

import java.io.IOException;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

class SwaggerRedirectFilterTest {

    @Mock
    private HttpServletRequest request;

    @Mock
    private HttpServletResponse response;

    @Mock
    private FilterChain filterChain;

    private SwaggerRedirectFilter filter;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        filter = new SwaggerRedirectFilter();
    }

    @DisplayName("Should redirect to swagger-ui.html when request URI is empty")
    @Test
    void test_redirect_to_swagger_ui_when_request_uri_is_empty() throws ServletException, IOException {
        when(request.getRequestURI()).thenReturn("");
        filter.doFilter(request, response, filterChain);
        verify(response).sendRedirect("/swagger-ui.html");
        verifyNoInteractions(filterChain);
    }

    @DisplayName("Should redirect to swagger-ui.html when request URI is /")
    @Test
    void test_redirect_to_swagger_ui_when_request_uri_is_slash() throws ServletException, IOException {
        when(request.getRequestURI()).thenReturn("/");
        filter.doFilter(request, response, filterChain);
        verify(response).sendRedirect("/swagger-ui.html");
        verifyNoInteractions(filterChain);
    }

    @DisplayName("Should call filterChain.doFilter when request URI is not empty or /")
    @Test
    void test_all_filter_chain_when_request_uri_is_not_empty_or_slash() throws ServletException, IOException {
        when(request.getRequestURI()).thenReturn("/some-uri");
        filter.doFilter(request, response, filterChain);
        verify(filterChain).doFilter(request, response);
        verifyNoInteractions(response);
    }
}