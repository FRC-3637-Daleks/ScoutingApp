package com.team3637.bluealliance.api.model;

public class TeamList {

	private Integer team_number;
	private String city;
	private String name;
	private String country;
	private Integer rookie_year;

	public Integer getTeam_number() {
		return team_number;
	}

	public void setTeam_number(Integer team_number) {
		this.team_number = team_number;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public Integer getRookie_year() {
		return rookie_year;
	}

	public void setRookie_year(Integer rookie_year) {
		this.rookie_year = rookie_year;
	}
}
