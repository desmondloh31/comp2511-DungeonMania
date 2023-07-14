package dungeonmania.goals;

import dungeonmania.Game;

public class AndGoal implements GoalStrategy {
    private Goal goal1;
    private Goal goal2;

    public AndGoal(Goal goal1, Goal goal2) {
        this.goal1 = goal1;
        this.goal2 = goal2;
    }

    public boolean achieved(Game game) {
        return goal1.achieved(game) && goal2.achieved(game);
    }

    public String toString(Game game) {
        return "(" + goal1.toString(game) + " AND " + goal2.toString(game) + ")";
    }
}
