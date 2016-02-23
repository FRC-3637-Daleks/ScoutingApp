import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;

public class TagExpression {
    public static void main(String[] args) throws ScriptException {
        //This code would be run once per tag

        //Create a new test tag
        Tag[] test = new Tag[]{
                new Tag("high goal fast", "match", "high goal",
                        "if (contains(arr, 'high goal great shot')) {(x < 0) ? x = 1 : x += 2} " +
                                "else if (contains(arr, 'high goal fair shot')) {(x < 0) ? x = 0 : x++} " +
                                "else x--"),
                new Tag("high goal shit shot", "match", "high goal", "")
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
        int highGoal = -20;

        //Create new ScriptEngine that will process the javascript expression
        ScriptEngine engine = new ScriptEngineManager().getEngineByName("javascript");
        for (Tag tag : test) {
            if (tag.getExpression() != null && !tag.getExpression().equals("")) {
                //Set the javascript variable x equal to the value of highGoal
                engine.put("x", highGoal);
                //Set the javascript variable arr equal to the an array of strings of each tag
                String[] tags = new String[test.length];
                for (int i = 0; i < test.length; i++) {
                    tags[i] = test[i].toString();
                }
                engine.put("arr", tags);
                //Evaluated to javascript expression
                engine.eval(containsFunction + tag.getExpression());
                //Retrieve the value of x
                Object x = engine.get("x");
                //Convert the java Object x into an int set highGoal equal to that value
                highGoal = (x.getClass() == Double.class) ? (int) Math.round((Double) x) : (int) x;
                //Print the now value if highGoal
                System.out.println(highGoal);
            }
        }
    }
}

//The Tag class is used to hold the data for one tag
class Tag {
    private String tag, type, category, expression;

    public Tag() {}

    public Tag(String tag, String type, String category, String expression) {
        this.tag = tag;
        this.type = type;
        this.category = category;
        this.expression = expression;
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