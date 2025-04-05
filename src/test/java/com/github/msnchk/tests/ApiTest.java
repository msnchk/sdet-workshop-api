package com.github.msnchk.tests;

import com.github.msnchk.helpers.ApiHelper;
import com.github.msnchk.helpers.PropertyProvider;
import com.github.msnchk.helpers.RequestSpecProvider;
import com.github.msnchk.models.request.AdditionRequest;
import com.github.msnchk.models.request.EntityRequest;
import com.github.msnchk.models.response.EntityResponse;
import com.github.msnchk.models.response.GetAllResponse;
import io.qameta.allure.*;
import io.restassured.RestAssured;
import io.restassured.mapper.ObjectMapperType;
import io.restassured.specification.RequestSpecification;
import org.testng.Assert;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import java.util.List;

/**
 * Класс {@code ApiTest} содержит набор автотестов для проверки
 * CRUD-операций над сущностями через API.
 * <p>
 * Используется RestAssured и аннотации Allure для структурирования отчётов.
 */
@Epic("API Tests")
@Feature("Entity Management")
public class ApiTest extends BaseApiTest {
    private final String createPath = PropertyProvider.getInstance().getProperty("api.create.path");
    private final String deletePath = PropertyProvider.getInstance().getProperty("api.delete.path");
    private final String getPath = PropertyProvider.getInstance().getProperty("api.get.path");
    private final String getAllPath = PropertyProvider.getInstance().getProperty("api.getAll.path");
    private final String patchPath = PropertyProvider.getInstance().getProperty("api.patch.path");
    private final RequestSpecification requestSpecification = RequestSpecProvider.newRequestSpec();
    private final ApiHelper apiHelper = ApiHelper.newHelper();

    /**
     * Проверяет успешное создание сущности и соответствие полученных данных отправленным.
     */
    @Test
    @Story("Create entity")
    @Description("Verify that an entity can be created successfully")
    @Severity(SeverityLevel.CRITICAL)
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
        var entityResponse = apiHelper.getEntity(id);
        SoftAssert softAssert = new SoftAssert();
        assertEntitiesEquals(softAssert, entity, entityResponse);
        softAssert.assertAll();
        entitiesToCleanup.add(id);
    }

    /**
     * Проверяет, что после удаления сущность больше не доступна через GET-запрос.
     */
    @Test
    @Story("Delete entity")
    @Description("Verify that an entity can be deleted and no longer retrieved")
    @Severity(SeverityLevel.CRITICAL)
    public void shouldDeleteEntity() {
        var entity = EntityRequest.builder().build();
        String id = apiHelper.createEntity(entity);
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

    /**
     * Проверяет, что созданная сущность может быть успешно получена по ID.
     */
    @Test
    @Story("Get entity")
    @Description("Verify that a specific entity can be retrieved after creation")
    @Severity(SeverityLevel.CRITICAL)
    public void shouldGetEntity() {
        var entity = EntityRequest.builder().build();
        String id = apiHelper.createEntity(entity);
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

    /**
     * Проверяет получение всех сущностей по заданному фильтру.
     * <p>
     * Для теста создаются сущности с одинаковым заголовком, чтобы исключить влияние
     * существующих в базе данных записей. Затем выполняется GET-запрос с фильтром по заголовку,
     * и сравниваются списки отправленных и полученных сущностей.
     */
    @Test
    @Story("Get all entities")
    @Description("Verify that all created entities with a specific tag can be retrieved")
    @Severity(SeverityLevel.CRITICAL)
    public void shouldGetAllEntities() {
        String tag = "getAllTest";
        List<EntityRequest> entities = apiHelper.generateListOfTaggedEntities(tag);
        List<String> ids = apiHelper.createEntities(entities);
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

    /**
     * Проверяет, что сущность может быть обновлена через PATCH,
     * и что изменения сохраняются корректно.
     */
    @Test
    @Story("Patch entity")
    @Description("Verify that an entity can be updated using PATCH and retrieved correctly")
    @Severity(SeverityLevel.CRITICAL)
    public void shouldPatchEntity() {
        String id = apiHelper.createEntity(EntityRequest.builder().build());
        var updatedEntity = new EntityRequest("New entity", true, List.of(12, 1000, 14, 2, 2),
                new AdditionRequest("New info", 554));
        RestAssured.given()
                .spec(requestSpecification)
                .body(updatedEntity)
                .when()
                .patch(patchPath + "/" + id)
                .then()
                .statusCode(204);
        var entityResponse = apiHelper.getEntity(id);
        SoftAssert softAssert = new SoftAssert();
        assertEntitiesEquals(softAssert, updatedEntity, entityResponse);
        softAssert.assertAll();
        entitiesToCleanup.add(id);
    }

    /**
     * Сравнивает поля запроса {@code EntityRequest} и ответа {@code EntityResponse}.
     *
     * @param softAssert объект для мягких проверок
     * @param entity     отправленная сущность
     * @param entityResponse полученная сущность
     */
    public void assertEntitiesEquals(SoftAssert softAssert, EntityRequest entity, EntityResponse entityResponse) {
        softAssert.assertEquals(entity.getTitle(), entityResponse.getTitle(), "Поле title не совпадает");
        softAssert.assertEquals(entity.isVerified(), entityResponse.isVerified(), "Поле verified не совпадает");
        softAssert.assertEquals(entity.getImportantNumbers(), entityResponse.getImportantNumbers(), "Поле importantNumbers не совпадает");
        softAssert.assertEquals(entity.getAddition().getAdditionalInfo(), entityResponse.getAddition().getAdditionalInfo(), "Поле additionalInfo не совпадает");
        softAssert.assertEquals(entity.getAddition().getAdditionalNumber(), entityResponse.getAddition().getAdditionalNumber(), "Поле additionalNumber не совпадает");
    }
}