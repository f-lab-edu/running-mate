package com.runningmate.runningmate.common.utils;

import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpSession;

/**
 * Session 관련 클래스
 * 
 * @author junsoo
 */
public class SessionUtils{
    private static final String LOGIN_USER_ID = "LOGIN_USER_ID";


    /**
     * 객체생성 막기
     * @author junsoo
     */
    private SessionUtils(){
    }

    public static HttpSession getSession(){
        ServletRequestAttributes servletRequestAttributes = (ServletRequestAttributes) RequestContextHolder.currentRequestAttributes();
        return servletRequestAttributes.getRequest().getSession();
    }

    public static void sessionClear(){
        SessionUtils.getSession().invalidate();
    }

    public static void removeAttribute(String sessionKey){
        SessionUtils.getSession().removeAttribute(sessionKey);
    }


    /**
     * 로그인 세션 등록
     *
     * @param userId
     * @author junsoo
     */
    public static void setLoginSessionUserId(long userId){
        SessionUtils.getSession().setAttribute(LOGIN_USER_ID, userId);
    }

    /**
     * 세션 등록된 유저 고유 ID 정보
     *
     * @return 세션 저장되어있는 userId
     * @author junsoo
     */
    public static long getLoginSessionUserId(){ return Long.valueOf(StringUtils.getStringSafeFromObj(SessionUtils.getSession().getAttribute(LOGIN_USER_ID))); }

    /**
     * 로그아웃 세션 클리어
     *
     * @author junsoo
     */
    public static void logoutSession(){ SessionUtils.removeAttribute(LOGIN_USER_ID); }

    /**
     *  모든 세션 클리어
     *
     * @author junsoo
     */
    public static void clear(){
        SessionUtils.sessionClear();
    }
}
