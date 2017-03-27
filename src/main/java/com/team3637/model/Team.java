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

import java.util.List;

public class Team
{
	private Integer id;
	private Integer team;
	private Double avgScore;
	private Double ourScore;
	private Integer matches;
	private Integer wins;
	private Integer ties;
	private Integer losses;
	private List<MatchStatistics> matchStatistics;
	private List<TeamTag> teamTags;
	private Integer rankingpoints;
	private Integer tagsEntered;
	private String scoutingComments;

	public Integer getTagsEntered()
	{
		return tagsEntered;
	}

	public void setTagsEntered(Integer tagsEntered)
	{
		this.tagsEntered = tagsEntered;
	}

	public Integer getRankingpoints()
	{
		return rankingpoints;
	}

	public void setRankingpoints(Integer rankingpoints)
	{
		this.rankingpoints = rankingpoints;
	}

	public Integer getId()
	{
		return id;
	}

	public void setId(Integer id)
	{
		this.id = id;
	}

	public Integer getTeam()
	{
		return team;
	}

	public void setTeam(Integer team)
	{
		this.team = team;
	}

	public Double getAvgScore()
	{
		return avgScore;
	}

	public void setAvgScore(Double avgScore)
	{
		this.avgScore = avgScore;
	}

	public Double getOurScore()
	{
		return ourScore;
	}

	public void setOurScore(Double ourScore)
	{
		this.ourScore = ourScore;
	}

	public Integer getMatches()
	{
		return matches;
	}

	public void setMatches(Integer matches)
	{
		this.matches = matches;
	}

	public Integer getWins()
	{
		return wins;
	}

	public void setWins(Integer wins)
	{
		this.wins = wins;
	}

	public Integer getLosses()
	{
		return losses;
	}

	public void setLosses(Integer losses)
	{
		this.losses = losses;
	}

	public List<MatchStatistics> getMatchStatistics()
	{
		return matchStatistics;
	}

	public void setMatchStatistics(List<MatchStatistics> matchStatistics)
	{
		this.matchStatistics = matchStatistics;
	}

	@Override
	public String toString()
	{
		return Integer.toString(getTeam());
	}

	public Integer getTies()
	{
		return ties;
	}

	public void setTies(Integer ties)
	{
		this.ties = ties;
	}

	public List<TeamTag> getTeamTags()
	{
		return teamTags;
	}

	public void setTeamTags(List<TeamTag> teamTags)
	{
		this.teamTags = teamTags;
	}

	public String getScoutingComments()
	{
		return scoutingComments;
	}

	public void setScoutingComments(String scoutingComments)
	{
		this.scoutingComments = scoutingComments;
	}
}
