package com.example.interview.controller;

import com.example.interview.bean.ArrayComparisonRequest;
import com.example.interview.bean.ArrayComparisonResponse;
import com.example.interview.service.ArrayComparisonService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/arrays")
public class ArrayComparisonController {

    private final ArrayComparisonService arrayComparisonService;

    @PostMapping("/common-elements")
    public ArrayComparisonResponse findCommonElements(@Valid @RequestBody ArrayComparisonRequest request) {
        return new ArrayComparisonResponse(arrayComparisonService.findCommonElement(
                request.getPrimaryNumbers(), request.getComparisonNumbers()));
    }
}