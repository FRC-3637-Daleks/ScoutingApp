package com.team3637.service;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

import com.team3637.model.Award;

public class AwardsDaoImpl implements AwardsDao {
	private JdbcTemplate jdbcTemplateObject;

	public void setDataSource(DataSource dataSource) {
		this.jdbcTemplateObject = new JdbcTemplate(dataSource);
	}

	@Override
	public List<Award> getAwardsForTeam(Integer team) {
		//@formatter:off
		String SQL = "select name, event_key, year from scoutingtags.blue_alliance_awards "
					+ "where team = ? "
					+ "order by year desc";
		//@formatter:on
		return jdbcTemplateObject.query(SQL, new Object[] { team }, new RowMapper<Award>() {

			@Override
			public Award mapRow(ResultSet resultSet, int rowNum) throws SQLException {

				Award award = new Award();
				award.setName(resultSet.getString("name"));
				award.setEventId(resultSet.getString("event_key"));
				award.setYear(resultSet.getInt("year"));
				return award;
			}
		});

	}

}
