package com.github.msnchk.models.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;

@Data
public class EntityResponse {
    private int id;
    private String title;
    private boolean verified;

    @JsonProperty("important_numbers")
    private List<Integer> importantNumbers;

    private AdditionResponse addition;
}
