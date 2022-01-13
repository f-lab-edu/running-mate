package com.runningmate.runningmate.common.utils;

import java.util.*;

/**
 * String 변환 관련 클래스
 * 
 * @author junsoo 
 */
class StringUtils {


    /**
     * Object형으로 해당값이 null인지 체크하여 ""를 리턴한다.
     * @param obj
     * @return
     * @author junsoo
     */
    public static String getStringSafeFromObj(Object obj) {
        return Optional.ofNullable(obj).map(String::valueOf).orElse("");
    }

    /**
     * 해당 String이 null일 경우 "NULL"로 채워서 리턴한다.
     * @param Str
     * @return
     * @author junsoo
     */
    public static String getStringNULL(String Str) {
        return Optional.ofNullable(Str).orElse("NULL");
    }


    /**
     * 해당 String에 값이 존재하는지 확인.
     * @param Str
     * @return false 존재하지 않음. true 존재함.
     * @author junsoo
     */
    public static boolean CheckStrExist(String Str) {
        return Str != null && Str.length() > 0;
    }

    /**
     *  Object가 String 형태로 저장 되어있는지 여부
     *
     * @param obj
     * @return
     * @author junsoo
     */
    public static boolean CheckObjExist(Object obj) {
        boolean bval = false;
        if (obj != null) {
            if (obj instanceof String) {
                bval = CheckStrExist((String)obj);
            } else {
                bval = true;
            }
        }
        return bval;
    }


}