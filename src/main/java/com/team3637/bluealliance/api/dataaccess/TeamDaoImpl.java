package com.team3637.bluealliance.api.dataaccess;

import javax.sql.DataSource;

import org.springframework.jdbc.core.JdbcTemplate;

import com.team3637.bluealliance.api.model.TeamList;

public class TeamDaoImpl implements TeamDao {

	private JdbcTemplate jdbcTemplateObject;

	public void setDataSource(DataSource dataSource) {
		this.jdbcTemplateObject = new JdbcTemplate(dataSource);
	}

	@Override
	public void updateInsertTeam(TeamList teamList) {
		//@formatter:off
		String sql = "select count(*) from scoutingtags.teams_list "
				+ "where team_number = ?";
		//@formatter:on
		Integer count = jdbcTemplateObject.queryForObject(sql, new Object[] { teamList.getTeam_number() },
				Integer.class);
		if (count == 0) {
			//@formatter:off
			String insertSQL = "insert into scoutingtags.teams_list (team_number, name, city, country, rookie_year) values (?,?,?,?,?)";
			//@formatter:on
			jdbcTemplateObject.update(insertSQL, new Object[] { teamList.getTeam_number(), teamList.getName(),
					teamList.getCity(), teamList.getCountry(), teamList.getRookie_year() });

		}
	}

}