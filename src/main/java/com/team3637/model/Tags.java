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
package com.team3637.model;

public class Tags implements Comparable{
    private int id;
    private String tag, type, counter, expression, category, grouping, input_type;
    private boolean eval, inTable;

    public Tags() {}

    public Tags(int id, String tag, String type, String counter, String expression, boolean eval, boolean inTable, String input_type, String category, String grouping) {
        this.id = id;
        this.tag = tag;
        this.type = type;
        this.counter = counter;
        this.expression = expression;
        this.eval = eval;
        this.inTable = inTable;
        this.category = category;
        this.grouping = grouping;
        this.input_type = input_type;
    }

    public Tags(String tag, String type, String counter, String expression) {
        this.tag = tag;
        this.type = type;
        this.counter = counter;
        this.expression = expression;
    }

    public Tags(String tag, String type) {
        this.tag = tag;
        this.type = type;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getCounter() {
        return counter;
    }

    public void setCounter(String counter) {
        this.counter = counter;
    }

    public boolean requiesEval() {
        return eval;
    }

    public void setRequiesEval(boolean eval) {
        this.eval = eval;
    }

    public boolean isEval() {
        return eval;
    }

    public void setEval(boolean eval) {
        this.eval = eval;
    }

    public boolean isInTable() {
        return inTable;
    }

    public void setInTable(boolean inTable) {
        this.inTable = inTable;
    }

    public String getExpression() {
        return expression;
    }

    public void setExpression(String expression) {
        this.expression = expression;
    }
    
    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getGrouping() {
        return grouping;
    }

    public void setGrouping(String grouping) {
        this.grouping = grouping;
    }
    
    public String getInput_type() {
        return input_type;
    }

    public void setInput_type(String input_type) {
        this.input_type = input_type;
    }
    
    @Override
    public String toString() {
        return tag;
    }

    @Override
    public int compareTo(Object o) {
        if(o.toString().equals(tag))
            return 0;
        return -1;
    }
}
