package com.team3637.service;

import com.team3637.mapper.MatchMapper;
import com.team3637.mapper.TagStringMapper;
import com.team3637.mapper.TeamMapper;
import com.team3637.model.Match;
import com.team3637.model.Team;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVPrinter;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcCall;

import javax.sql.DataSource;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

public class TeamServiceMySQLImpl implements TeamService {

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
    public void create(Team team) {
        String fieldsSting = "id, team", valuesSting = "?, ?", SQL;
        List<Object> values = new ArrayList<>();
        values.add(team.getId());
        values.add(team.getTeam());
        for(int i = 0; i < team.getTags().size(); i++) {
            fieldsSting += ", tag" + i;
            valuesSting += ", ?";
            values.add(team.getTags().get(i));
        }
        SqlParameterSource addColsArg = new MapSqlParameterSource()
                .addValue("tableName", "teams")
                .addValue("ignoreCols", 2)
                .addValue("newCols", team.getTags().size());
        addCols.execute(addColsArg);
        SQL = "INSERT INTO teams (" + fieldsSting + ") VALUES (" + valuesSting + ");";
        jdbcTemplateObject.update(SQL, values.toArray());
        for(String tagName : team.getTags()) {
            SqlParameterSource addTagArg = new MapSqlParameterSource()
                    .addValue("tableName", "teams")
                    .addValue("tagName", tagName);
            addTag.execute(addTagArg);
        }
    }

    @Override
    public Match getTeam(Integer id) {
        String SQL = "SELECT * FROM teams WHERE id = ?";
        return jdbcTemplateObject.queryForObject(SQL, new MatchMapper(), id);
    }

    @Override
    public List<Team> getTeams() {
        String SQL = "SELECT * FROM teams";
        return jdbcTemplateObject.query(SQL, new TeamMapper());
    }

    @Override
    public List<Team> getTeamByNumber(Integer teamNum) {
        String SQL = "SELECT * FROM teams WHERE team = ?";
        return jdbcTemplateObject.query(SQL, new TeamMapper(), teamNum);
    }

    @Override
    public void update(Team team) {
        String valuesSting = "id=?, team=?", SQL;
        List<Object> values = new ArrayList<>();
        values.add(team.getId());
        values.add(team.getTeam());
        for(int i = 0; i < team.getTags().size(); i++) {
            valuesSting += ", tag" + i + "=?";
            values.add(team.getTags().get(i));
        }
        SQL = "UPDATE teams SET " + valuesSting + " WHERE id=" + team.getId() + ";";
        SqlParameterSource in = new MapSqlParameterSource()
                .addValue("ignoreCols", 2)
                .addValue("tableName", "matches")
                .addValue("newCols", team.getTags().size());
        addCols.execute(in);
        jdbcTemplateObject.update(SQL, values.toArray());
        for(String tagName : team.getTags()) {
            SqlParameterSource addTagArg = new MapSqlParameterSource()
                    .addValue("tableName", "teams")
                    .addValue("tagName", tagName);
            addTag.execute(addTagArg);
        }
    }

    @Override
    public void delete(Integer id) {
        String SQL = "DELETE FROM teams WHERE id = ?";
        jdbcTemplateObject.update(SQL, id);
    }

    @Override
    public boolean checkForId(Integer id) {
        String SQL = "SELECT count(*) FROM teams WHERE id = ?";
        Integer count = jdbcTemplateObject.queryForObject(SQL, Integer.class, id);
        return count != null && count > 0;
    }

    @Override
    public List<String> getTags() {
        String SQL = "SELECT tag FROM tags WHERE type = 'teams'";
        return jdbcTemplateObject.query(SQL, new TagStringMapper());
    }

    @Override
    public void mergeTags(String oldTag, String newTag) {
        SqlParameterSource args = new MapSqlParameterSource()
                .addValue("tableName", "teams")
                .addValue("noTagCols", 2)
                .addValue("oldTag", oldTag)
                .addValue("newTag", newTag);
        mergeTags.execute(args);
    }

    @Override
    public void exportCSV(String outputFile, List<Team> data) {
        FileWriter fileWriter = null;
        CSVPrinter csvFilePrinter = null;
        try {
            fileWriter = new FileWriter(outputFile);
            csvFilePrinter = new CSVPrinter(fileWriter, CSVFormat.DEFAULT.withRecordSeparator("\n"));
            for (Team team : data) {
                List<Object> line = new ArrayList<>();
                for (Field field : Match.class.getDeclaredFields()) {
                    field.setAccessible(true);
                    Object value = field.get(team);
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

}
