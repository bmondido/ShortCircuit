package com.attask.jenkins;

import hudson.model.Result;

import java.lang.reflect.Field;

/**
 * Created with IntelliJ IDEA.
 * User: brianmondido
 * Date: 8/21/12
 * Time: 1:10 PM
 * To change this template use File | Settings | File Templates.
 */
public class ResultUtil {
    public static Result[] getResultNames(){
        Result[] results=null;

        Class resultClass=Result.class;

        try {
            Field all = resultClass.getDeclaredField("all");
            all.setAccessible(true);
            Object allFieldObj = all.get(null);
            if(allFieldObj instanceof Result[]){
                results= (Result[]) allFieldObj;
            }
            if(results!=null){
                for (Result result : results) {
                    System.out.println(result.toString());
                }
            }
            else{
                System.out.println("Results is null!");
            }
        } catch (NoSuchFieldException e) {
            throw new RuntimeException(e);
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        }

        return results;
    }
}
