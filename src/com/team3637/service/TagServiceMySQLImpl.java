package com.team3637.service;

import com.team3637.mapper.*;
import com.team3637.model.Match;
import com.team3637.model.Tag;
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

public class TagServiceMySQLImpl implements TagService {

    private JdbcTemplate jdbcTemplateObject;
    private SimpleJdbcCall mergeTags;
    private SimpleJdbcCall deleteTag;

    @Override
    public void setDataSource(DataSource dataSource) {
        this.jdbcTemplateObject = new JdbcTemplate(dataSource);
        this.mergeTags = new SimpleJdbcCall(dataSource).withProcedureName("mergeTags");
        this.deleteTag = new SimpleJdbcCall(dataSource).withProcedureName("deleteTag");
    }

    @Override
    public void create(Tag tag) {
        String fieldsSting = "id, tag, type", valuesSting = "?, ?, ?", SQL;
        List<Object> values = new ArrayList<>();
        values.add(tag.getId());
        values.add(tag.getTag());
        values.add(tag.getType());
        SQL = "INSERT INTO tags (" + fieldsSting + ") VALUES (" + valuesSting + ");";
        jdbcTemplateObject.update(SQL, values.toArray());
    }

    @Override
    public Tag getTag(Integer id) {
        String SQL = "SELECT * FROM tags WHERE id = ?";
        return jdbcTemplateObject.queryForObject(SQL, new TagMapper(), id);
    }

    @Override
    public Tag getTagByName(String name) {
        String SQL = "SELECT * FROM tags WHERE tag = ?";
        return jdbcTemplateObject.queryForObject(SQL, new TagMapper(), name);
    }

    @Override
    public List<Tag> getTags() {
        String SQL = "SELECT * FROM tags";
        return jdbcTemplateObject.query(SQL, new TagMapper());
    }

    @Override
    public List<Integer> search(String[] matchTags, String[] teamTags) {
        List<Integer> teams = new ArrayList<>();
        int matchRows = jdbcTemplateObject.queryForObject("SELECT COUNT(*) FROM INFORMATION_SCHEMA.COLUMNS WHERE " +
                "TABLE_SCHEMA = 'scoutingtags' AND table_name = 'matches'", Integer.class) - 4;
        int teamRows = jdbcTemplateObject.queryForObject("SELECT COUNT(*) FROM INFORMATION_SCHEMA.COLUMNS WHERE " +
                "TABLE_SCHEMA = 'scoutingtags' AND table_name = 'teams'", Integer.class) - 2;
        String SQL = "SELECT matches.team FROM matches JOIN teams ON matches.team=teams.team WHERE ";
        for(int i = 0; i < matchTags.length; i++) {
            SQL += "(";
            for (int j = 0; j < matchRows; j++) {
                SQL +="matches.tag" + j + "=" +  "'" + matchTags[i] + "'";
                if(j != matchRows - 1) {
                    SQL += " OR ";
                }
            }
            SQL += ")";
            if(i != matchTags.length - 1)
                SQL += " AND ";
        }
        SQL += " AND ";
        for(int i = 0; i < teamTags.length; i++) {
            SQL += "(";
            for (int j = 0; j < teamRows; j++) {
                SQL +="teams.tag" + j + "=" +  "'" + teamTags[i] +  "'";
                if(j != teamRows - 1) {
                    SQL += " OR ";
                }
            }
            SQL += ")";
            if(i != teamTags.length - 1)
                SQL += " AND ";
        }
        return jdbcTemplateObject.query(SQL, new IntegerMapper());
    }

    @Override
    public List<Match> searchMatches(String... params) {
        int rows = jdbcTemplateObject.queryForObject("SELECT COUNT(*) FROM INFORMATION_SCHEMA.COLUMNS WHERE " +
                "TABLE_SCHEMA = 'scoutingtags' AND table_name = 'matches'", Integer.class) - 4;
        String SQL = "SELECT * FROM matches WHERE ";
        for(int i = 0; i < params.length; i++) {
            SQL += "(";
            for (int j = 0; j < rows; j++) {
                SQL +="tag" + j + "=" +  "'" + params[i] +  "'";
                if(j != rows - 1) {
                    SQL += " OR ";
                }
            }
            SQL += ")";
            if(i != params.length - 1)
                SQL += " AND ";
        }
        return jdbcTemplateObject.query(SQL, new MatchMapper());
    }

    @Override
    public List<Team> searchTeams(String... params) {
        int rows = jdbcTemplateObject.queryForObject("SELECT COUNT(*) FROM INFORMATION_SCHEMA.COLUMNS WHERE " +
                "TABLE_SCHEMA = 'scoutingtags' AND table_name = 'teams'", Integer.class) - 2;
        String SQL = "SELECT * FROM teams WHERE ";
        for(int i = 0; i < params.length; i++) {
            SQL += "(";
            for (int j = 0; j < rows; j++) {
                SQL +="tag" + j + "=" +  "'" + params[i] +  "'";
                if(j != rows - 1) {
                    SQL += " OR ";
                }
            }
            SQL += ")";
            if(i != params.length - 1)
                SQL += " AND ";
        }
        return jdbcTemplateObject.query(SQL, new TeamMapper());
    }

    @Override
    public List<String> getMatchTagsForTeam(Integer teamNum) {
        int matchRows = jdbcTemplateObject.queryForObject("SELECT COUNT(*) FROM INFORMATION_SCHEMA.COLUMNS WHERE " +
                "TABLE_SCHEMA = 'scoutingtags' AND table_name = 'matches'", Integer.class) - 4;
        String SQL = "";
        for(int i = 0; i < matchRows; i ++) {
            SQL += " SELECT DISTINCT `tag" + i + "` FROM matches WHERE `team`=" + teamNum +
                    " AND `tag" + i + "` IS NOT NULL UNION ALL";
        }
        SQL = SQL.substring(0, SQL.length() - 9);
        return jdbcTemplateObject.query(SQL, new TagStringMapper());
    }

    @Override
    public void update(Tag tag) {
        String valuesSting = "id=?, tag=?, type=?", SQL;
        List<Object> values = new ArrayList<>();
        values.add(tag.getId());
        values.add(tag.getTag());
        values.add(tag.getType());
        SQL = "UPDATE tags SET " + valuesSting + " WHERE id=" + tag.getId() + ";";
        jdbcTemplateObject.update(SQL, values.toArray());
    }

    @Override
    public void deleteById(Integer id) {
        String tag = jdbcTemplateObject.queryForObject("SELECT tag FROM tags WHERE id = ?", String.class, id);
        delete(tag);
    }

    @Override
    public void delete(String name) {
        SqlParameterSource args = new MapSqlParameterSource()
                .addValue("tagName", name);
        deleteTag.execute(args);
        String SQL = "DELETE FROM tags WHERE tag = ?";
        jdbcTemplateObject.update(SQL, name);
    }

    @Override
    public boolean checkForId(Integer id) {
        String SQL = "SELECT count(*) FROM tags WHERE id = ?";
        Integer count = jdbcTemplateObject.queryForObject(SQL, Integer.class, id);
        return count != null && count > 0;
    }

    @Override
    public void mergeTags(Tag oldTag, Tag newTag) {
        if(oldTag.getType().equals(newTag.getType()))
            return;
        SqlParameterSource args = new MapSqlParameterSource()
                .addValue("tableName", oldTag.getType())
                .addValue("noTagCols", 4)
                .addValue("oldTag", oldTag)
                .addValue("newTag", newTag);
        mergeTags.execute(args);
    }

    @Override
    public void exportCSV(String outputFile, List<Tag> data) {
        FileWriter fileWriter = null;
        CSVPrinter csvFilePrinter = null;
        try {
            fileWriter = new FileWriter(outputFile);
            csvFilePrinter = new CSVPrinter(fileWriter, CSVFormat.DEFAULT.withRecordSeparator("\n"));
            for (Tag tag : data) {
                List<Object> line = new ArrayList<>();
                for (Field field : Tag.class.getDeclaredFields()) {
                    field.setAccessible(true);
                    Object value = field.get(tag);
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