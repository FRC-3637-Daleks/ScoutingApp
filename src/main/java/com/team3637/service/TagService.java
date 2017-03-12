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

import java.util.List;

import javax.sql.DataSource;

import com.team3637.model.Tag;

public interface TagService extends Service
{
	void setDataSource(DataSource dataSource);

	Tag getTag(Integer id);

	Tag getTagByName(String name);

	List<Tag> getTags();

	void deleteTagById(Integer id);

	void deleteTag(String name);

	boolean checkTagForId(Integer id);

	boolean checkForTag(Tag tag);

	void mergeTags(Tag oldTag, Tag newTag);

	void createTag(Tag tag);

	List<Tag> getTeamTags();

	List<Tag> getMatchTags();

	void deleteTag(Integer id);

	List<String> getTeamTagGroupings();

	List<String> getMatchTagGroupings();

	void updateInsertTag(Tag tag);

	Integer saveTag(Integer id, String tag, String type, String category, String grouping, String inputType,
			Float pointValue);
}
