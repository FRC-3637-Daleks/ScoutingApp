package com.team3637.model;

import java.util.List;

public class TeamAwards {
	Integer team;
	List<Award> awards;
	Integer awardCount;

	public Integer getAwardCount() {
		return awardCount;
	}

	public void setAwardCount(Integer awardCount) {
		this.awardCount = awardCount;
	}

	public Integer getTeam() {
		return team;
	}

	public void setTeam(Integer team) {
		this.team = team;
	}

	public List<Award> getAwards() {
		return awards;
	}

	public void setAwards(List<Award> awards) {
		this.awards = awards;
		awardCount = awards.size();
	}
}
