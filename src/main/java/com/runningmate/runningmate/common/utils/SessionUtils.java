package com.runningmate.runningmate.common.utils;

import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpSession;
/**
 * Session 관련 클래스
 * 
 * @author junsoo
 */
public class SessionUtils {
    private static final String LOGIN_USER_EMAIL = "LOGIN_USER_EMAIL";
    private static final ServletRequestAttributes servletRequestAttributes = (ServletRequestAttributes) RequestContextHolder.currentRequestAttributes();
    /**
     * 객체생성 막기
     * @author junsoo
     */
    private SessionUtils(){
    }

    /**
     * 세션정보 가져오기
     * 
     * @param getStr 
     * @return session정보
     * @author junsoo
     */
    public static Object getSession(String getStr){
        return servletRequestAttributes.getRequest().getSession().getAttribute(getStr);
    }

    /**
     * 세션 등록
     * 
     * @param setStr 
     * @param setObject
     * @author junsoo
     */
    public static void setSetsession(String setStr, Object setObject){
        HttpSession httpSession = servletRequestAttributes.getRequest().getSession();
        httpSession.setAttribute(setStr, setObject);
    }

    /**
     * 모든 세션 삭제
     * 
     * @author junsoo
     */
    public static void clear(){
        servletRequestAttributes.getRequest().getSession().invalidate();
    }

    /**
     * 특정 세션정보 삭제
     *
     * @param removeStr
     * @author junsoo
     * 
     */
    public static void sessionRemoveAttribute(String removeStr){
        servletRequestAttributes.getRequest().getSession().removeAttribute(removeStr);
    }

    /**
     * 로그인 세션 등록
     *
     * @param email
     * @author junsoo
     */
    public static void setLoginSessionEmail(String email){
        setSetsession(LOGIN_USER_EMAIL, email);
    }


    /**
     * 세션 등록된 이메일 정보
     *
     * @return 세션 저장되어있는 email
     * @author junsoo
     */
    public static String getLoginSessionEmail(){
        return StringUtils.getStringSafeFromObj(getSession(LOGIN_USER_EMAIL));
    }

    /**
     * 로그아웃 세션 클리어
     *
     * @author junsoo
     */
    public static void logoutSession(){
        sessionRemoveAttribute(LOGIN_USER_EMAIL);
    }

}
