package com.team3637.mapper;

import com.team3637.model.Tag;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class TagMapper implements RowMapper<Tag> {
    @Override
    public Tag mapRow(ResultSet resultSet, int i) throws SQLException {
        Tag tag = new Tag();
        tag.setId(resultSet.getInt("id"));
        tag.setTag(resultSet.getString("tag"));
        tag.setType(resultSet.getString("type"));
        tag.setCategory(resultSet.getString("category"));
        tag.setExpression(resultSet.getString("expression"));
        return tag;
    }
}
