/*
 * Created on Mar 17, 2017
 *
 * To change the template for this generated file go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
package com.team3637.bluealliance.api;

import java.util.List;

import com.team3637.bluealliance.api.model.Match;
import com.team3637.service.ScheduleService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Repository;
import org.springframework.web.client.RestTemplate;

@Repository
public class EventMatchesService
{
	@Autowired
	protected RestTemplate restTemplate;

	@Autowired
	private ScheduleService scheduleService;

	public RestTemplate getRestTemplate()
	{
		return restTemplate;
	}

	public void setRestTemplate(RestTemplate restTemplate)
	{
		this.restTemplate = restTemplate;
	}

	public void loadEventMatches(String event)
	{
		HttpHeaders headers = new HttpHeaders();
		headers.add("X-TBA-App-Id", "3637:ScoutingApp:3");
		headers.add("User-Agent", "Mozilla/5.0 (Windows NT 6.1; WOW64; rv:25.0) Gecko/20100101 Firefox/25.0");

		HttpEntity<String> requestEntity = new HttpEntity<String>(headers);
		ResponseEntity<List<Match>> matchEventResponse = restTemplate.exchange(
				"https://www.thebluealliance.com/api/v2/event/" + event + "/matches", HttpMethod.GET, requestEntity,
				new ParameterizedTypeReference<List<Match>>()
				{
				});
		List<Match> matches = matchEventResponse.getBody();
		for (Match match : matches)
		{
			scheduleService.updateInsertMatch(match);
		}
	}
}
