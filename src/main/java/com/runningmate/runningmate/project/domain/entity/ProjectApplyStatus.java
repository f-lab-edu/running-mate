package com.runningmate.runningmate.project.domain.entity;

import com.runningmate.runningmate.common.handler.ProjectApplyStatusTypeHandler;
import lombok.Getter;
import org.apache.ibatis.type.MappedTypes;

public enum ProjectApplyStatus {
    WAIT(0),
    APPROVE(1),
    REJECT(2);

    @Getter
    private final int code;

    ProjectApplyStatus(int code) {
        this.code = code;
    }

    @MappedTypes(ProjectApplyStatus.class)
    public static class TypeHandler extends ProjectApplyStatusTypeHandler<ProjectApplyStatus> {
        public TypeHandler() {
            super(ProjectApplyStatus.class);
        }
    }
}
