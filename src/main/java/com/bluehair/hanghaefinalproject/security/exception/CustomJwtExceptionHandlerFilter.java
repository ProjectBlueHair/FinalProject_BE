package com.bluehair.hanghaefinalproject.security.exception;

import com.bluehair.hanghaefinalproject.common.response.error.ErrorResponse;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;

@Slf4j
@RequiredArgsConstructor
public class CustomJwtExceptionHandlerFilter extends OncePerRequestFilter {
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        try {
            filterChain.doFilter(request, response);
        } catch (CustomJwtException ex){
            setErrorResponse(response, ex);
        }
    }

    private void setErrorResponse(HttpServletResponse response,
                                  CustomJwtException ex) throws IOException{
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        response.setStatus(ex.getErrorCode().getHttpStatus().value());
        try (OutputStream os = response.getOutputStream()) {
            ObjectMapper objectMapper = new ObjectMapper();
            objectMapper.writeValue(os, ErrorResponse.builder()
                    .customHttpStatus(ex.getErrorCode().getCustomHttpStatusCode())
                    .message(ex.getErrorCode().getMessage())
                    .build()
            );
            os.flush();
        }
    }
}
