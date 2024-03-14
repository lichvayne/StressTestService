package com.example.stresstestservice.facade;

import com.example.stresstestservice.service.StressTestService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class StressTestFacade {

    private final StressTestService stressTestService;

    public void testUsersService(String url, Integer port, String path, String httpMethod, String requestBody, Integer parallelThreadQuantity, Long cycleQuantity) throws InterruptedException {
        StringBuilder endpoint = new StringBuilder();
        endpoint.append(url).append(":").append(port).append(path);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<String> requestEntity = new HttpEntity<>(requestBody, headers);
        stressTestService.sendRequests(parallelThreadQuantity, cycleQuantity, endpoint.toString(), HttpMethod.valueOf(httpMethod), requestEntity);
    }
}
