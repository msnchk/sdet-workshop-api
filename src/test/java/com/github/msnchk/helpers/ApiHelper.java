package com.github.msnchk.helpers;

import com.github.msnchk.models.request.EntityRequest;
import com.github.msnchk.models.response.EntityResponse;
import io.restassured.RestAssured;
import io.restassured.mapper.ObjectMapperType;
import io.restassured.specification.RequestSpecification;

import java.util.ArrayList;
import java.util.List;

public class ApiHelper {

    private static final String createPath = PropertyProvider.getInstance().getProperty("api.create.path");
    private static final String deletePath = PropertyProvider.getInstance().getProperty("api.delete.path");
    private static final String getPath = PropertyProvider.getInstance().getProperty("api.get.path");
    private static final RequestSpecification requestSpecification = RequestSpecProvider.newRequestSpec();

    private ApiHelper() {
    }

    public static ApiHelper newHelper() {
        return new ApiHelper();
    }

    public String createEntity(EntityRequest entity) {
        return RestAssured.given()
                .spec(requestSpecification)
                .body(entity)
                .when()
                .post(createPath)
                .then()
                .statusCode(200)
                .extract().asString();
    }

    public List<String> createEntities(List<EntityRequest> entities) {
        List<String> ids = new ArrayList<>();
        for (EntityRequest entity : entities) {
            ids.add(createEntity(entity));
        }
        return ids;
    }

    public List<EntityRequest> generateListOfTaggedEntities(String title){
        List<EntityRequest> entities = new ArrayList<>();
        for (int i = 0; i < 3; i++) {
            EntityRequest entity = EntityRequest.builder().build();
            entity.setTitle(title);
            entities.add(entity);
        }
        return entities;
    }

    public void deleteEntity(String entityId) {
        RestAssured.given()
                .log().all()
                .spec(requestSpecification)
                .when()
                .delete(deletePath + "/" + entityId)
                .then()
                .statusCode(204);
    }

    public EntityResponse getEntity(String entityId) {
        return RestAssured.given()
                .spec(requestSpecification)
                .when()
                .get(getPath + "/" + entityId)
                .then()
                .statusCode(200)
                .extract().as(EntityResponse.class, ObjectMapperType.JACKSON_2);
    }
}
