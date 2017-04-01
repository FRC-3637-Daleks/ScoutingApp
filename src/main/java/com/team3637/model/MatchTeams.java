package com.team3637.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MatchTeams
{
	private Integer match;
	private List<Team> teams = new ArrayList<Team>();
	private Boolean hasData = false;
	private String eventId;

	private Map<String, String> allianceMap = new HashMap<String, String>();

	public Map<String, String> getAllianceMap()
	{
		return allianceMap;
	}

	public void setAllianceMap(Map<String, String> allianceMap)
	{
		this.allianceMap = allianceMap;
	}

	public Integer getMatch()
	{
		return match;
	}

	public void setMatch(Integer match)
	{
		this.match = match;
	}

	public List<Team> getTeams()
	{
		return teams;
	}

	public void setTeams(List<Team> teams)
	{
		this.teams = teams;
	}

	public Boolean getHasData()
	{
		return hasData;
	}

	public void setHasData(Boolean hasData)
	{
		this.hasData = hasData;
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
