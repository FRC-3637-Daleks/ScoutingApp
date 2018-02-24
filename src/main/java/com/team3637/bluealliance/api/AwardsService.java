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

import com.team3637.bluealliance.api.dataaccess.AwardsDao;
import com.team3637.bluealliance.api.model.Award;
import com.team3637.bluealliance.api.model.AwardImport;
import com.team3637.bluealliance.api.model.AwardImport.AwardRecipient;
import com.team3637.service.TeamService;

@Repository
public class AwardsService {
	@Autowired
	protected RestTemplate restTemplate;

	@Autowired
	private AwardsDao awardsDao;

	@Autowired
	private TeamService teamService;

	public RestTemplate getRestTemplate() {
		return restTemplate;
	}

	public void setRestTemplate(RestTemplate restTemplate) {
		this.restTemplate = restTemplate;
	}

	public void loadAwards() {
		List<Integer> teams = teamService.getAllTeams();
		for (Integer team : teams) {
			loadAwards(team);
		}
	}

	public void loadAwards(Integer team) {
		HttpHeaders headers = new HttpHeaders();
		headers.add("X-TBA-Auth-Key", "RomsUyhrgfr6IYGj0YNKlY0PzmtASoIcZG5eKnZ1h3pU1H0DmdXrgQWVMPfgoD29");
		headers.add("User-Agent", "Mozilla/5.0 (Windows NT 6.1; WOW64; rv:25.0) Gecko/20100101 Firefox/25.0");

		HttpEntity<String> requestEntity = new HttpEntity<String>(headers);
		ResponseEntity<List<AwardImport>> awardResponse = restTemplate.exchange(
				"https://www.thebluealliance.com/api/v3/team/frc" + team + "/awards", HttpMethod.GET, requestEntity,
				new ParameterizedTypeReference<List<AwardImport>>() {
				});
		List<AwardImport> awards = awardResponse.getBody();
		for (AwardImport awardImport : awards) {
			for (AwardRecipient awardRecipient : awardImport.getRecipient_list()) {
				Integer teamKey = awardRecipient.getTeam();
				if (teamKey != null && teamKey.equals(team)) {
					Award award = new Award();
					award.setEvent(awardImport.getEvent_key());
					award.setName(awardImport.getName());
					award.setTeam(team);
					award.setYear(awardImport.getYear());
					awardsDao.updateInsertAward(award);
				}
			}
		}
	}
}
