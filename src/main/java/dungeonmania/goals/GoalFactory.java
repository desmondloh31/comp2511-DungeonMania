package dungeonmania.goals;

import org.json.JSONArray;
import org.json.JSONObject;

// Improve this with a factory pattern, eliminate switch cases
public class GoalFactory {
    public static Goal createGoal(JSONObject jsonGoal, JSONObject config) {
        JSONArray subgoals;
        Goal goal1 = null;
        Goal goal2 = null;
        switch (jsonGoal.getString("goal")) {
        case "AND":
            subgoals = jsonGoal.getJSONArray("subgoals");
            goal1 = createGoal(subgoals.getJSONObject(0), config);
            goal2 = createGoal(subgoals.getJSONObject(1), config);
            return new Goal(new AndGoal(goal1, goal2), goal1, goal2);
        case "OR":
            subgoals = jsonGoal.getJSONArray("subgoals");
            goal1 = createGoal(subgoals.getJSONObject(0), config);
            goal2 = createGoal(subgoals.getJSONObject(1), config);
            return new Goal(new OrGoal(goal1, goal2), goal1, goal2);
        case "exit":
            return new Goal(new ExitGoal());
        case "boulders":
            return new Goal(new BoulderGoal());
        case "treasure":
            int treasureGoal = config.optInt("treasure_goal", 1);
            return new Goal(new TreasureGoal(treasureGoal));
        default:
            return null;
        }
    }
}
