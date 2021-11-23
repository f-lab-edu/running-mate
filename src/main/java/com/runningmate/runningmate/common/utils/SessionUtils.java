package com.runningmate.runningmate.common.utils;

import javax.servlet.http.HttpSession;

/**
 * Session 관련 클래스
 * 
 * @author junsoo
 */
public class SessionUtils {
    private static final String LOGIN_USER_ID = "LOGIN_USER_ID";


    /**
     * 객체생성 막기
     * @author junsoo
     */
    private SessionUtils(){
    }


    /**
     * 로그인 세션 등록
     *
     * @param session
     * @param email
     * @author junsoo
     */
    public static void setLoginSessionEmail(HttpSession session, String email){
        session.setAttribute(LOGIN_USER_ID, email);
    }

    /**
     * 세션 등록된 이메일 정보
     * 
     * @param session 
     * @return 세션 저장되어있는 email
     * @author junsoo
     */
    public static String getLoginSessionEmail(HttpSession session){
        return StringUtils.getStringSafeFromObj( session.getAttribute(LOGIN_USER_ID) );
    }

    /**
     * 로그아웃 세션 클리어
     * 
     * @param session 
     * @author junsoo
     */
    public static void logoutSession(HttpSession session){
        session.removeAttribute(LOGIN_USER_ID);
    }

    /**
     *  모든 세션 클리어
     * @param session 
     * @author junsoo
     */
    public static void clear(HttpSession session){
        session.invalidate();
    }


}
