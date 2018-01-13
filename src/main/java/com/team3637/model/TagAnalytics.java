package com.team3637.model;

import java.util.List;

public class TagAnalytics {

	private Tag tag;
	private List<TagAnalyticsTeamData> topScoringTeams;

	public Tag getTag() {
		return tag;
	}

	public void setTag(Tag tag) {
		this.tag = tag;
	}

	public List<TagAnalyticsTeamData> getTopScoringTeams() {
		return topScoringTeams;
	}

	public void setTopScoringTeams(List<TagAnalyticsTeamData> topScoringTeams) {
		this.topScoringTeams = topScoringTeams;
	}

}
