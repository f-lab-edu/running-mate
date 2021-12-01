package com.runningmate.runningmate.aop;

import java.lang.annotation.*;

/**
 * 로그인 상태 확인
 * 매니저와 일반 유저 체크
 * @author junsoo 
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface LoginCheck {

    /**
     * 코드를 관리(Skill, Position 등)하는 매니저와
     * 프로젝트를 생성/관리/참여 등을 할 수 있는 유저
     *
     * @return
     * @author junsoo
     */
    UserLevel userLevel();

    public enum UserLevel{
        MANAGER, CUSTOMER;

        public static UserLevel getEnumLevel(String level) {
            return Enum.valueOf(UserLevel.class, level);
        }
    }

}
