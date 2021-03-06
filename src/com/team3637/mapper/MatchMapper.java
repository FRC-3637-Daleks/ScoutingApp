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

import org.springframework.jdbc.core.RowMapper;
import com.team3637.model.Match;

import java.lang.reflect.Field;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;

public class MatchMapper implements RowMapper<Match> {
    @Override
    public Match mapRow(ResultSet resultSet, int rowNum) throws SQLException {
        Match match = new Match();
        match.setId(resultSet.getInt("id"));
        match.setMatchNum(resultSet.getInt("matchNum"));
        match.setTeam(resultSet.getInt("team"));
        match.setScore(resultSet.getInt("score"));
        ResultSetMetaData rsmd = resultSet.getMetaData();
        int columns = rsmd.getColumnCount();
        for(int i = 5; i <= columns; i++) {
            if(resultSet.getString(i) != null) {
                match.getTags().add(resultSet.getString(i));
            }
        }
        return match;
    }
}
