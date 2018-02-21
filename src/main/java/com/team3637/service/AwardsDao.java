package com.team3637.service;

import java.util.List;

import com.team3637.model.Award;

public interface AwardsDao {

	List<Award> getAwardsForTeam(Integer team);

}
