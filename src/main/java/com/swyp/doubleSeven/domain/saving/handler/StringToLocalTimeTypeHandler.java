package com.swyp.doubleSeven.domain.saving.handler;

import org.apache.ibatis.type.BaseTypeHandler;
import org.apache.ibatis.type.JdbcType;
import org.apache.ibatis.type.MappedTypes;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

@MappedTypes(LocalTime.class)
public class StringToLocalTimeTypeHandler extends BaseTypeHandler<LocalTime> {

    @Override
    public LocalTime getNullableResult(ResultSet rs, String columnName) throws SQLException {
        String time = rs.getString(columnName);
        return time == null ? null : LocalTime.parse(time, DateTimeFormatter.ofPattern("HH:mm"));
    }

    @Override
    public void setNonNullParameter(PreparedStatement ps, int i, LocalTime parameter, JdbcType jdbcType) throws SQLException {

    }

    @Override
    public LocalTime getNullableResult(ResultSet rs, int columnIndex) throws SQLException {
        return null;
    }

    @Override
    public LocalTime getNullableResult(CallableStatement cs, int columnIndex) throws SQLException {
        return null;
    }
}

