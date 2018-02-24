/*
 * Created on Mar 17, 2017
 *
 * To change the template for this generated file go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
package com.team3637.bluealliance.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Repository;
import org.springframework.web.client.RestTemplate;

import com.team3637.bluealliance.api.dataaccess.TeamRankingDao;
import com.team3637.bluealliance.api.model.TeamRanking;
import com.team3637.bluealliance.api.model.TeamRankingImport;
import com.team3637.bluealliance.api.model.TeamRankingImport.Rankings;

@Repository
public class TeamRankingService {
	@Autowired
	protected RestTemplate restTemplate;

	@Autowired
	private TeamRankingDao teamRankingDao;

	public RestTemplate getRestTemplate() {
		return restTemplate;
	}

	public void setRestTemplate(RestTemplate restTemplate) {
		this.restTemplate = restTemplate;
	}

	public void loadTeamRankings(String event) {
		HttpHeaders headers = new HttpHeaders();
		headers.add("X-TBA-Auth-Key", "RomsUyhrgfr6IYGj0YNKlY0PzmtASoIcZG5eKnZ1h3pU1H0DmdXrgQWVMPfgoD29");
		headers.add("User-Agent", "Mozilla/5.0 (Windows NT 6.1; WOW64; rv:25.0) Gecko/20100101 Firefox/25.0");

		HttpEntity<String> requestEntity = new HttpEntity<String>(headers);
		ResponseEntity<TeamRankingImport> teamRankingResponse = restTemplate.exchange(
				"https://www.thebluealliance.com/api/v3/event/" + event + "/rankings", HttpMethod.GET, requestEntity,
				new ParameterizedTypeReference<TeamRankingImport>() {
				});
		TeamRankingImport teamRankingImports = teamRankingResponse.getBody();
		for (Rankings ranking : teamRankingImports.getRankings()) {
			TeamRanking teamRanking = new TeamRanking();
			teamRanking.setTeam(Integer.parseInt(ranking.getTeam_key().substring(3)));
			teamRanking.setEventId(event);
			teamRanking.setDisqualifications(ranking.getDq());
			teamRanking.setMatchesPlayed(ranking.getMatches_played());
			teamRanking.setQualAverage(ranking.getQual_average());
			teamRanking.setRank(ranking.getRank());
			teamRanking.setWins(ranking.getRecord().getWins());
			teamRanking.setTies(ranking.getRecord().getTies());
			teamRanking.setLosses(ranking.getRecord().getLosses());
			teamRankingDao.updateInsertTeamRanking(teamRanking);
		}
	}
}
