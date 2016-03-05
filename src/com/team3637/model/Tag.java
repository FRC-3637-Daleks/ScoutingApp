package com.team3637.model;

public class Tag {
    private int id;
    private String tag, type, category, expression;

    public Tag() {}

    public Tag(String tag, String type, String category, String expression) {
        this.tag = tag;
        this.type = type;
        this.category = category;
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

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
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
}
