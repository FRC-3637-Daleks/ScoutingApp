import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;
import java.util.HashMap;
import java.util.Map;

public class TagDesignationGenerator {

    private Map<String, Integer> counters;

    public TagDesignationGenerator() {
        counters = new HashMap<>();
    }

    public static void main(String[] args) throws ScriptException {
        //Create a new matches tag
        Tag[][] matches = new Tag[][]{new Tag[]{
                new Tag("high goal fast", "match", "high goal",
                        "if (contains(arr, 'high goal great shot')) {(x < 0) ? x = 1 : x += 2} " +
                                "else if (contains(arr, 'high goal fair shot')) {(x < 0) ? x = 0 : x++} " +
                                "else if (contains(arr, 'high goal poor shot')) {x--}"),
                new Tag("high goal poor shot", "match", "high goal", null),
                },
                new Tag[]{
                        new Tag("high goal fast", "match", "high goal",
                                "if (contains(arr, 'high goal great shot')) {(x < 0) ? x = 1 : x += 2} " +
                                        "else if (contains(arr, 'high goal fair shot')) {(x < 0) ? x = 0 : x++} " +
                                        "else if (contains(arr, 'high goal poor shot')) {x--}"),
                        new Tag("high poor great shot", "match", "high goal", null),
                },
                new Tag[]{
                        new Tag("high goal fast", "match", "high goal",
                                "if (contains(arr, 'high goal great shot')) {(x < 0) ? x = 1 : x += 2} " +
                                        "else if (contains(arr, 'high goal fair shot')) {(x < 0) ? x = 0 : x++} " +
                                        "else if (contains(arr, 'high goal poor shot')) {x--}"),
                        new Tag("high goal great shot", "match", "high goal", null),
                },
                new Tag[]{
                        new Tag("high goal fast", "match", "high goal",
                                "if (contains(arr, 'high goal great shot')) {(x < 0) ? x = 1 : x += 2} " +
                                        "else if (contains(arr, 'high goal fair shot')) {(x < 0) ? x = 0 : x++} " +
                                        "else if (contains(arr, 'high goal poor shot')) {x--}"),
                        new Tag("high goal fair shot", "match", "high goal", null),
                }
        };

        TagDesignationGenerator designationGenerator = new TagDesignationGenerator();
        String designations = designationGenerator.generateDesignations(matches);
        System.out.println(designationGenerator);
        System.out.println(designations);
    }

    private String addDesignation(String category, String designation) {
        String s = addDesignationNS(category, designation);
        if(!s.equals(""))
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

    public String generateDesignations(Tag[][] matches) throws ScriptException {
        processTags(matches);

        String designation = "";
        designation += addDesignation("high goal", "S");
        designation += addDesignation("low goal", "L");
        designation += addDesignation("high goal", "S");

        String temp = "B(";
        temp += addDesignation("boulder carries", "C");
        temp += addDesignation("boulder passes", "P");
        temp += addDesignation("boulder shoves", "S");
        if (temp.length() > 2)
            designation += temp + ") ";

        temp = "O(";
        temp += addDesignation("lowbar", "L");
        String temp2 = "A";
        temp2 += addDesignationNS("portcullis", "p");
        temp2 += addDesignation("cheval", "c");
        if(temp2.length() > 1)
            temp += temp2;
        temp2 = "B";
        temp2 += addDesignationNS("moat", "m");
        temp2 += addDesignation("ramparts", "r");
        if(temp2.length() > 1)
            temp += temp2;
        temp2 = "C";
        temp2 += addDesignationNS("drawbridge", "d");
        temp2 += addDesignation("sally", "s");
        if(temp2.length() > 1)
            temp += temp2;
        temp2 = "D";
        temp2 += addDesignationNS("wall", "w");
        temp2 += addDesignation("terrain", "t");
        if(temp2.length() > 1)
            temp += temp2;

        temp2 = " <";
        int fails = convertToTint(counters.get("lowbarstuck"));
        if(fails != 0)
            temp2 += "L" + fails + " ";
        String temp3 = "A";
        fails = convertToTint(counters.get("portcullisstuck"));
        if(fails > 0)
            temp3 += "p" + fails;
        fails = convertToTint(counters.get("chevalstuck"));
        if(fails > 0)
            temp3 += "c" + fails;
        if(temp3.length() > 1)
            temp2 += temp3 + " ";
        temp3 = "B";
        fails = convertToTint(counters.get("moatstuck"));
        if(fails > 0)
            temp3 += "m" + fails;
        fails = convertToTint(counters.get("rampartsstuck"));
        if(fails > 0)
            temp3 += "r" + fails;
        if(temp3.length() > 1)
            temp2 += temp3 + " ";
        temp3 = "C";
        fails = convertToTint(counters.get("drawbridgestuck"));
        if(fails > 0)
            temp3 += "d" + fails;
        fails = convertToTint(counters.get("sallystuck"));
        if(fails > 0)
            temp3 += "s" + fails;
        if(temp3.length() > 1)
            temp2 += temp3 + " ";
        temp3 = "D";
        fails = convertToTint(counters.get("wallstuck"));
        if(fails > 0)
            temp3 += "w" + fails;
        fails = convertToTint(counters.get("terrainstuck"));
        if(fails > 0)
            temp3 += "t" + fails;
        if(temp3.length() > 1)
            temp2 += temp3 + " ";

        if(temp2.length() > 2)
            temp += temp2 + ">";

        if (temp.length() > 2)
            designation += temp + ") ";

        return designation;
    }

    private void processTags(Tag[][] matches) throws ScriptException {
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
        for (Tag[] match : matches) {
            for (Tag tag : match) {
                if (tag.getExpression() != null && !tag.getExpression().equals("")) {
                    if (!counters.containsKey(tag.getCategory()))
                        counters.put(tag.getCategory(), 0);
                    //Set the javascript variable arr equal to the an array of strings of each tag
                    String[] tags = new String[match.length];
                    for (int i = 0; i < match.length; i++)
                        tags[i] = match[i].toString();
                    //Set the javascript variable x equal to the value of highGoal
                    engine.put("x", counters.get(tag.getCategory()));
                    engine.put("arr", tags);
                    //Evaluated to javascript expression
                    engine.eval(containsFunction + tag.getExpression());
                    //Retrieve the value of x
                    Object x = engine.get("x");
                    //Convert the java Object x into an int set highGoal equal to that value
                    counters.put(tag.getCategory(), convertToTint(engine.get("x")));
                }
            }
        }
    }

    private static int convertToTint(Object x) {
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