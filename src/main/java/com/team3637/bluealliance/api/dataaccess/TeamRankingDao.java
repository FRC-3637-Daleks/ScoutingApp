package com.team3637.bluealliance.api.dataaccess;

import javax.sql.DataSource;

import com.team3637.bluealliance.api.model.TeamRanking;

public interface TeamRankingDao {

	void updateInsertTeamRanking(TeamRanking teamRanking);

	int update(TeamRanking teamRanking);

	void setDataSource(DataSource dataSource);

	void create(TeamRanking teamRanking);

}
