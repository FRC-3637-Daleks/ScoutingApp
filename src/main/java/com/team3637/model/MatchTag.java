package com.team3637.model;

public class MatchTag {

	private Integer team;
	private String tag;
	private Integer match;
	private Integer occurrences;
	private String eventId;
	private Integer isRankingPoint;

	public Integer getIsRankingPoint() {
		return isRankingPoint;
	}

	public void setIsRankingPoint(Integer isRankingPoint) {
		this.isRankingPoint = isRankingPoint;
	}

	public Integer getTeam() {
		return team;
	}

	public void setTeam(Integer team) {
		this.team = team;
	}

	public String getTag() {
		return tag;
	}

	public void setTag(String tag) {
		this.tag = tag;
	}

	public Integer getMatch() {
		return match;
	}

	public void setMatch(Integer match) {
		this.match = match;
	}

	public Integer getOccurrences() {
		return occurrences;
	}

	public void setOccurrences(Integer occurrences) {
		this.occurrences = occurrences;
	}

	public String getEventId() {
		return eventId;
	}

	public void setEventId(String eventId) {
		this.eventId = eventId;
	}

}
