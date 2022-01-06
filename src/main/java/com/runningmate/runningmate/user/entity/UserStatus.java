package com.runningmate.runningmate.user.entity;

import com.runningmate.runningmate.common.handler.UserStatusTypeHandler;
import lombok.Getter;
import org.apache.ibatis.type.MappedTypes;

public enum UserStatus {
    USE(1),
    DELETE(0);

    @Getter
    private final int code;

    UserStatus(int code) {
        this.code = code;
    }

    @MappedTypes(UserStatus.class)
    public static class TypeHandler extends UserStatusTypeHandler<UserStatus> {
        public TypeHandler() {
            super(UserStatus.class);
        }
    }
}
