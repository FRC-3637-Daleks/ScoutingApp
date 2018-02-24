package com.team3637.bluealliance.api.dataaccess;

import javax.sql.DataSource;

import org.springframework.jdbc.core.JdbcTemplate;

import com.team3637.bluealliance.api.model.TeamRanking;

public class TeamRankingDaoImpl implements TeamRankingDao {

	private JdbcTemplate jdbcTemplateObject;

	@Override
	public void setDataSource(DataSource dataSource) {
		this.jdbcTemplateObject = new JdbcTemplate(dataSource);
	}

	@Override
	public void updateInsertTeamRanking(TeamRanking teamRanking) {
		if (update(teamRanking) == 0) {
			create(teamRanking);
		}
	}

	@Override
	public int update(TeamRanking teamRanking) {
		String updateSQL = "update scoutingtags.blue_alliance_rankings set matches_played = ?, qual_average = ?, rank = ?, disqualifications = ?, wins = ?, losses = ?, ties = ? where team = ? and event_id = ?";
		return jdbcTemplateObject.update(updateSQL, teamRanking.getMatchesPlayed(), teamRanking.getQualAverage(),
				teamRanking.getRank(), teamRanking.getDisqualifications(), teamRanking.getWins(),
				teamRanking.getLosses(), teamRanking.getTies(), teamRanking.getTeam(), teamRanking.getEventId());
	}

	@Override
	public void create(TeamRanking teamRanking) {
		String SQL = "INSERT INTO blue_alliance_rankings (team, matches_played, qual_average, rank, disqualifications, wins, losses, ties, event_id) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?);";
		jdbcTemplateObject.update(SQL, teamRanking.getTeam(), teamRanking.getMatchesPlayed(),
				teamRanking.getQualAverage(), teamRanking.getRank(), teamRanking.getDisqualifications(),
				teamRanking.getWins(), teamRanking.getLosses(), teamRanking.getTies(), teamRanking.getEventId());
	}

}
