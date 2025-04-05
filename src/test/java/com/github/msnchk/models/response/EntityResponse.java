package com.github.msnchk.models.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.Getter;

import java.util.List;

@Getter
public class EntityResponse {
    private String id;
    private String title;
    private boolean verified;

    @JsonProperty("important_numbers")
    private List<Integer> importantNumbers;

    private AdditionResponse addition;
}
