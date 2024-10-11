package com.searchoptimization.resourceservice.services;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;


@Service
@AllArgsConstructor
public class QueryService {

    private final RestTemplate restTemplate;

    public Object searchBasedOnKeyword(String keyword) {

        String elasticsearchUrl = "http://elasticsearch:9200/_search";

        String requestBody = "{ \"query\": { \"query_string\": { \"query\": \"" + keyword + "~\" } } }";

        HttpHeaders headers = new HttpHeaders();
        headers.set("Content-Type", "application/json");

        HttpEntity<String> request = new HttpEntity<>(requestBody, headers);

        ResponseEntity<Object> response = restTemplate.exchange(elasticsearchUrl, HttpMethod.POST, request, Object.class);

        return response.getBody();
    }
}
