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

public class Schedule
{

	public Schedule()
	{
	}

	public Schedule(Integer matchNum)
	{
		this.matchNum = matchNum;
	}

	private Integer id;

	private Integer matchNum = null;

	private Integer b1 = null;

	private Integer b2 = null;

	private Integer b3 = null;

	private Integer r1 = null;

	private Integer r2 = null;

	private Integer r3 = null;
	private String eventId;

	public Integer getId()
	{
		return id;
	}

	public void setId(Integer id)
	{
		this.id = id;
	}

	public Integer getMatchNum()
	{
		return matchNum;
	}

	public void setMatchNum(Integer matchNum)
	{
		this.matchNum = matchNum;
	}

	public Integer getB1()
	{
		return b1;
	}

	public void setB1(Integer b1)
	{
		this.b1 = b1;
	}

	public Integer getB2()
	{
		return b2;
	}

	public void setB2(Integer b2)
	{
		this.b2 = b2;
	}

	public Integer getB3()
	{
		return b3;
	}

	public void setB3(Integer b3)
	{
		this.b3 = b3;
	}

	public Integer getR1()
	{
		return r1;
	}

	public void setR1(Integer r1)
	{
		this.r1 = r1;
	}

	public Integer getR2()
	{
		return r2;
	}

	public void setR2(Integer r2)
	{
		this.r2 = r2;
	}

	public Integer getR3()
	{
		return r3;
	}

	public void setR3(Integer r3)
	{
		this.r3 = r3;
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
