package com.runningmate.runningmate.common.handler;

import com.runningmate.runningmate.project.domain.entity.ProjectApplyStatus;
import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import org.apache.ibatis.type.JdbcType;
import org.apache.ibatis.type.TypeException;
import org.apache.ibatis.type.TypeHandler;

public class ProjectApplyStatusTypeHandler<E extends Enum<E>> implements TypeHandler<ProjectApplyStatus> {

    private final Class<E> type;

    public ProjectApplyStatusTypeHandler(Class<E> type) {
        this.type = type;
    }

    @Override
    public void setParameter(PreparedStatement preparedStatement, int i,
        ProjectApplyStatus projectApplyStatus, JdbcType jdbcType) throws SQLException {
        preparedStatement.setInt(i, projectApplyStatus.getCode());
    }

    @Override
    public ProjectApplyStatus getResult(ResultSet resultSet, String s) throws SQLException {
        return getProjectApplyStatus(resultSet.getInt(s));
    }

    @Override
    public ProjectApplyStatus getResult(ResultSet resultSet, int i) throws SQLException {
        return getProjectApplyStatus(resultSet.getInt(i));
    }

    @Override
    public ProjectApplyStatus getResult(CallableStatement callableStatement, int i)
        throws SQLException {
        return getProjectApplyStatus(callableStatement.getInt(i));
    }

    private ProjectApplyStatus getProjectApplyStatus(int code) {
        try {
            ProjectApplyStatus[] enumConstants = (ProjectApplyStatus[]) type.getEnumConstants();
            for (ProjectApplyStatus projectApplyStatus : enumConstants) {
                if (projectApplyStatus.getCode() == code) {
                    return projectApplyStatus;
                }
            }
            return null;
        } catch (Exception exception) {
            throw new TypeException();
        }
    }
}
