package com.hsbc.funds.service;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import okhttp3.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * Generic API client for making HTTP requests
 */
@Component
public class ApiClient {
    
    private static final Logger logger = LoggerFactory.getLogger(ApiClient.class);
    
    private final OkHttpClient httpClient;
    private final ObjectMapper objectMapper;
    private static final MediaType JSON = MediaType.get("application/json; charset=utf-8");
    
    public ApiClient() {
        this.httpClient = new OkHttpClient.Builder()
                .connectTimeout(30, TimeUnit.SECONDS)
                .readTimeout(60, TimeUnit.SECONDS)
                .writeTimeout(60, TimeUnit.SECONDS)
                .build();
        this.objectMapper = new ObjectMapper();
    }
    
    /**
     * Make a GET request and parse the response as JSON
     */
    public <T> T get(String url, Class<T> responseType) throws IOException {
        Request request = new Request.Builder()
                .url(url)
                .get()
                .build();
        
        try (Response response = httpClient.newCall(request).execute()) {
            if (!response.isSuccessful()) {
                throw new IOException("Unexpected response code: " + response.code());
            }
            
            String responseBody = response.body().string();
            return objectMapper.readValue(responseBody, responseType);
        }
    }
    
    /**
     * Make a GET request with custom headers and return raw response as String
     */
    public String get(String url, Map<String, String> headers) throws IOException {
        Request.Builder requestBuilder = new Request.Builder().url(url);
        
        // Add custom headers
        if (headers != null) {
            headers.forEach(requestBuilder::addHeader);
        }
        
        Request request = requestBuilder.build();
        
        try (Response response = httpClient.newCall(request).execute()) {
            if (!response.isSuccessful()) {
                String errorBody = response.body() != null ? response.body().string() : "No response body";
                logger.error("GET Request failed for URL: {}. Code: {}. Body: {}", url, response.code(), errorBody);
                throw new IOException("Unexpected response code: " + response.code() + " for URL: " + url + ". Body: " + errorBody);
            }
            
            return response.body().string();
        }
    }
    
    /**
     * Make a POST request with JSON body and parse the response as JSON
     */
    public <T> T post(String url, Object requestBody, Class<T> responseType) throws IOException {
        String jsonBody = objectMapper.writeValueAsString(requestBody);
        RequestBody body = RequestBody.create(jsonBody, JSON);
        
        Request request = new Request.Builder()
                .url(url)
                .post(body)
                .addHeader("Content-Type", "application/json")
                .addHeader("x-hsbc-channel-id", "OHI")
                .addHeader("x-hsbc-locale", "en_GB")
                .build();
        
        try (Response response = httpClient.newCall(request).execute()) {
            if (!response.isSuccessful()) {
                throw new IOException("Unexpected response code: " + response.code());
            }
            
            String responseBody = response.body().string();
            return objectMapper.readValue(responseBody, responseType);
        }
    }
    
    /**
     * Make a POST request with custom headers and return raw response as String
     */
    public String post(String url, String jsonBody, Map<String, String> headers) throws IOException {
        RequestBody body = RequestBody.create(jsonBody, JSON);
        Request.Builder requestBuilder = new Request.Builder().url(url).post(body);
        
        // Add custom headers
        if (headers != null) {
            headers.forEach(requestBuilder::addHeader);
        }
        
        Request request = requestBuilder.build();
        
        try (Response response = httpClient.newCall(request).execute()) {
            if (!response.isSuccessful()) {
                String errorBody = response.body() != null ? response.body().string() : "No response body";
                logger.error("POST Request failed for URL: {}. Code: {}. Body: {}", url, response.code(), errorBody);
                throw new IOException("Unexpected response code: " + response.code() + " for URL: " + url + ". Body: " + errorBody);
            }
            
            return response.body().string();
        }
    }
    
    /**
     * Make a GET request and parse the response as a list
     */
    public <T> List<T> getList(String url, TypeReference<List<T>> typeReference) throws IOException {
        Request request = new Request.Builder()
                .url(url)
                .get()
                .build();
        
        try (Response response = httpClient.newCall(request).execute()) {
            if (!response.isSuccessful()) {
                throw new IOException("Unexpected response code: " + response.code());
            }
            
            String responseBody = response.body().string();
            return objectMapper.readValue(responseBody, typeReference);
        }
    }
    
    /**
     * Make a GET request and return raw response as String
     */
    public String getRawResponse(String url) throws IOException {
        Request request = new Request.Builder()
                .url(url)
                .get()
                .build();
        
        try (Response response = httpClient.newCall(request).execute()) {
            if (!response.isSuccessful()) {
                throw new IOException("Unexpected response code: " + response.code());
            }
            
            return response.body().string();
        }
    }
    
    public void close() {
        httpClient.dispatcher().executorService().shutdown();
        httpClient.connectionPool().evictAll();
    }
}
