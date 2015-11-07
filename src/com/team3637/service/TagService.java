package com.team3637.service;

import com.team3637.model.Match;
import com.team3637.model.Tag;
import com.team3637.model.Team;

import javax.sql.DataSource;
import java.util.List;

public interface TagService extends Service {
    void setDataSource(DataSource dataSource);

    void create(Tag tag);

    Tag getTag(Integer id);

    Tag getTagByName(String name);

    List<Tag> getTags();

    List<Team> search(String[] matchTags, String[] teamTags);

    List<Team> search(Double minScore, Double maxScore, String[] matchTags, String[] teamTags);

    List<Match> searchMatches(String... params);

    List<Match> searchMatches(Double minScore, Double maxScore, String... params);

    List<Team> searchTeams(String... params);

    List<Team> searchTeams(Double minScore, Double maxScore, String... params);

    List<String> getMatchTagsForTeam(Integer teamNum);

    void update(Tag tag);

    void deleteById(Integer id);

    void delete(String name);

    boolean checkForId(Integer id);

    boolean checkForTag(Tag tag);

    void mergeTags(Tag oldTag, Tag newTag);
}
