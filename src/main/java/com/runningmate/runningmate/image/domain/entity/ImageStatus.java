package com.runningmate.runningmate.image.domain.entity;

import com.runningmate.runningmate.common.handler.ImageStatusTypeHandler;
import lombok.Getter;
import org.apache.ibatis.type.MappedTypes;

public enum ImageStatus {
    BEING_USED(1),
    NOT_USED(0);

    @Getter
    private final int code;

    ImageStatus(int code) {
        this.code = code;
    }

    @MappedTypes(ImageStatus.class)
    public static class TypeHandler extends ImageStatusTypeHandler<ImageStatus> {
        public TypeHandler() {
            super(ImageStatus.class);
        }
    }
}
