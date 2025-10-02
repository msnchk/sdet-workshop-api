package com.github.msnchk.models.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

/**
 * Класс {@code AdditionRequest} представляет собой дополнительную информацию,
 * связанную с основной сущностью при отправке запроса.
 * Для всех полей задаются значения по умолчанию.
 */
@Data
@Builder
@AllArgsConstructor
public class AdditionRequest {

    /**
     * Дополнительная текстовая информация
     */
    @Builder.Default
    @JsonProperty("additional_info")
    private String additionalInfo = "Addition to entity";

    /**
     * Дополнительное числовое значение
     */
    @Builder.Default
    @JsonProperty("additional_number")
    private int additionalNumber = 100;
}

