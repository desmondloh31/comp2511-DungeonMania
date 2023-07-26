package dungeonmania.goals;

import dungeonmania.Game;

public class CompositeGoal implements GoalStrategy {
    private GoalStrategy[] goalsAll;

    public CompositeGoal(GoalStrategy... goalsAll) {
        this.goalsAll = goalsAll;
    }

    public boolean achieved(Game game) {
        for (GoalStrategy goal : goalsAll) {
            if (!goal.achieved(game)) {
                return false;
            }
        }
        return true;
    }

    public String toString(Game game) {
        return ":composite";
    }
}
