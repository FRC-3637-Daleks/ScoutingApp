/*
 * Created on Mar 11, 2017
 *
 * To change the template for this generated file go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
package com.team3637.service;

import java.util.List;

import javax.sql.DataSource;

import com.team3637.model.MatchTag;
import com.team3637.model.MatchTagExportModel;

public interface MatchTagService extends Service
{
	void setDataSource(DataSource dataSource);

	List<MatchTagExportModel> getMatchTagsForExport();

	List<MatchTag> getMatchTags(Integer team, String tag, String event);

	String getDefaultEvent();

}
