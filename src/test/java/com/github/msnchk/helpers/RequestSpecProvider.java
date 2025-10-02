package com.github.msnchk.helpers;

import io.restassured.builder.RequestSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;

/**
 * Класс {@code RequestSpecProvider} предоставляет типовую спецификацию запроса
 * для использования в API-тестах с RestAssured.
 */
public class RequestSpecProvider {
    private static final String baseUrl = PropertyProvider.getInstance().getProperty("api.base.url");

    /**
     * Приватный конструктор, чтобы не создавать экземпляры напрямую.
     */
    private RequestSpecProvider() {}

    /**
     * Возвращает спецификацию запроса с базовыми настройками:
     * Content-Type: application/json
     * Accept: application/json
     * Base URI: из файла properties
     *
     * @return объект {@code RequestSpecification}
     */
    public static RequestSpecification newRequestSpec() {
        return new RequestSpecBuilder()
                .setContentType(ContentType.JSON)
                .setBaseUri(baseUrl)
                .setAccept(ContentType.JSON)
                .build();
    }
}
