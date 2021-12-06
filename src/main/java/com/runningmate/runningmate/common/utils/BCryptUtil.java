package com.runningmate.runningmate.common.utils;

import org.mindrot.jbcrypt.BCrypt;

/**
 * 암호화 관련 클래스
 * 
 * @author junsoo 
 */
public class BCryptUtil {

    /**
     *  비밀번호 암호화
     * 
     * @param password 
     * @return
     * 
     * @author junsoo
     */
    public static String setEncrypt(String password) {
        return BCrypt.hashpw(password, BCrypt.gensalt());
    }

    /**
     * 비밀번호 비교 후 진위 여부
     * 
     * @param password 
     * @param hashPassword
     * @return 
     * 
     * @author junsoo
     */
    public static boolean comparePassword(String password, String hashPassword) {
        return BCrypt.checkpw(password, hashPassword);
    }
}
