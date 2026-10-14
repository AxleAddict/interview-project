package com.example.interview.service.impl;

import com.example.interview.service.ArrayComparisonService;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import org.springframework.stereotype.Service;

@Service
public class ArrayComparisonServiceImpl implements ArrayComparisonService {

    @Override
    public List<Integer> findCommonElements(List<Integer> primaryNumbers, List<Integer> comparisonNumbers) {
        Set<Integer> commonElements = new LinkedHashSet<>();
        for (Integer number : primaryNumbers) {
            if (comparisonNumbers.contains(number)) {
                commonElements.add(number);
            }
        }
        return new ArrayList<>(commonElements);
    }
}