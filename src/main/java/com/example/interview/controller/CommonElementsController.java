package com.example.interview.controller;

import com.example.interview.bean.CommonElementsRequest;
import com.example.interview.bean.CommonElementsResponse;
import com.example.interview.service.CommonElementsService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/arrays")
public class CommonElementsController {

    private final CommonElementsService commonElementsService;

    @PostMapping("/common-elements")
    public CommonElementsResponse findCommonElements(@Valid @RequestBody CommonElementsRequest request) {
        return new CommonElementsResponse(commonElementsService.findCommonElement(
                request.getPrimaryNumbers(), request.getComparisonNumbers()));
    }
}