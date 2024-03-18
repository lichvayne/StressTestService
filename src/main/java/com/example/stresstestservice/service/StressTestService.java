package com.example.stresstestservice.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.atomic.AtomicInteger;

@Service
@RequiredArgsConstructor
@Slf4j
public class StressTestService {

    private CyclicBarrier cyclicBarrier;
    private final RestTemplate restTemplate;

    public Long sendRequests(
            Integer parallelThreadQuantity,
            Integer cycleQuantity,
            String endpoint,
            HttpMethod httpMethod,
            HttpEntity<?> requestObject) {

        cyclicBarrier = new CyclicBarrier(parallelThreadQuantity);
        log.info("--- Started sending requests to {} ---", endpoint);
        AtomicInteger successfulRequestQuantity = new AtomicInteger(0);
        for (int j = 0; j < cycleQuantity; j++) {
            CountDownLatch countDownLatch = new CountDownLatch(parallelThreadQuantity);
            for (int i = 0; i < parallelThreadQuantity; i++) {
                new Thread(() -> {
                    try {
                        cyclicBarrier.await();
                        HttpStatusCode statusCode = restTemplate.exchange(endpoint, httpMethod, requestObject, Void.class).getStatusCode();
                        if (statusCode.is2xxSuccessful() || statusCode.is3xxRedirection()) {
                            successfulRequestQuantity.getAndIncrement();
                        }
                    } catch (Exception e) {
                        log.info("Exception: {} Message: {}", e.getClass().getName(), e.getMessage());
                    }
                    countDownLatch.countDown();
                }).start();
            }
            try {
                countDownLatch.await();
            } catch (InterruptedException e) {
                log.info("{}", e.getMessage());
            }
        }
        return successfulRequestQuantity.longValue();
    }
}
