package com.team3637.analytics;

import com.team3637.model.Tag;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TagDesignationGenerator {
    private Map<String, Integer> counters;

    public TagDesignationGenerator() {
        counters = new HashMap<>();
    }

    private String addDesignation(String category, String designation) {
        String s = addDesignationNS(category, designation);
        if (!s.equals(""))
            return s + " ";
        else
            return s;
    }

    private String addDesignationNS(String category, String designation) {
        if (convertToTint(counters.get(category)) > 2)
            return designation;
        else if (convertToTint(counters.get(category)) >= 0)
            return designation + "?";
        else
            return "";
    }

    public String generateDesignation(int teamNum, double avgScore, List<Tag> tags) {
        processTags(tags);

        String designation, temp, temp2, temp3;
        int tempNum;

        designation = "{ " + teamNum + " | " + String.format("%.2f", avgScore) + " - ";

        //Start
        temp = "S(";
        if (convertToTint(counters.get("boulder")) > 0)
            temp += "B" + convertToTint(counters.get("boulder")) + " ";
        if (convertToTint(counters.get("spybox")) > 0)
            temp += "S" + convertToTint(counters.get("spybox")) + " ";
        if (convertToTint(counters.get("neutral")) > 0)
            temp += "N" + convertToTint(counters.get("neutral")) + " ";
        if (temp.length() > 2)
            designation += temp.trim() + ") ";

        //Auton
        temp = "A(";
        tempNum = convertToTint(counters.get("auto carry"));
        if (tempNum > 0)
            temp += "B" + tempNum + " ";
        tempNum = convertToTint(counters.get("auto high goal"));
        if (tempNum > 0)
            temp += "Hs" + tempNum + " ";
        tempNum = convertToTint(counters.get("auto high fail"));
        if (tempNum > 0)
            temp += "Hf" + tempNum + " ";
        tempNum = convertToTint(counters.get("auto low goal"));
        if (tempNum > 0)
            temp += "Ls" + tempNum + " ";
        tempNum = convertToTint(counters.get("auto low fail"));
        if (tempNum > 0)
            temp += "Lf" + tempNum + " ";
        temp2 = "C(";
        tempNum = convertToTint(counters.get("auto cross l"));
        if (tempNum > 0)
            temp2 += "l" + tempNum + " ";
        tempNum = convertToTint(counters.get("auto cross p"));
        if (tempNum > 0)
            temp2 += "p" + tempNum + " ";
        tempNum = convertToTint(counters.get("auto cross c"));
        if (tempNum > 0)
            temp2 += "c" + tempNum + " ";
        tempNum = convertToTint(counters.get("auto cross m"));
        if (tempNum > 0)
            temp2 += "m" + tempNum + " ";
        tempNum = convertToTint(counters.get("auto cross r"));
        if (tempNum > 0)
            temp2 += "r" + tempNum + " ";
        tempNum = convertToTint(counters.get("auto cross d"));
        if (tempNum > 0)
            temp2 += "d" + tempNum + " ";
        tempNum = convertToTint(counters.get("auto cross s"));
        if (tempNum > 0)
            temp2 += "s" + tempNum + " ";
        tempNum = convertToTint(counters.get("auto cross w"));
        if (tempNum > 0)
            temp2 += "w" + tempNum + " ";
        tempNum = convertToTint(counters.get("auto cross t"));
        if (tempNum > 0)
            temp2 += "t" + tempNum + " ";
        if (temp2.length() > 2)
            temp += temp2.trim() + ") ";
        if (temp.length() > 2)
            designation += temp.trim() + ") ";

        //Outerworks
        temp = "O(";
        temp += addDesignation("lowbar", "L");
        temp2 = "A";
        temp2 += addDesignation("portcullis", "p");
        temp2 += addDesignation("cheval", "c");
        if (temp2.length() > 1)
            temp += temp2;
        temp2 = "B";
        temp2 += addDesignation("moat", "m");
        temp2 += addDesignation("ramparts", "r");
        if (temp2.length() > 1)
            temp += temp2;
        temp2 = "C";
        temp2 += addDesignation("drawbridge", "d");
        temp2 += addDesignation("sally", "s");
        if (temp2.length() > 1)
            temp += temp2;
        temp2 = "D";
        temp2 += addDesignation("wall", "w");
        temp2 += addDesignation("terrain", "t");
        if (temp2.length() > 1)
            temp += temp2;
        temp2 = " <";
        tempNum = convertToTint(counters.get("low bar stuck"));
        if (tempNum > 0)
            temp2 += "L" + tempNum + " ";
        temp3 = "A";
        tempNum = convertToTint(counters.get("portcullis stuck"));
        if (tempNum > 0)
            temp3 += "p" + tempNum + " ";
        tempNum = convertToTint(counters.get("cheval stuck"));
        if (tempNum > 0)
            temp3 += "c" + tempNum + " ";
        if (temp3.length() > 1)
            temp2 += temp3.trim() + " ";
        temp3 = "B";
        tempNum = convertToTint(counters.get("moat stuck"));
        if (tempNum > 0)
            temp3 += "m" + tempNum + " ";
        tempNum = convertToTint(counters.get("ramparts stuck"));
        if (tempNum > 0)
            temp3 += "r" + tempNum + " ";
        if (temp3.length() > 1)
            temp2 += temp3.trim() + " ";
        temp3 = "C";
        tempNum = convertToTint(counters.get("drawbridge stuck"));
        if (tempNum > 0)
            temp3 += "d" + tempNum + " ";
        tempNum = convertToTint(counters.get("sally stuck"));
        if (tempNum > 0)
            temp3 += "s" + tempNum + " ";
        if (temp3.length() > 1)
            temp2 += temp3.trim() + " ";
        temp3 = "D";
        tempNum = convertToTint(counters.get("rock wall stuck"));
        if (tempNum > 0)
            temp3 += "w" + tempNum + " ";
        tempNum = convertToTint(counters.get("rough terrain stuck"));
        if (tempNum > 0)
            temp3 += "t" + tempNum + " ";
        if (temp3.length() > 1)
            temp2 += temp3.trim() + " ";
        if (temp2.length() > 2)
            temp += temp2.trim() + ">";
        if (temp.length() > 2)
            designation += temp.trim() + ") ";

        //Boulder
        temp = "B(";
        temp += addDesignation("boulder carries", "C");
        temp += addDesignation("boulder passes", "P");
        temp += addDesignation("boulder shoves", "S");
        if (temp.length() > 2)
            designation += temp + ") ";

        //Low goal
        designation += addDesignation("low goal", "L");

        //High goal
        designation += addDesignation("high goal", "S");

        //Defender
        temp = "D(";
        temp += addDesignation("defender ground", "G");
        temp += addDesignation("defender air", "A");
        if (temp.length() > 2)
            designation += temp.trim() + ") ";

        //Drivers
        designation += addDesignation("drivers", "V");

        //Human player
        temp = "H(";
        temp += addDesignation("bowling", "B");
        temp += addDesignation("spy", "S");
        if (temp.length() > 2)
            designation += temp.trim() + ") ";

        //End game
        temp = "E(";
        if (convertToTint(counters.get("challenges")) > 0)
            temp += "Ch" + convertToTint(counters.get("challenges")) + " ";
        if (convertToTint(counters.get("climbs")) > 0)
            temp += "Cl" + convertToTint(counters.get("climbs")) + " ";
        if (convertToTint(counters.get("wins")) > 0)
            temp += "W" + convertToTint(counters.get("wins")) + " ";
        if (convertToTint(counters.get("loses")) > 0)
            temp += "L" + convertToTint(counters.get("loses")) + " ";
        if (temp.length() > 2)
            designation += temp.trim() + ") ";

        //Toughness
        if (convertToTint(counters.get("tough")) > 1)
            designation += "T ";
        else if (convertToTint(counters.get("tough")) == 0)
            designation += "T? ";

        //Unreliability
        if (convertToTint(counters.get("unreliable")) > 1)
            designation += "U ";
        else if (convertToTint(counters.get("unreliable")) == 0)
            designation += "U? ";

        return designation.trim() + " }";
    }

    private void processTags(List<Tag>  tags) {
        //Added an array contains function because Java's script engine doesn't contain ones
        String containsFunction = "function contains(arr, obj) {\n" +
                "   for (var i = 0; i < arr.length; i++) {\n" +
                "       if (arr[i] == obj) {\n" +
                "           return true;\n" +
                "       }\n" +
                "   }\n" +
                "   return false;\n" +
                "}\n";
        counters.clear();
        //Create new ScriptEngine that will process the javascript expression
        ScriptEngine engine = new ScriptEngineManager().getEngineByName("javascript");
        for (Tag tag : tags) {
            if (tag != null &&
                    tag.getExpression() != null &&
                    !tag.getExpression().equals("") &&
                    tag.getCategory() != null &&
                    !tag.getCategory().equals("")) {
                if (!counters.containsKey(tag.getCategory()))
                    counters.put(tag.getCategory(), 0);
                if(tag.requiesEval()) {
                    //Set the javascript variable arr equal to the an array of strings of each tag
                    String[] stingTags = new String[tags.size()];
                    for (int i = 0; i < tags.size(); i++)
                        if (tags.get(i) != null)
                            stingTags[i] = tags.get(i).toString();
                    //Set the javascript variable x equal to the value of highGoal
                    engine.put("x", counters.get(tag.getCategory()));
                    engine.put("arr", stingTags);
                    //Evaluated to javascript expression
                    try {
                        engine.eval(containsFunction + tag.getExpression());
                    } catch (ScriptException e) {
                        System.err.println("Tag error: " + tag.getTag());
                    }
                    //Convert the java Object x into an int set highGoal equal to that value
                    counters.put(tag.getCategory(), convertToTint(engine.get("x")));
                } else {
                    counters.put(tag.getCategory(), convertToTint(counters.get(tag.getCategory())) +
                            Integer.parseInt(tag.getExpression()));
                }
            }
        }
    }

    private static int convertToTint(Object x) {
        if (x == null)
            return -1;
        return (x.getClass() == Double.class) ? (int) Math.round((Double) x) : (int) x;
    }

    public String toString() {
        String string = "";
        for (Map.Entry<String, Integer> entry : counters.entrySet()) {
            string += entry.getKey() + " : " + entry.getValue();
        }
        return string;
    }
}
