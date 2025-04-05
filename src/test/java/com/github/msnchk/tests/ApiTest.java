package com.github.msnchk.tests;

import com.github.msnchk.helpers.ApiHelper;
import com.github.msnchk.helpers.PropertyProvider;
import com.github.msnchk.helpers.RequestSpecProvider;
import com.github.msnchk.models.request.AdditionRequest;
import com.github.msnchk.models.request.EntityRequest;
import com.github.msnchk.models.response.EntityResponse;
import com.github.msnchk.models.response.GetAllResponse;
import io.restassured.RestAssured;
import io.restassured.mapper.ObjectMapperType;
import io.restassured.specification.RequestSpecification;
import org.testng.Assert;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import java.util.List;

public class ApiTest extends BaseApiTest {
    private final String createPath = PropertyProvider.getInstance().getProperty("api.create.path");
    private final String deletePath = PropertyProvider.getInstance().getProperty("api.delete.path");
    private final String getPath = PropertyProvider.getInstance().getProperty("api.get.path");
    private final String getAllPath = PropertyProvider.getInstance().getProperty("api.getAll.path");
    private final String patchPath = PropertyProvider.getInstance().getProperty("api.patch.path");
    private final RequestSpecification requestSpecification = RequestSpecProvider.newRequestSpec();

    @Test
    public void shouldCreateEntity() {
        var entity = EntityRequest.builder().build();
        String id = RestAssured.given()
                .spec(requestSpecification)
                .body(entity)
                .when()
                .post(createPath)
                .then()
                .statusCode(200)
                .extract().asString();
        entitiesToCleanup.add(id);
    }

    @Test
    public void shouldDeleteEntity() {
        var entity = EntityRequest.builder().build();
        String id = ApiHelper.newHelper().createEntity(entity);
        RestAssured.given()
                .spec(requestSpecification)
                .when()
                .delete(deletePath + "/" + id)
                .then()
                .statusCode(204);
        RestAssured.given()
                .spec(requestSpecification)
                .when()
                .get(getPath + "/" + id)
                .then()
                .statusCode(500);
    }

    @Test
    public void shouldGetEntity() {
        var entity = EntityRequest.builder().build();
        String id = ApiHelper.newHelper().createEntity(entity);
        var entityResponse = RestAssured.given()
                .spec(requestSpecification)
                .when()
                .get(getPath + "/" + id)
                .then()
                .statusCode(200)
                .extract().as(EntityResponse.class, ObjectMapperType.JACKSON_2);
        SoftAssert softAssert = new SoftAssert();
        assertEntitiesEquals(softAssert, entity, entityResponse);
        softAssert.assertAll();
        entitiesToCleanup.add(id);
    }

    @Test
    public void shouldGetAllEntities() {
        String tag = "getAllTest5";
        List<EntityRequest> entities = ApiHelper.newHelper().generateListOfTaggedEntities(tag);
        List<String> ids = ApiHelper.newHelper().createEntities(entities);
        GetAllResponse response = RestAssured.given()
                .spec(requestSpecification)
                .when()
                .formParam("title", tag)
                .formParam("page", 1)
                .get(getAllPath)
                .then()
                .statusCode(200)
                .extract().as(GetAllResponse.class);
        List<EntityResponse> responseEntities = response.getEntity();
        Assert.assertEquals(entities.size(), responseEntities.size(), "Не совпадает размер списков отправленных и полученных сущностей");
        SoftAssert softAssert = new SoftAssert();
        for (int i = 0; i < entities.size(); i++) {
            assertEntitiesEquals(softAssert, entities.get(i), responseEntities.get(i));
        }
        softAssert.assertAll();
        entitiesToCleanup.addAll(ids);
    }
    @Test
    public void shouldPatchEntity() {
        String id = ApiHelper.newHelper().createEntity(EntityRequest.builder().build());
        var updatedEntity = new EntityRequest("New entity", true, List.of(12, 1000, 14, 2, 2),
                new AdditionRequest("New info", 554));
        RestAssured.given()
                .spec(requestSpecification)
                .body(updatedEntity)
                .when()
                .patch(patchPath + "/" + id)
                .then()
                .statusCode(204);
        var entityResponse = ApiHelper.newHelper().getEntity(id);
        SoftAssert softAssert = new SoftAssert();
        assertEntitiesEquals(softAssert, updatedEntity, entityResponse);
        softAssert.assertAll();
        entitiesToCleanup.add(id);
    }

    public void assertEntitiesEquals(SoftAssert softAssert, EntityRequest entity, EntityResponse entityResponse) {
        softAssert.assertEquals(entity.getTitle(), entityResponse.getTitle(), "Поле title не совпадает");
        softAssert.assertEquals(entity.isVerified(), entityResponse.isVerified(), "Поле verified не совпадает");
        softAssert.assertEquals(entity.getImportantNumbers(), entityResponse.getImportantNumbers(), "Поле importantNumbers не совпадает");
        softAssert.assertEquals(entity.getAddition().getAdditionalInfo(), entityResponse.getAddition().getAdditionalInfo(), "Поле additionalInfo не совпадает");
        softAssert.assertEquals(entity.getAddition().getAdditionalNumber(), entityResponse.getAddition().getAdditionalNumber(), "Поле additionalNumber не совпадает");
    }
}
