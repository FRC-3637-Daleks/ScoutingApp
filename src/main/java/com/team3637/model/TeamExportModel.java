package com.team3637.model;

import java.util.Date;

public class TeamExportModel
{

	private Integer team;
	private String name;
	private String scoutingComments;
	private Date modifiedTimestamp;
	private String eventId;

	public Date getModifiedTimestamp()
	{
		return modifiedTimestamp;
	}

	public void setModifiedTimestamp(Date modifiedTimestamp)
	{
		this.modifiedTimestamp = modifiedTimestamp;
	}

	public Integer getTeam()
	{
		return team;
	}

	public void setTeam(Integer team)
	{
		this.team = team;
	}

	public String getName()
	{
		return name;
	}

	public void setName(String name)
	{
		this.name = name;
	}

	public String getScoutingComments()
	{
		return scoutingComments;
	}

	public void setScoutingComments(String scoutingComments)
	{
		this.scoutingComments = scoutingComments;
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
