package com.github.msnchk.models.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
public class EntityRequest {
    @Builder.Default
    private String title = "My entity";

    @Builder.Default
    private boolean verified = false;

    @Builder.Default
    @JsonProperty("important_numbers")
    private List<Integer> importantNumbers = List.of(0, 11, 222, 3333);

    @Builder.Default
    private AdditionRequest addition = AdditionRequest.builder().build();;
}
