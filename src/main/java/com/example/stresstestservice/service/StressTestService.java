package com.example.stresstestservice.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.concurrent.CyclicBarrier;

@Service
@RequiredArgsConstructor
@Slf4j
public class StressTestService {

    private CyclicBarrier cyclicBarrier;

    private final RestTemplate restTemplate;

    public void sendRequests(
            Integer parallelThreadQuantity,
            Long requestQuantity,
            String endpoint,
            HttpMethod httpMethod,
            HttpEntity<?> requestObject,
            Object... pathVariables) {

        cyclicBarrier = new CyclicBarrier(parallelThreadQuantity);

        log.info("Created endpoint to send requests: {}",endpoint);
        log.info("--- Started sending requests ---");

        for (int i = 0; i < requestQuantity; i++) {
            new Thread(() -> {
                try {
                    cyclicBarrier.await();
                    ResponseEntity<Void> exchange = restTemplate.exchange(endpoint, httpMethod, requestObject,Void.class,pathVariables);
                    log.info("Request Status {}",exchange.getStatusCode());
                } catch (Exception e) {
                    log.info("Exception: {} Message: {}",e.getClass().getName(),e.getMessage());
                }
            }).start();
        }
    }

    public void sendRequests(
            Integer parallelThreadQuantity,
            Long requestQuantity,
            String endpoint,
            HttpMethod httpMethod,
            HttpEntity<?> requestObject) {

        cyclicBarrier = new CyclicBarrier(parallelThreadQuantity);

        log.info("Created endpoint to send requests: {}",endpoint);
        log.info("--- Started sending requests ---");

        for (int i = 0; i < requestQuantity; i++) {
            new Thread(() -> {
                try {
                    cyclicBarrier.await();
                    ResponseEntity<?> exchange = restTemplate.exchange(endpoint, httpMethod, requestObject,Void.class);
                    log.info("Request Status {}",exchange.getStatusCode());
                } catch (Exception e) {
                    log.info("Exception: {} Message: {}",e.getClass().getName(),e.getMessage());
                }
            }).start();
        }
    }
}
