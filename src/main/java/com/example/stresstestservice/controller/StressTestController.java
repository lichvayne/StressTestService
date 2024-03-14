package com.example.stresstestservice.controller;

import com.example.stresstestservice.facade.StressTestFacade;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/stress-test")
@Slf4j
public class StressTestController {

    private final StressTestFacade stressTestFacade;

    @GetMapping
    public void testUsersService(
            @RequestParam("url") String url,
            @RequestParam("port") Integer port,
            @RequestParam("path") String path,
            @RequestParam("http_method") String httpMethod,
            @RequestParam("parallel_thread_quantity") Integer parallelthreadquantity,
            @RequestParam("cycle_quantity") Long cycleQuantity,
            @RequestBody(required = false) String requestBody) throws InterruptedException {
        log.info("Json Request Body: {}",requestBody);
        stressTestFacade.testUsersService(url, port, path, httpMethod, requestBody, parallelthreadquantity, cycleQuantity);
    }
}
