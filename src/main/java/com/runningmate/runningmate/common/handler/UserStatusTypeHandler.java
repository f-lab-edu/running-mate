package com.runningmate.runningmate.common.handler;

import com.runningmate.runningmate.user.entity.UserStatus;
import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import org.apache.ibatis.type.JdbcType;
import org.apache.ibatis.type.TypeException;
import org.apache.ibatis.type.TypeHandler;

public class UserStatusTypeHandler<E extends Enum<E>> implements TypeHandler<UserStatus> {

    private final Class<E> type;

    public UserStatusTypeHandler(Class<E> type) {
        this.type = type;
    }

    @Override
    public void setParameter(PreparedStatement preparedStatement, int i, UserStatus userStatus,
        JdbcType jdbcType) throws SQLException {
        preparedStatement.setInt(i, userStatus.getCode());
    }

    @Override
    public UserStatus getResult(ResultSet resultSet, String s) throws SQLException {
        return getUserStatus(resultSet.getInt(s));
    }

    @Override
    public UserStatus getResult(ResultSet resultSet, int i) throws SQLException {
        return getUserStatus(resultSet.getInt(i));
    }

    @Override
    public UserStatus getResult(CallableStatement callableStatement, int i) throws SQLException {
        return getUserStatus(callableStatement.getInt(i));
    }

    private UserStatus getUserStatus(int code) {
        try {
            UserStatus[] enumConstants = (UserStatus[]) type.getEnumConstants();
            for (UserStatus userStatus : enumConstants) {
                if (userStatus.getCode() == code) {
                    return userStatus;
                }
            }
            return null;
        } catch (Exception exception) {
            throw new TypeException();
        }
    }
}
