package com.github.msnchk.models.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;

import java.util.List;

/**
 * Класс {@code EntityResponse} представляет собой модель ответа от API
 * при получении информации о сущности.
 */
@Getter
public class EntityResponse {

    /**
     * Уникальный идентификатор сущности.
     */
    private String id;
    /**
     * Название сущности.
     */
    private String title;

    /**
     * Флаг верификации.
     */
    private boolean verified;

    /**
     * Список важных чисел, полученных от API.
     */
    @JsonProperty("important_numbers")
    private List<Integer> importantNumbers;

    /**
     * Дополнительная информация, связанная с сущностью.
     */
    private AdditionResponse addition;
}
