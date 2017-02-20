/*
 * Created on Feb 19, 2017
 *
 * To change the template for this generated file go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
package com.team3637.model;

public class TeamMatchResult {
	private Integer team;
	private Integer match;
	private Integer score;
	private Boolean win;
	private Boolean tie;
	private Boolean loss;
	private Integer rankingPoints;
	private Integer penalty;

	public Integer getRankingPoints() {
		return rankingPoints;
	}

	public void setRankingPoints(Integer rankingPoints) {
		this.rankingPoints = rankingPoints;
	}

	public Integer getPenalty() {
		return penalty;
	}

	public void setPenalty(Integer penalty) {
		this.penalty = penalty;
	}

	public Integer getTeam() {
		return team;
	}

	public void setTeam(Integer team) {
		this.team = team;
	}

	public Integer getMatch() {
		return match;
	}

	public void setMatch(Integer match) {
		this.match = match;
	}

	public Integer getScore() {
		return score;
	}

	public void setScore(Integer score) {
		this.score = score;
	}

	public Boolean getWin() {
		return win;
	}

	public void setWin(Boolean win) {
		this.win = win;
	}

	public Boolean getTie() {
		return tie;
	}

	public void setTie(Boolean tie) {
		this.tie = tie;
	}

	public Boolean getLoss() {
		return loss;
	}

	public void setLoss(Boolean loss) {
		this.loss = loss;
	}
}
