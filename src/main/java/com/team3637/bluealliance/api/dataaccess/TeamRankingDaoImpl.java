package com.team3637.bluealliance.api.dataaccess;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

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

	@Override
	public void updateLastLoadTime(String event) {
		String updateSQL = "update scoutingtags.event set ba_rankings_load_time = CURRENT_TIMESTAMP where event_id = ?";
		jdbcTemplateObject.update(updateSQL, event);
	}

	@Override
	public List<TeamRanking> getTeamRankings(String eventId) {
		//@formatter:off
		String SQL = "select rank, team, matches_played, qual_average, disqualifications, wins, losses, ties from scoutingtags.blue_alliance_rankings "
				   + "where event_id = ? "
				   + "order by rank asc";
		//@formatter:on
		return jdbcTemplateObject.query(SQL, new Object[] { eventId }, new RowMapper<TeamRanking>() {

			@Override
			public TeamRanking mapRow(ResultSet resultSet, int rowNum) throws SQLException {

				TeamRanking teamRanking = new TeamRanking();
				teamRanking.setTeam(resultSet.getInt("team"));
				teamRanking.setMatchesPlayed(resultSet.getInt("matches_played"));
				teamRanking.setQualAverage(resultSet.getInt("qual_average"));
				teamRanking.setRank(resultSet.getInt("rank"));
				teamRanking.setDisqualifications(resultSet.getInt("disqualifications"));
				teamRanking.setWins(resultSet.getInt("wins"));
				teamRanking.setLosses(resultSet.getInt("losses"));
				teamRanking.setTies(resultSet.getInt("ties"));
				return teamRanking;
			}
		});
	}

}
