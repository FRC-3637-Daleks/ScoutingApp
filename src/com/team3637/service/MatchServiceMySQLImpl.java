/*Team 3637 Scouting App - An application for data collection/analytics at FIRST competitions
 Copyright (C) 2016  Team 3637

 This program is free software: you can redistribute it and/or modify
 it under the terms of the GNU General Public License as published by
 the Free Software Foundation, either version 3 of the License, or
 (at your option) any later version.

 This program is distributed in the hope that it will be useful,
 but WITHOUT ANY WARRANTY; without even the implied warranty of
 MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 GNU General Public License for more details.

 You should have received a copy of the GNU General Public License
 along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package com.team3637.service;

import com.team3637.mapper.MatchMapper;
import com.team3637.mapper.TagStringMapper;
import com.team3637.model.Match;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVPrinter;
import org.apache.commons.csv.CSVRecord;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcCall;

import javax.sql.DataSource;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Field;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MatchServiceMySQLImpl implements MatchService {

    private JdbcTemplate jdbcTemplateObject;
    private SimpleJdbcCall addCols;
    private SimpleJdbcCall addTag;
    private SimpleJdbcCall mergeTags;

    @Override
    public void setDataSource(DataSource dataSource) {
        this.jdbcTemplateObject = new JdbcTemplate(dataSource);
        this.addCols = new SimpleJdbcCall(dataSource).withProcedureName("addCols");
        this.addTag = new SimpleJdbcCall(dataSource).withProcedureName("addTag");
        this.mergeTags = new SimpleJdbcCall(dataSource).withProcedureName("mergeTags");
    }

    @Override
    public void create(Match match) {
        String fieldsSting = "matchNum, team, score", valuesSting = "?, ?, ?", SQL;
        List<Object> values = new ArrayList<>();
        values.add(match.getMatchNum());
        values.add(match.getTeam());
        values.add(match.getScore());
        for (int i = 0; i < match.getTags().size(); i++) {
            fieldsSting += ", tag" + i;
            valuesSting += ", ?";
            values.add(match.getTags().get(i));
        }
        SqlParameterSource addColsArg = new MapSqlParameterSource()
                .addValue("ignoreCols", 4)
                .addValue("tableName", "matches")
                .addValue("newCols", match.getTags().size());
        addCols.execute(addColsArg);
        SQL = "INSERT INTO matches (" + fieldsSting + ") VALUES (" + valuesSting + ");";
        jdbcTemplateObject.update(SQL, values.toArray());
        for (String tagName : match.getTags()) {
            SqlParameterSource addTagArg = new MapSqlParameterSource()
                    .addValue("tableName", "matches")
                    .addValue("tagName", tagName);
            addTag.execute(addTagArg);
        }
        SQL = "UPDATE teams SET `avgscore` = (`avgscore` * `matches` + ?) / (`matches` + 1) WHERE `team` = ?";
        jdbcTemplateObject.update(SQL, match.getScore(), match.getTeam());
        SQL = "UPDATE teams SET `matches` = `matches` + 1 WHERE `team` = ?";
        jdbcTemplateObject.update(SQL, match.getTeam());
    }

    @Override
    public Match getMatch(Integer id) {
        String SQL = "SELECT * FROM matches WHERE id = ?";
        return jdbcTemplateObject.queryForObject(SQL, new MatchMapper(), id);
    }

    @Override
    public List<Match> getMatches() {
        String SQL = "SELECT * FROM matches ORDER BY team ASC ";
        return jdbcTemplateObject.query(SQL, new MatchMapper());
    }

    @Override
    public List<Match> getForTeam(Integer teamNum) {
        String SQL = "SELECT * FROM matches WHERE team = ?";
        return jdbcTemplateObject.query(SQL, new MatchMapper(), teamNum);
    }

    @Override
    public List<Match> getForMatch(Integer matchNum) {
        String SQL = "SELECT * FROM matches WHERE matchNum = ?";
        return jdbcTemplateObject.query(SQL, new MatchMapper(), matchNum);
    }

    @Override
    public Match getForMatchAndTeam(Integer matchNum, Integer teamNum) {
        String SQL = "SELECT * FROM matches WHERE matchNum = ? AND team = ?";
        List<Match> results = jdbcTemplateObject.query(SQL, new MatchMapper(), matchNum, teamNum);
        return (results.size() > 0) ? results.get(0) : null;
    }

    @Override
    public void update(Match match) {
        Match oldMatch = getForMatchAndTeam(match.getMatchNum(), match.getTeam());
        int diff = oldMatch.getTags().size() - match.getTags().size();
        String valuesSting = "matchNum=?, team=?, score=?", SQL;
        SQL = "SELECT `score` FROM matches WHERE `matchNum` = ? AND `team` = ?";
        Integer oldScore = jdbcTemplateObject.queryForObject(SQL, Integer.class, match.getMatchNum(), match.getTeam());
        List<Object> values = new ArrayList<>();
        values.add(match.getMatchNum());
        values.add(match.getTeam());
        values.add(match.getScore());
        if (diff <= 0) {
            for (int i = 0; i < match.getTags().size(); i++) {
                valuesSting += ", tag" + i + "=?";
                values.add(match.getTags().get(i));
            }
        } else {
            for (int i = 0; i < oldMatch.getTags().size(); i++) {
                valuesSting += ", tag" + i + "=?";
                if (match.getTags().size() > i)
                    values.add(match.getTags().get(i));
                else
                    values.add(null);
            }
        }
        SQL = "UPDATE matches SET " + valuesSting + " WHERE matchNum = " +
                match.getMatchNum() + " AND team = " + match.getTeam() + ";";
        SqlParameterSource in = new MapSqlParameterSource()
                .addValue("ignoreCols", 4)
                .addValue("tableName", "matches")
                .addValue("newCols", match.getTags().size());
        addCols.execute(in);
        jdbcTemplateObject.update(SQL, values.toArray());
        for (String tagName : match.getTags()) {
            SQL = "SELECT count(*) FROM tags WHERE tag = ? AND type = ?";
            Integer count = jdbcTemplateObject.queryForObject(SQL, Integer.class, tagName, "matches");
            if(count <= 0) {
                SQL = "INSERT INTO tags (tag, type) VALUES (?, ?)";
                jdbcTemplateObject.update(SQL, tagName, "matches");
            }
        }
        SQL = "UPDATE teams SET `avgscore` = IF(`matches` > 1, (`avgscore` * `matches` - ? + ?) / `matches`, 0) " +
                "WHERE `team` = ?";
        jdbcTemplateObject.update(SQL, match.getScore(), oldScore, match.getTeam());
    }

    @Override
    public void delete(Match match) {
        String SQL = "DELETE FROM matches WHERE id = ?";
        jdbcTemplateObject.update(SQL, match.getId());
        SQL = "UPDATE teams SET `avgscore` = IF(`matches` > 1, (`avgscore` * `matches` - ?) / (`matches` - 1), 0) WHERE `team` = ?";
        jdbcTemplateObject.update(SQL, match.getScore(), match.getTeam());
        SQL = "UPDATE teams SET `matches` = `matches` - 1 WHERE `team` = ?";
        jdbcTemplateObject.update(SQL, match.getTeam());
    }

    @Override
    public boolean checkForId(Integer id) {
        String SQL = "SELECT count(*) FROM matches WHERE id = ?";
        Integer count = jdbcTemplateObject.queryForObject(SQL, Integer.class, id);
        return count != null && count > 0;
    }

    @Override
    public boolean checkForMatch(Integer matchNum, Integer team) {
        String SQL = "SELECT count(*) FROM matches WHERE matchNum = ? AND team = ?";
        Integer count = jdbcTemplateObject.queryForObject(SQL, Integer.class, matchNum, team);
        return count != null && count > 0;
    }

    @Override
    public List<String> getTags() {
        String SQL = "SELECT tag FROM tags WHERE type = 'matches' ORDER BY tag";
        return jdbcTemplateObject.query(SQL, new TagStringMapper());
    }

    @Override
    public void mergeTags(String oldTag, String newTag) {
        SqlParameterSource args = new MapSqlParameterSource()
                .addValue("tableName", "matches")
                .addValue("noTagCols", 4)
                .addValue("oldTag", oldTag)
                .addValue("newTag", newTag);
        mergeTags.execute(args);
    }

    @Override
    public void exportCSV(String outputFile) {
        List<Match> data = getMatches();
        FileWriter fileWriter = null;
        CSVPrinter csvFilePrinter = null;
        try {
            fileWriter = new FileWriter(outputFile);
            csvFilePrinter = new CSVPrinter(fileWriter, CSVFormat.DEFAULT.withRecordSeparator("\n"));
            for (Match match : data) {
                List<Object> line = new ArrayList<>();
                for (Field field : Match.class.getDeclaredFields()) {
                    field.setAccessible(true);
                    Object value = field.get(match);
                    line.add(value);
                }
                csvFilePrinter.printRecord(line);
            }
        } catch (IOException | IllegalAccessException e) {
            e.printStackTrace();
        } finally {
            try {
                if (fileWriter != null) {
                    fileWriter.flush();
                    fileWriter.close();
                }
                if (csvFilePrinter != null) {
                    csvFilePrinter.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void importCSV(String inputFile) {
        try {
            String csvData = new String(Files.readAllBytes(FileSystems.getDefault().getPath(inputFile)));
            csvData = csvData.replaceAll("\\r", "");
            CSVParser parser = CSVParser.parse(csvData, CSVFormat.DEFAULT.withRecordSeparator("\n"));
            for (CSVRecord record : parser) {
                Match match = new Match();
                match.setId(Integer.parseInt(record.get(0)));
                match.setMatchNum(Integer.parseInt(record.get(1)));
                match.setTeam(Integer.parseInt(record.get(2)));
                match.setScore(Integer.parseInt(record.get(3)));
                String[] tags = record.get(4).substring(1, record.get(4).length() - 1).split(",");
                for(int i =0; i < tags.length; i++)
                    tags[i] = tags[i].trim();
                if (tags.length > 0 && !tags[0].equals(""))
                    match.setTags(Arrays.asList(tags));
                else
                    match.setTags(new ArrayList<String>());
                if(checkForMatch(match.getMatchNum(), match.getTeam()))
                    update(match);
                else
                    create(match);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}