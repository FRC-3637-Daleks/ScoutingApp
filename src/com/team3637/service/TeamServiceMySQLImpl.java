package com.team3637.service;

import com.team3637.mapper.TagStringMapper;
import com.team3637.mapper.TeamMapper;
import com.team3637.model.Team;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVPrinter;
import org.apache.commons.csv.CSVRecord;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcCall;

import javax.sql.DataSource;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Field;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.Arrays;
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
        String fieldsSting = "team, avgscore, matches", valuesSting = "?, ?, ?", SQL;
        List<Object> values = new ArrayList<>();
        values.add(team.getTeam());
        values.add(team.getAvgscore());
        values.add(team.getMatches());
        for(int i = 0; i < team.getTags().size(); i++) {
            fieldsSting += ", tag" + i;
            valuesSting += ", ?";
            values.add(team.getTags().get(i));
        }
        SqlParameterSource addColsArg = new MapSqlParameterSource()
                .addValue("tableName", "teams")
                .addValue("ignoreCols", 4)
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
    public Team getTeam(Integer team) {
        String SQL = "SELECT * FROM teams WHERE team = ?";
        return jdbcTemplateObject.queryForObject(SQL, new TeamMapper(), team);
    }

    @Override
    public Team getTeamById(Integer id) {
        String SQL = "SELECT * FROM teams WHERE id = ?";
        return jdbcTemplateObject.queryForObject(SQL, new TeamMapper(), id);
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
    public Double[] getScoreRange() {
        Double[] scores = new Double[2];
        String SQL = "SELECT MIN(avgscore) FROM teams";
        scores[0] = jdbcTemplateObject.queryForObject(SQL, Double.class);
        SQL = "SELECT MAX(avgscore) FROM teams";
        scores[1] = jdbcTemplateObject.queryForObject(SQL, Double.class);
        return scores;
    }

    @Override
    public Integer[] getScoreRangeFor(Team team) {
        Integer[] scores = new Integer[2];
        String SQL = "SELECT MIN(avgscore) FROM teams WHERE team = ?";
        scores[0] = jdbcTemplateObject.queryForObject(SQL, Integer.class, team.getTeam());
        SQL = "SELECT MAX(avgscore) FROM teams WHERE team = ?";
        scores[1] = jdbcTemplateObject.queryForObject(SQL, Integer.class, team.getTeam());
        return scores;
    }

    @Override
    public void update(Team team) {
        Team oldTeam = getTeamByNumber(team.getTeam()).get(0);
        int diff = oldTeam.getTags().size() - team.getTags().size();
        String valuesSting = "team=?, avgscore=?, matches=?", SQL;
        List<Object> values = new ArrayList<>();
        values.add(team.getTeam());
        values.add(team.getAvgscore());
        values.add(team.getMatches());
        if(diff <= 0) {
            for (int i = 0; i < team.getTags().size(); i++) {
                valuesSting += ", tag" + i + "=?";
                values.add(team.getTags().get(i));
            }
        } else {
            for (int i = 0; i < oldTeam.getTags().size(); i++) {
                valuesSting += ", tag" + i + "=?";
                if(team.getTags().size() > i)
                    values.add(team.getTags().get(i));
                else
                    values.add(null);
            }
        }
        SQL = "UPDATE teams SET " + valuesSting + " WHERE team=" + team.getTeam() + ";";
        SqlParameterSource in = new MapSqlParameterSource()
                .addValue("ignoreCols", 4)
                .addValue("tableName", "teams")
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
    public void delete(Team team) {
        String SQL = "DELETE FROM teams WHERE id = ?";
        jdbcTemplateObject.update(SQL, team.getId());
    }

    @Override
    public boolean checkForId(Integer id) {
        String SQL = "SELECT count(*) FROM teams WHERE id = ?";
        Integer count = jdbcTemplateObject.queryForObject(SQL, Integer.class, id);
        return count != null && count > 0;
    }

    @Override
    public boolean checkForTeam(Integer team) {
        String SQL = "SELECT count(*) FROM teams WHERE team = ?";
        Integer count = jdbcTemplateObject.queryForObject(SQL, Integer.class, team);
        return count != null && count > 0;
    }

    @Override
    public List<String> getTags() {
        String SQL = "SELECT tag FROM tags WHERE type = 'teams' ORDER BY tag";
        return jdbcTemplateObject.query(SQL, new TagStringMapper());
    }

    @Override
    public void mergeTags(String oldTag, String newTag) {
        SqlParameterSource args = new MapSqlParameterSource()
                .addValue("tableName", "teams")
                .addValue("noTagCols", 4)
                .addValue("oldTag", oldTag)
                .addValue("newTag", newTag);
        mergeTags.execute(args);
    }

    @Override
    public void exportCSV(String outputFile) {
        List<Team> data = getTeams();
        FileWriter fileWriter = null;
        CSVPrinter csvFilePrinter = null;
        try {
            fileWriter = new FileWriter(outputFile);
            csvFilePrinter = new CSVPrinter(fileWriter, CSVFormat.DEFAULT.withRecordSeparator("\n"));
            for (Team team : data) {
                List<Object> line = new ArrayList<>();
                for (Field field : Team.class.getDeclaredFields()) {
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

    @Override
    public void importCSV(String inputFile) {
        try {
            String csvData = new String(Files.readAllBytes(FileSystems.getDefault().getPath(inputFile)));
            csvData = csvData.replaceAll("\\r", "");
            CSVParser parser = CSVParser.parse(csvData, CSVFormat.DEFAULT.withRecordSeparator("\n"));
            for (CSVRecord record : parser) {
                Team team = new Team();
                team.setId(Integer.parseInt(record.get(0)));
                team.setTeam(Integer.parseInt(record.get(1)));
                team.setAvgscore(Double.parseDouble(record.get(2)));
                team.setMatches(Integer.parseInt(record.get(3)));
                String[] tags = record.get(4).substring(1, record.get(4).length() - 1).split(",");
                for(int i =0; i < tags.length; i++)
                    tags[i] = tags[i].trim();
                if (tags.length > 0 && !tags[0].equals(""))
                    team.setTags(Arrays.asList(tags));
                else
                    team.setTags(new ArrayList<String>());
                if(checkForTeam(team.getTeam()))
                    update(team);
                else
                    create(team);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
