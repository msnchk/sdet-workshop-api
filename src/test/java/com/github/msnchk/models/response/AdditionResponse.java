package com.github.msnchk.models.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class AdditionResponse {
    private int id;

    @JsonProperty("additional_info")
    private String additionalInfo;

    @JsonProperty("additional_number")
    private int additionalNumber;
}
