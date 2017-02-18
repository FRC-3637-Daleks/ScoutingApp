package com.team3637.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MatchTeams {
	private Integer match;
	private List<Team> teams = new ArrayList<Team>();

	private Map<Integer, String> allianceMap = new HashMap<Integer, String>();

	public Map<Integer, String> getAllianceMap() {
		return allianceMap;
	}

	public void setAllianceMap(Map<Integer, String> allianceMap) {
		this.allianceMap = allianceMap;
	}

	public Integer getMatch() {
		return match;
	}

	public void setMatch(Integer match) {
		this.match = match;
	}

	public List<Team> getTeams() {
		return teams;
	}

	public void setTeams(List<Team> teams) {
		this.teams = teams;
	}
}
