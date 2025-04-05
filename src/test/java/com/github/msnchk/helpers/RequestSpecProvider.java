package com.github.msnchk.helpers;

import io.restassured.builder.RequestSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;

public class RequestSpecProvider {

    private static final String baseUrl = PropertyProvider.getInstance().getProperty("api.base.url");

    private RequestSpecProvider() {}

    public static RequestSpecification newRequestSpec() {
        return new RequestSpecBuilder()
                .setContentType(ContentType.JSON)
                .setBaseUri(baseUrl)
                .setAccept(ContentType.JSON)
                .build();
    }
}
