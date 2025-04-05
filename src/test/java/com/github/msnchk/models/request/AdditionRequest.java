package com.github.msnchk.models.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class AdditionRequest {
    @Builder.Default
    @JsonProperty("additional_info")
    private String additionalInfo = "Addition to entity";

    @Builder.Default
    @JsonProperty("additional_number")
    private int additionalNumber = 100;
}

