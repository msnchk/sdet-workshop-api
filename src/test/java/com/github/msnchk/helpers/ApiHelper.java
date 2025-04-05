package com.github.msnchk.helpers;

import com.github.msnchk.models.request.EntityRequest;
import com.github.msnchk.models.response.EntityResponse;
import io.restassured.RestAssured;
import io.restassured.mapper.ObjectMapperType;
import io.restassured.specification.RequestSpecification;

import java.util.ArrayList;
import java.util.List;

/**
 * Класс {@code ApiHelper} предоставляет вспомогательные методы
 * для взаимодействия с API: создание, получение, удаление сущностей.
 * <p>
 * Использует {@code RestAssured} для отправки HTTP-запросов.
 */
public class ApiHelper {
    private static final String createPath = PropertyProvider.getInstance().getProperty("api.create.path");
    private static final String deletePath = PropertyProvider.getInstance().getProperty("api.delete.path");
    private static final String getPath = PropertyProvider.getInstance().getProperty("api.get.path");
    private static final RequestSpecification requestSpecification = RequestSpecProvider.newRequestSpec();

    /**
     * Приватный конструктор, чтобы не создавались экземпляры напрямую.
     */
    private ApiHelper() {
    }

    /**
     * Метод для создания нового экземпляра {@code ApiHelper}.
     *
     * @return новый экземпляр {@code ApiHelper}
     */
    public static ApiHelper newHelper() {
        return new ApiHelper();
    }

    /**
     * Создаёт сущность через API и возвращает её ID.
     *
     * @param entity объект запроса {@code EntityRequest}
     * @return строка с ID созданной сущности
     */
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

    /**
     * Принимает на вход список сущностей, создаёт их через API и возвращает идентификаторы.
     *
     * @param entities список объектов {@code EntityRequest}
     * @return список строк с ID созданных сущностей
     */
    public List<String> createEntities(List<EntityRequest> entities) {
        List<String> ids = new ArrayList<>();
        for (EntityRequest entity : entities) {
            ids.add(createEntity(entity));
        }
        return ids;
    }

    /**
     * Генерирует список сущностей с одинаковым заголовком (для теста запроса getAll)
     *
     * @param title заголовок для всех сущностей
     * @return список сгенерированных сущностей
     */
    public List<EntityRequest> generateListOfTaggedEntities(String title){
        List<EntityRequest> entities = new ArrayList<>();
        for (int i = 0; i < 3; i++) {
            EntityRequest entity = EntityRequest.builder().build();
            entity.setTitle(title);
            entities.add(entity);
        }
        return entities;
    }

    /**
     * Удаляет сущность по идентификатору
     *
     * @param entityId ID сущности
     */
    public void deleteEntity(String entityId) {
        RestAssured.given()
                .log().all()
                .spec(requestSpecification)
                .when()
                .delete(deletePath + "/" + entityId)
                .then()
                .statusCode(204);
    }

    /**
     * Получает сущность по её ID.
     *
     * @param entityId ID сущности
     * @return объект {@code EntityResponse}, представляющий сущность
     */
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
