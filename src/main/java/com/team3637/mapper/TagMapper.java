/*Team 3637 Scouting App - An application for data collection/analytics at FIRST competitions
 Copyright (C) 2016  Team 3637

 This program is free software: you can redistribute it and/or modify
 it under the terms of the GNU General Public License as published by
 the Free Software Foundation, either version 3 of the License, or
 (at your option) any later version.

 This program is distributed in the hope that it will be useful,
 but WITHOUT ANY WARRANTY; without even the implied warranty of
 MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 GNU General Public License for more details.

 You should have received a copy of the GNU General Public License
 along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package com.team3637.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import com.team3637.model.Tag;

import org.springframework.jdbc.core.RowMapper;

public class TagMapper implements RowMapper<Tag>
{
	@Override
	public Tag mapRow(ResultSet resultSet, int i) throws SQLException
	{
		Tag tag = new Tag();
		tag.setId(resultSet.getInt("id"));
		tag.setTag(resultSet.getString("tag"));
		tag.setType(resultSet.getString("type"));
		tag.setCategory(resultSet.getString("category"));
		tag.setGrouping(resultSet.getString("grouping"));
		tag.setInputType(resultSet.getString("input_type"));
		tag.setPointValue(resultSet.getFloat("point_value"));
		return tag;
	}
}
