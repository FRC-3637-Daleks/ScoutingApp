/*
 * Created on Mar 17, 2017
 *
 * To change the template for this generated file go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
package com.team3637.bluealliance.api;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Repository;
import org.springframework.web.client.RestTemplate;

import com.team3637.bluealliance.api.model.Match;
import com.team3637.bluealliance.api.model.Team;
import com.team3637.model.TeamExportModel;
import com.team3637.service.ScheduleService;
import com.team3637.service.TeamService;

@Repository
public class EventMatchesService {
	@Autowired
	protected RestTemplate restTemplate;

	@Autowired
	private ScheduleService scheduleService;

	@Autowired
	private TeamService teamService;

	public RestTemplate getRestTemplate() {
		return restTemplate;
	}

	public void setRestTemplate(RestTemplate restTemplate) {
		this.restTemplate = restTemplate;
	}

	public void loadEventMatches(String event) {
		HttpHeaders headers = new HttpHeaders();
		headers.add("X-TBA-Auth-Key", "RomsUyhrgfr6IYGj0YNKlY0PzmtASoIcZG5eKnZ1h3pU1H0DmdXrgQWVMPfgoD29");
		headers.add("User-Agent", "Mozilla/5.0 (Windows NT 6.1; WOW64; rv:25.0) Gecko/20100101 Firefox/25.0");

		HttpEntity<String> requestEntity = new HttpEntity<String>(headers);
		ResponseEntity<List<Match>> matchEventResponse = restTemplate.exchange(
				"https://www.thebluealliance.com/api/v3/event/" + event + "/matches", HttpMethod.GET, requestEntity,
				new ParameterizedTypeReference<List<Match>>() {
				});
		List<Match> matches = matchEventResponse.getBody();
		for (Match match : matches) {
			match.setEventId(event);
			scheduleService.updateInsertMatch(match);
		}
	}

	public void loadEventTeams(String event) {
		HttpHeaders headers = new HttpHeaders();
		headers.add("X-TBA-Auth-Key", "RomsUyhrgfr6IYGj0YNKlY0PzmtASoIcZG5eKnZ1h3pU1H0DmdXrgQWVMPfgoD29");
		headers.add("User-Agent", "Mozilla/5.0 (Windows NT 6.1; WOW64; rv:25.0) Gecko/20100101 Firefox/25.0");

		HttpEntity<String> requestEntity = new HttpEntity<String>(headers);
		ResponseEntity<List<Team>> teamsEventResponse = restTemplate.exchange(
				"https://www.thebluealliance.com/api/v3/event/" + event + "/teams", HttpMethod.GET, requestEntity,
				new ParameterizedTypeReference<List<Team>>() {
				});
		List<Team> teams = teamsEventResponse.getBody();
		for (Team team : teams) {
			TeamExportModel teamExportModel = new TeamExportModel();
			teamExportModel.setEventId(event);
			teamExportModel.setTeam(team.getTeam_number());
			teamExportModel.setName(team.getName());
			teamService.updateInsertTeam(teamExportModel);
		}
	}
}