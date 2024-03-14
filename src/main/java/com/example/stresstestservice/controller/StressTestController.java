package com.example.stresstestservice.controller;

import com.example.stresstestservice.facade.StressTestFacade;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/stress-test")
public class StressTestController {

    private final StressTestFacade stressTestFacade;

    @GetMapping
    public ResponseEntity<Long> testUsersService(
            @RequestParam("endpoint") String endpoint,
            @RequestParam("http_method") String httpMethod,
            @RequestParam("parallel_thread_quantity") Integer parallelThreadQuantity,
            @RequestParam("cycle_quantity") Integer cycleQuantity,
            @RequestBody(required = false) String requestBody) {

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(stressTestFacade.testUsersService(endpoint, httpMethod, requestBody, parallelThreadQuantity, cycleQuantity));
    }
}
