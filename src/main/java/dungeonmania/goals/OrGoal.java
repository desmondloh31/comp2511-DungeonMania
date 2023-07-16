package dungeonmania.goals;

import dungeonmania.Game;

public class OrGoal implements GoalStrategy {
    private Goal goal1;
    private Goal goal2;

    public OrGoal(Goal goal1, Goal goal2) {
        this.goal1 = goal1;
        this.goal2 = goal2;
    }

    public boolean achieved(Game game) {
        return goal1.achieved(game) || goal2.achieved(game);
    }

    public String toString(Game game) {
        if (achieved(game))
            return "";
        else
            return "(" + goal1.toString(game) + " OR " + goal2.toString(game) + ")";
    }
}
