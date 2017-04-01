/*
 * Created on Mar 17, 2017
 *
 * To change the template for this generated file go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
package com.team3637.bluealliance.api.model;

import java.util.HashMap;
import java.util.List;

public class Match
{
	private String comp_level;
	private Integer match_number;
	private Integer set_number;
	private String eventId;
	private HashMap<String, Alliance> alliances = new HashMap<String, Alliance>();

	public class Alliance
	{
		private Integer score;
		private List<String> teams;

		public Integer getScore()
		{
			return score;
		}

		public void setScore(Integer score)
		{
			this.score = score;
		}

		public List<String> getTeams()
		{
			return teams;
		}

		public void setTeams(List<String> teams)
		{
			this.teams = teams;
		}
	}

	public String getComp_level()
	{
		return comp_level;
	}

	public void setComp_level(String comp_level)
	{
		this.comp_level = comp_level;
	}

	public Integer getMatch_number()
	{
		return match_number;
	}

	public void setMatch_number(Integer match_number)
	{
		this.match_number = match_number;
	}

	public HashMap<String, Alliance> getAlliances()
	{
		return alliances;
	}

	public void setAlliances(HashMap<String, Alliance> alliances)
	{
		this.alliances = alliances;
	}

	public Integer getSet_number()
	{
		return set_number;
	}

	public void setSet_number(Integer set_number)
	{
		this.set_number = set_number;
	}

	public String getEventId()
	{
		return eventId;
	}

	public void setEventId(String eventId)
	{
		this.eventId = eventId;
	}

}
