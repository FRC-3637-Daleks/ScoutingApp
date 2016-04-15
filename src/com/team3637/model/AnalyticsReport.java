/*
 * Team 3637 Scouting App - An application for data collection/analytics at FIRST competitions
 *  Copyright (C) 2016  Team 3637
 *
 *  This program is free software: you can redistribute it and/or modify
 *  it under the terms of the GNU General Public License as published by
 *  the Free Software Foundation, either version 3 of the License, or
 *  (at your option) any later version.
 *
 *  This program is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *  GNU General Public License for more details.
 *
 *  You should have received a copy of the GNU General Public License
 *  along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

package com.team3637.model;


import java.util.Arrays;
import java.util.List;

public class AnalyticsReport {

    private Team team;
    private String codedDesignation;
    private String englishDesignation;
    private String[] tableHeaders;
    private String[][] tableData;

    public AnalyticsReport() {}

    public String getCodedDesignation() {
        return codedDesignation;
    }

    public Team getTeam() {
        return team;
    }

    public void setTeam(Team team) {
        this.team = team;
    }

    public void setCodedDesignation(String codedDesignation) {
        this.codedDesignation = codedDesignation;
    }

    public String getEnglishDesignation() {
        return englishDesignation;
    }

    public void setEnglishDesignation(String englishDesignation) {
        this.englishDesignation = englishDesignation;
    }

    public String[] getTableHeaders() {
        return tableHeaders;
    }

    public void setTableHeaders(String[] tableHeaders) {
        this.tableHeaders = tableHeaders;
    }

    public String[][] getTableData() {
        return tableData;
    }

    public void setTableData(String[][] tableData) {
        this.tableData = tableData;
    }

    @Override
    public String toString() {
        return "AnalyticsReport{" +
                "team=" + team +
                ", codedDesignation='" + codedDesignation + '\'' +
                ", englishDesignation='" + englishDesignation + '\'' +
                ", tableHeaders=" + Arrays.toString(tableHeaders) +
                ", tableData=" + Arrays.toString(tableData) +
                '}';
    }
}
