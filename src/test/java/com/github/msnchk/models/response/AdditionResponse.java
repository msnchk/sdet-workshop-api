package com.github.msnchk.models.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;

/**
 * Класс {@code AdditionResponse} представляет собой дополнительную информацию,
 * возвращаемую в составе ответа на запрос сущности.
 */
@Getter
public class AdditionResponse {

    /**
     * Уникальный идентификатор дополнительного объекта.
     */
    private String id;

    /**
     * Текстовое описание дополнительной информации.
     */
    @JsonProperty("additional_info")
    private String additionalInfo;

    /**
     * Числовое значение дополнительной информации.
     */
    @JsonProperty("additional_number")
    private int additionalNumber;
}
