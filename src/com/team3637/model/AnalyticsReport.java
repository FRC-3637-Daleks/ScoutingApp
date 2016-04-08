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


public class AnalyticsReport {

    private Team team;
    private String codedDesignation;
    private String englishDesignation;
    private String tableImage;

    public AnalyticsReport() {}

    public AnalyticsReport(String codedDesignation, String englishDesignation, String tableImage) {
        this.codedDesignation = codedDesignation;
        this.englishDesignation = englishDesignation;
        this.tableImage = tableImage;
    }

    public AnalyticsReport(Team team, String codedDesignation, String englishDesignation, String tableImage) {
        this.team = team;
        this.codedDesignation = codedDesignation;
        this.englishDesignation = englishDesignation;
        this.tableImage = tableImage;
    }

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

    public String getTableImage() {
        return tableImage;
    }

    public void setTableImage(String tableImage) {
        this.tableImage = tableImage;
    }
}
