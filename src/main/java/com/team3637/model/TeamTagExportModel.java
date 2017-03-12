/*
 * Created on Mar 11, 2017
 *
 * To change the template for this generated file go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
package com.team3637.model;

import java.util.Date;

public class TeamTagExportModel
{
	private Integer team;
	private String tag;
	private Integer occurrences;
	private Date modifiedTimestamp;

	public Integer getTeam()
	{
		return team;
	}

	public void setTeam(Integer team)
	{
		this.team = team;
	}

	public String getTag()
	{
		return tag;
	}

	public void setTag(String tag)
	{
		this.tag = tag;
	}

	public Integer getOccurrences()
	{
		return occurrences;
	}

	public void setOccurrences(Integer occurrences)
	{
		this.occurrences = occurrences;
	}

	public Date getModifiedTimestamp()
	{
		return modifiedTimestamp;
	}

	public void setModifiedTimestamp(Date modifiedTimestamp)
	{
		this.modifiedTimestamp = modifiedTimestamp;
	}
}
