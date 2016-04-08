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

import com.team3637.model.Match;
import com.team3637.model.Tag;
import com.team3637.model.Team;

import javax.sql.DataSource;
import java.util.List;

public interface TagService extends Service {
    void setDataSource(DataSource dataSource);

    void create(Tag tag);

    Tag getTag(Integer id);

    Tag getTagByName(String name);

    List<Tag> getTags();

    List<Team> search(String[] matchTags, String[] teamTags);

    List<Team> search(Double minScore, Double maxScore, String[] matchTags, String[] teamTags);

    List<Match> searchMatches(String... params);

    List<Match> searchMatches(Double minScore, Double maxScore, String... params);

    List<Team> searchTeams(String... params);

    List<Team> searchTeams(Double minScore, Double maxScore, String... params);

    List<String> getMatchTagStringsForTeam(Integer teamNum);

    void update(Tag tag);

    void deleteById(Integer id);

    void delete(String name);

    boolean checkForId(Integer id);

    boolean checkForTag(Tag tag);

    void mergeTags(Tag oldTag, Tag newTag);
}
