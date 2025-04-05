package com.github.msnchk.tests;

import com.github.msnchk.helpers.ApiHelper;
import org.testng.annotations.AfterClass;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public abstract class BaseApiTest {

    protected final List<String> entitiesToCleanup =  Collections.synchronizedList(new ArrayList<>());

    @AfterClass
    public void cleanupEntities() {
        for (String id : entitiesToCleanup) {
            ApiHelper.newHelper().deleteEntity(id);
        }
        entitiesToCleanup.clear();
    }
}
