package com.github.msnchk.tests;

import com.github.msnchk.helpers.ApiHelper;
import org.testng.annotations.AfterClass;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Абстрактный базовый класс для API-тестов.
 * Обеспечивает автоматическую очистку созданных сущностей после выполнения тестов.
 */
public abstract class BaseApiTest {

    /**
     * Список идентификаторов сущностей, подлежащих удалению после тестов.
     */
    protected final List<String> entitiesToCleanup =  Collections.synchronizedList(new ArrayList<>());

    /**
     * Удаляет все сущности из {@code entitiesToCleanup} по завершении всех тестов в классе.
     */
    @AfterClass
    public void cleanupEntities() {
        for (String id : entitiesToCleanup) {
            ApiHelper.newHelper().deleteEntity(id);
        }
        entitiesToCleanup.clear();
    }
}
