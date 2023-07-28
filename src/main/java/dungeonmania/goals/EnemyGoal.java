package dungeonmania.goals;

import dungeonmania.Game;

public class EnemyGoal implements GoalStrategy {
    private int target;

    public EnemyGoal(int target) {
        this.target = target;
    }

    public boolean achieved(Game game) {
        return game.getKilledEnemiesCount() >= target;
    }

    public String toString(Game game) {
        return ":enemies";
    }
}
