package com.team3637.bluealliance.api.model;

import java.util.List;

public class TeamRankingImport {

	public class Record {

		private Integer wins;
		private Integer losses;
		private Integer ties;

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

	public class Rankings {

		private Integer dq;
		private Integer matches_played;
		private Integer qual_average;
		private Integer rank;
		private String team_key;
		private List<Record> record;

		public Integer getDq() {
			return dq;
		}

		public void setDq(Integer dq) {
			this.dq = dq;
		}

		public Integer getMatches_played() {
			return matches_played;
		}

		public void setMatches_played(Integer matches_played) {
			this.matches_played = matches_played;
		}

		public Integer getQual_average() {
			return qual_average;
		}

		public void setQual_average(Integer qual_average) {
			this.qual_average = qual_average;
		}

		public Integer getRank() {
			return rank;
		}

		public void setRank(Integer rank) {
			this.rank = rank;
		}

		public String getTeam_key() {
			return team_key;
		}

		public void setTeam_key(String team_key) {
			this.team_key = team_key;
		}

		public List<Record> getRecord() {
			return record;
		}

		public void setRecord(List<Record> record) {
			this.record = record;
		}

	}

	private List<Rankings> rankings;

	public List<Rankings> getRankings() {
		return rankings;
	}

	public void setRankings(List<Rankings> rankings) {
		this.rankings = rankings;
	}

}
