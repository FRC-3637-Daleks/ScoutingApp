package com.team3637.service;

import com.team3637.model.Match;
import com.team3637.model.Tag;
import com.team3637.model.Team;

import javax.sql.DataSource;
import java.util.List;

public interface TagService {
    void setDataSource(DataSource dataSource);

    void create(Tag tag);

    Tag getTag(Integer id);

    Tag getTagByName(String name);

    List<Tag> getTags();

    List<Integer> search(String[] matchTags, String[] teamTags);

    List<Match> searchMatches(String... params);

    List<Team> searchTeams(String... params);

    List<String> getMatchTagsForTeam(Integer teamNum);

    void update(Tag tag);

    void deleteById(Integer id);

    void delete(String name);

    boolean checkForId(Integer id);

    void mergeTags(Tag oldTag, Tag newTag);

    void exportCSV(String outputFile, List<Tag> data);
}
