package com.example.interview.bean;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class CommonElementsResponse {

    private final List<Integer> commonElements;
}