package com.github.msnchk.models.response;

import lombok.Getter;

import java.util.List;

@Getter
public class GetAllResponse {
    private List<EntityResponse> entity;
    private int page;
    private int perPage;
}
