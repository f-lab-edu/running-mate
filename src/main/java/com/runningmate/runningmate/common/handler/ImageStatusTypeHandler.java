package com.runningmate.runningmate.common.handler;

import com.runningmate.runningmate.image.domain.entity.ImageStatus;
import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import org.apache.ibatis.type.JdbcType;
import org.apache.ibatis.type.TypeException;
import org.apache.ibatis.type.TypeHandler;

public class ImageStatusTypeHandler<E extends Enum<E>> implements TypeHandler<ImageStatus> {

    private final Class<E> type;

    public ImageStatusTypeHandler(Class<E> type) {
        this.type = type;
    }

    @Override
    public void setParameter(PreparedStatement preparedStatement, int i, ImageStatus imageStatus,
        JdbcType jdbcType) throws SQLException {
        preparedStatement.setInt(i, imageStatus.getCode());
    }

    @Override
    public ImageStatus getResult(ResultSet resultSet, String s) throws SQLException {
        return getImageStatus(resultSet.getInt(s));
    }

    @Override
    public ImageStatus getResult(ResultSet resultSet, int i) throws SQLException {
        return getImageStatus(resultSet.getInt(i));
    }

    @Override
    public ImageStatus getResult(CallableStatement callableStatement, int i) throws SQLException {
        return getImageStatus(callableStatement.getInt(i));
    }

    private ImageStatus getImageStatus(int code) {
        try {
            ImageStatus[] enumConstants = (ImageStatus[]) type.getEnumConstants();
            for (ImageStatus imageStatus : enumConstants) {
                if (imageStatus.getCode() == code) {
                    return imageStatus;
                }
            }
            return null;
        } catch (Exception exception) {
            throw new TypeException();
        }
    }
}
