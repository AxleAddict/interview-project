package com.example.interview.bean;

import com.fasterxml.jackson.annotation.JsonAlias;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.util.List;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DebuggingRequest {

    @JsonAlias("right")
    @NotNull
    @Size(max = 10_000)
    private List<@NotNull Integer> primaryNumbers;

    @JsonAlias("left")
    @NotNull
    @Size(max = 10_000)
    private List<@NotNull Integer> comparisonNumbers;
}