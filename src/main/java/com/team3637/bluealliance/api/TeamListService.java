/*
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

import com.team3637.bluealliance.api.dataaccess.TeamDao;
import com.team3637.bluealliance.api.model.TeamList;

@Repository
public class TeamListService {
	@Autowired
	protected RestTemplate restTemplate;

	@Autowired
	private TeamDao teamDao;

	public RestTemplate getRestTemplate() {
		return restTemplate;
	}

	public void setRestTemplate(RestTemplate restTemplate) {
		this.restTemplate = restTemplate;
	}

	public void loadTeamList() {
		int pageNum = 0;
		boolean con = true;
		while (con) {
			HttpHeaders headers = new HttpHeaders();
			headers.add("X-TBA-Auth-Key", "RomsUyhrgfr6IYGj0YNKlY0PzmtASoIcZG5eKnZ1h3pU1H0DmdXrgQWVMPfgoD29");
			headers.add("User-Agent", "Mozilla/5.0 (Windows NT 6.1; WOW64; rv:25.0) Gecko/20100101 Firefox/25.0");

			HttpEntity<String> requestEntity = new HttpEntity<String>(headers);
			ResponseEntity<List<TeamList>> teamResponse = restTemplate.exchange(
					"https://www.thebluealliance.com/api/v3/teams/" + pageNum, HttpMethod.GET, requestEntity,
					new ParameterizedTypeReference<List<TeamList>>() {
					});
			List<TeamList> team = teamResponse.getBody();
			if (team.isEmpty())
				con = false;
			else {

				for (TeamList teamList : team) {
					TeamList teamlist = new TeamList();
					teamlist.setTeam_number(teamList.getTeam_number());
					teamlist.setName(teamList.getName());
					teamlist.setCity(teamList.getCity());
					teamlist.setCountry(teamList.getCountry());
					teamlist.setRookie_year(teamList.getRookie_year());
					teamDao.updateInsertTeam(teamList);
				}
			}
			pageNum++;
		}
	}
}
