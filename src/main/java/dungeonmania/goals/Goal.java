package dungeonmania.goals;

import dungeonmania.Game;

public class Goal {
    private GoalStrategy type;
    private int target;
    private Goal goal1;
    private Goal goal2;

    public Goal(GoalStrategy type) {
        this.type = type;
    }

    public Goal(GoalStrategy type, int target) {
        this.type = type;
        this.target = target;
    }

    public Goal(GoalStrategy type, Goal goal1, Goal goal2) {
        this.type = type;
        this.goal1 = goal1;
        this.goal2 = goal2;
    }

    /**
     * @return true if the goal has been achieved, false otherwise
     */
    public boolean achieved(Game game) {
        if (game.getPlayer() == null)
            return false;
        return type.achieved(game);
    }

    public String toString(Game game) {
        if (this.achieved(game))
            return "";
        return type.toString(game);
    }
}
