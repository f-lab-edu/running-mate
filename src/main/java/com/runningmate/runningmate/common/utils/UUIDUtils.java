package com.runningmate.runningmate.common.utils;

import java.util.UUID;

public class UUIDUtils {

    public static String getUUID(){
        return UUID.randomUUID().toString().replaceAll("-", "");
    }

    public static String getUUID(String addValue){
        return UUID.randomUUID().toString().replaceAll("-", "") + addValue;
    }

}
