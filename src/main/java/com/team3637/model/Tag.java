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
package com.team3637.model;

public class Tag {
	private int id, isRankingPoint;
	private String tag, type;
	private String category, grouping, inputType;
	private Float pointValue;

	public Tag() {
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getTag() {
		return tag;
	}

	public void setTag(String tag) {
		this.tag = tag;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	@Override
	public String toString() {
		return tag;
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public String getGrouping() {
		return grouping;
	}

	public void setGrouping(String grouping) {
		this.grouping = grouping;
	}

	public String getInputType() {
		return inputType;
	}

	public void setInputType(String inputType) {
		this.inputType = inputType;
	}

	public Float getPointValue() {
		return pointValue;
	}

	public void setPointValue(Float pointValue) {
		this.pointValue = pointValue;
	}

	public int getIsRankingPoint() {
		return isRankingPoint;
	}

	public void setIsRankingPoint(int isRankingPoint) {
		this.isRankingPoint = isRankingPoint;

	}
}
