import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;
import java.util.HashMap;
import java.util.Map;

public class TagExpression {
    public static void main(String[] args) throws ScriptException {
        //This code would be run once per tag

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

        //Added an array contains function because Java's script engine doesn't contain ones
        String containsFunction = "function contains(arr, obj) {\n" +
                "   for (var i = 0; i < arr.length; i++) {\n" +
                "       if (arr[i] == obj) {\n" +
                "           return true;\n" +
                "       }\n" +
                "   }\n" +
                "   return false;\n" +
                "}\n";

        //Set the current high goal
        Map<String, Integer> counters = new HashMap<>();

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
                    counters.put(tag.getCategory(), (x.getClass() == Double.class) ? (int) Math.round((Double) x) : (int) x);
                }
            }
        }
        //Print counter values
        for(Map.Entry<String, Integer> entry : counters.entrySet()) {
            System.out.println(entry.getKey() + " : " + entry.getValue());
        }
    }
}