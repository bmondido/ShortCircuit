package com.attask.jenkins;

import hudson.model.Result;

import java.lang.reflect.Field;

/**
 * User: brianmondido
 * Date: 8/21/12
 * Time: 1:10 PM
 */
public class ResultUtil {

    /**
     * Utility method using reflection to get the all[] field from hudson.model.Result
     *
     * @return
     */
    public static Result[] getResultNames() {
        Result[] results = null;

        Class resultClass = Result.class;

        try {
            Field all = resultClass.getDeclaredField("all");
            all.setAccessible(true);
            Object allFieldObj = all.get(null);
            if (allFieldObj instanceof Result[]) {
                results = (Result[]) allFieldObj;
            }
        } catch (NoSuchFieldException e) {
            throw new RuntimeException(e);
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        }

        return results;
    }
}
