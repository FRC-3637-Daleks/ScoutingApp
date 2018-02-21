package com.team3637.model;

import java.util.List;

public class TeamAwards {
	Integer team;
	List<Award> awards;

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
	}
}
