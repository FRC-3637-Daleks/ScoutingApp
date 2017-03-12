
package com.team3637.model;

public class TeamMatchTag
{
	private String grouping;
	private String category;
	private int occurrences;
	private String tag;
	private String inputType;

	public String getInputType()
	{
		return inputType;
	}

	public void setInputType(String inputType)
	{
		this.inputType = inputType;
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

	public int getOccurrences()
	{
		return occurrences;
	}

	public void setOccurrences(int occurrences)
	{
		this.occurrences = occurrences;
	}

}
