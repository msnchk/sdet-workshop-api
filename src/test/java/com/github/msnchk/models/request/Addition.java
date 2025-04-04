package com.github.msnchk.models.request;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Addition {
    @Builder.Default
    private String additionalInfo = "Addition to entity";
    @Builder.Default
    private int additionalNumber = 100;
}

