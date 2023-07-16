package dungeonmania.goals;

import dungeonmania.Game;

public class TreasureGoal implements GoalStrategy {
    private int target;

    public TreasureGoal(int target) {
        this.target = target;
    }

    public boolean achieved(Game game) {
        return game.getCollectedTreasureCount() >= target;
    }

    public String toString(Game game) {
        return ":treasure";
    }
}
