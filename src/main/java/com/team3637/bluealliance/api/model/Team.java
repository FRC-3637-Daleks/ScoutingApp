/*
 * Created on Mar 31, 2017
 *
 * To change the template for this generated file go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
package com.team3637.bluealliance.api.model;

public class Team
{
	private Integer team_number;
	private String nickname;
	private String eventId;

	public Integer getTeam_number()
	{
		return team_number;
	}

	public void setTeam_number(Integer team_number)
	{
		this.team_number = team_number;
	}

	public String getNickname()
	{
		return nickname;
	}

	public void setNickname(String nickname)
	{
		this.nickname = nickname;
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
