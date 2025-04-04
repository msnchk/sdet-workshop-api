package com.github.msnchk.models.request;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class Entity {
    @Builder.Default
    private String title = "My entity";

    @Builder.Default
    private boolean verified = false;

    @Builder.Default
    private List<Integer> importantNumbers = List.of(0, 11, 222, 3333);

    private Addition addition;
}
