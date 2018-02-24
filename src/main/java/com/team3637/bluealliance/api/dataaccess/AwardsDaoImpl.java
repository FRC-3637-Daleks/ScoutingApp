package com.team3637.bluealliance.api.dataaccess;

import javax.sql.DataSource;

import org.springframework.jdbc.core.JdbcTemplate;

import com.team3637.bluealliance.api.model.Award;

public class AwardsDaoImpl implements AwardsDao {

	private JdbcTemplate jdbcTemplateObject;

	public void setDataSource(DataSource dataSource) {
		this.jdbcTemplateObject = new JdbcTemplate(dataSource);
	}

	@Override
	public void updateInsertAward(Award award) {
		//@formatter:off
		String sql = "select count(*) from scoutingtags.blue_alliance_awards "
				+ "where name = ? and event_key = ? and year = ? and team = ?";
		//@formatter:on
		Integer count = jdbcTemplateObject.queryForObject(sql,
				new Object[] { award.getName(), award.getEvent(), award.getYear(), award.getTeam() }, Integer.class);
		if (count == 0) {
			//@formatter:off
			String insertSQL = "insert into scoutingtags.blue_alliance_awards (name, event_key, year, team) values (?,?,?,?)";
			//@formatter:on
			jdbcTemplateObject.update(insertSQL,
					new Object[] { award.getName(), award.getEvent(), award.getYear(), award.getTeam() });

		}
	}

}