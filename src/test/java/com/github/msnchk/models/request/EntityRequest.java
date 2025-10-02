package com.github.msnchk.models.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.util.List;

/**
 * Класс {@code EntityRequest} представляет собой модель запроса для создания сущности.
 * Содержит основную и дополнительную информацию.
 * Для всех полей задаются значения по умолчанию.
 */
@Data
@Builder
@AllArgsConstructor
public class EntityRequest {

    /**
     * Название сущности.
     */
    @Builder.Default
    private String title = "My entity";

    /**
     * Флаг верификации сущности.
     */
    @Builder.Default
    private boolean verified = false;

    /**
     * Список важных чисел.
     */
    @Builder.Default
    @JsonProperty("important_numbers")
    private List<Integer> importantNumbers = List.of(0, 11, 222, 3333);

    /**
     * Объект дополнительной информации. Поля по умолчанию задаются в классе {@code AdditionRequest}.
     */
    @Builder.Default
    private AdditionRequest addition = AdditionRequest.builder().build();;
}
