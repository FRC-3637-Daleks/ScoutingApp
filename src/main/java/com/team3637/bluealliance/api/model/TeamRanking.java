package com.team3637.bluealliance.api.model;

public class TeamRanking {

	private String eventId;
	private Integer team;
	private Integer matchesPlayed;
	private Integer qualAverage;
	private Integer rank;
	private Integer disqualifications;
	private Integer wins;
	private Integer losses;
	private Integer ties;

	public String getEventId() {
		return eventId;
	}

	public void setEventId(String eventId) {
		this.eventId = eventId;
	}

	public Integer getTeam() {
		return team;
	}

	public void setTeam(Integer team) {
		this.team = team;
	}

	public Integer getMatchesPlayed() {
		return matchesPlayed;
	}

	public void setMatchesPlayed(Integer matchesPlayed) {
		this.matchesPlayed = matchesPlayed;
	}

	public Integer getQualAverage() {
		return qualAverage;
	}

	public void setQualAverage(Integer qualAverage) {
		this.qualAverage = qualAverage;
	}

	public Integer getRank() {
		return rank;
	}

	public void setRank(Integer rank) {
		this.rank = rank;
	}

	public Integer getDisqualifications() {
		return disqualifications;
	}

	public void setDisqualifications(Integer disqualifications) {
		this.disqualifications = disqualifications;
	}

	public Integer getWins() {
		return wins;
	}

	public void setWins(Integer wins) {
		this.wins = wins;
	}

	public Integer getLosses() {
		return losses;
	}

	public void setLosses(Integer losses) {
		this.losses = losses;
	}

	public Integer getTies() {
		return ties;
	}

	public void setTies(Integer ties) {
		this.ties = ties;
	}

}
