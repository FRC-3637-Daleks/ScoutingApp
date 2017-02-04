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

public class Tag implements Comparable{
    private int id;
    private String tag, type, counter, expression;
    private boolean eval, inTable;

    public Tag() {}

    public Tag(int id, String tag, String type, String counter, String expression, boolean eval, boolean inTable) {
        this.id = id;
        this.tag = tag;
        this.type = type;
        this.counter = counter;
        this.expression = expression;
        this.eval = eval;
        this.inTable = inTable;
    }

    public Tag(String tag, String type, String counter, String expression) {
        this.tag = tag;
        this.type = type;
        this.counter = counter;
        this.expression = expression;
    }

    public Tag(String tag, String type) {
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
