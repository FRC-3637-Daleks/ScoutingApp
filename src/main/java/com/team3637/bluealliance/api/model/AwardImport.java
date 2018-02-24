package com.team3637.bluealliance.api.model;

import java.util.List;

public class AwardImport {

	public class AwardRecipient {
		private String team_key;

		public String getTeam_key() {
			return team_key;
		}

		public void setTeam_key(String team_key) {
			this.team_key = team_key;
		}

		public Integer getTeam() {
			if (team_key == null || team_key.length() < 3)
				return null;
			else {
				try {
					return new Integer(team_key.substring(3));
				} catch (NumberFormatException e) {
					return null;
				}
			}
		}
	}

	private String name;
	private String event_key;
	private List<AwardRecipient> recipient_list;
	private Integer year;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getEvent_key() {
		return event_key;
	}

	public void setEvent_key(String event_key) {
		this.event_key = event_key;
	}

	public List<AwardRecipient> getRecipient_list() {
		return recipient_list;
	}

	public void setRecipient_list(List<AwardRecipient> recipient_list) {
		this.recipient_list = recipient_list;
	}

	public Integer getYear() {
		return year;
	}

	public void setYear(Integer year) {
		this.year = year;
	}
}
