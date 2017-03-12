
package com.team3637.model;

public class MatchStatistics
{
	private String grouping;
	private String category;
	private int totalOccurrences;
	private String tag;
	private Integer team;

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

	public String getGrouping()
	{
		return grouping;
	}

	public void setGrouping(String grouping)
	{
		this.grouping = grouping;
	}

	public String getCategory()
	{
		return category;
	}

	public void setCategory(String category)
	{
		this.category = category;
	}

	public int getTotalOccurrences()
	{
		return totalOccurrences;
	}

	public void setTotalOccurrences(int totalOccurrences)
	{
		this.totalOccurrences = totalOccurrences;
	}

}
