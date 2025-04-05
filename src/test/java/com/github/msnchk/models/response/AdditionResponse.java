package com.github.msnchk.models.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;

@Getter
public class AdditionResponse {
    private String id;

    @JsonProperty("additional_info")
    private String additionalInfo;

    @JsonProperty("additional_number")
    private int additionalNumber;
}
