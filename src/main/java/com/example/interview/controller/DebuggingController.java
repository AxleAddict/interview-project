package com.example.interview.controller;

import com.example.interview.bean.DebuggingRequest;
import com.example.interview.bean.DebuggingResponse;
import com.example.interview.service.DebuggingService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/arrays")
public class DebuggingController {

    private final DebuggingService debuggingService;

    @PostMapping("/common-elements")
    public DebuggingResponse findCommonElements(@Valid @RequestBody DebuggingRequest request) {
        return new DebuggingResponse(debuggingService.findCommonElement(
                request.getPrimaryNumbers(), request.getComparisonNumbers()));
    }
}