package com.example.stresstestservice.facade;

import com.example.stresstestservice.service.StressTestService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class StressTestFacade {

    private final StressTestService stressTestService;

    public Long testUsersService(String endpoint, String httpMethod, String requestBody, Integer parallelThreadQuantity, Integer cycleQuantity) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<String> requestEntity = new HttpEntity<>(requestBody, headers);
        return stressTestService.sendRequests(parallelThreadQuantity, cycleQuantity, endpoint, HttpMethod.valueOf(httpMethod), requestEntity);
    }
}
