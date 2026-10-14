package com.example.interview.service;

import static org.assertj.core.api.Assertions.assertThat;

import com.example.interview.service.impl.ArrayComparisonServiceImpl;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

class ArrayComparisonServiceImplTest {

    private final ArrayComparisonService service = new ArrayComparisonServiceImpl();

    @ParameterizedTest
    @MethodSource("intersectionCases")
    void findsDistinctCommonElementsInPrimaryOrder(
            List<Integer> primaryNumbers, List<Integer> comparisonNumbers, List<Integer> expected) {
        assertThat(service.findCommonElements(primaryNumbers, comparisonNumbers)).isEqualTo(expected);
    }

    static Stream<Arguments> intersectionCases() {
        return Stream.of(
                Arguments.of(List.of(4, 5, 6, 12, 2, 6, 19, 1), List.of(7, 19, 3, 6), List.of(6, 19)),
                Arguments.of(List.of(9, 2, 9, 5, 2), List.of(2, 9), List.of(9, 2)),
                Arguments.of(List.of(6, 6, 6), List.of(6, 6), List.of(6)),
                Arguments.of(List.of(0, -3, 4, -3), List.of(-3, 0), List.of(0, -3)),
                Arguments.of(List.of(Integer.MAX_VALUE, Integer.MIN_VALUE),
                        List.of(Integer.MIN_VALUE, Integer.MAX_VALUE),
                        List.of(Integer.MAX_VALUE, Integer.MIN_VALUE)),
                Arguments.of(List.of(), List.of(1), List.of()),
                Arguments.of(List.of(1), List.of(), List.of()),
                Arguments.of(List.of(), List.of(), List.of()),
                Arguments.of(List.of(1, 2), List.of(3, 4), List.of()));
    }

    @Test
    void doesNotChangeEitherInput() {
        List<Integer> primaryNumbers = new ArrayList<>(List.of(9, 2, 9));
        List<Integer> comparisonNumbers = new ArrayList<>(List.of(2, 9, 2));

        service.findCommonElements(primaryNumbers, comparisonNumbers);

        assertThat(primaryNumbers).containsExactly(9, 2, 9);
        assertThat(comparisonNumbers).containsExactly(2, 9, 2);
    }
}