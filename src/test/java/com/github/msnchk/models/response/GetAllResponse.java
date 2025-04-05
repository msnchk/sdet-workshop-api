package com.github.msnchk.models.response;

import lombok.Getter;

import java.util.List;

/**
 * Класс {@code GetAllResponse} представляет собой ответ API,
 * содержащий список сущностей и информацию о пагинации.
 */
@Getter
public class GetAllResponse {

    /**
     * Список полученных сущностей.
     */
    private List<EntityResponse> entity;

    /**
     * Номер текущей страницы.
     */
    private int page;

    /**
     * Количество сущностей на странице.
     */
    private int perPage;
}
