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
package com.team3637.service;

import com.team3637.model.Team;

import javax.sql.DataSource;
import java.util.List;

public interface TeamService extends Service {
    void setDataSource(DataSource dataSource);

    void create(Team team);

    Team getTeam(Integer id);

    Team getTeamById(Integer id);

    List<Team> getTeams();

    Team getTeamByNumber(Integer teamNum);

    Double[] getScoreRange();

    Integer[] getScoreRangeFor(Team team);

    void update(Team team);

    void delete(Team team);

    boolean checkForId(Integer id);

    boolean checkForTeam(Integer id);

    List<String> getTags();

    void mergeTags(String oldTag, String newTag);
}
