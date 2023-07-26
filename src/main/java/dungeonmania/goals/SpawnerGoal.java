package dungeonmania.goals;

import dungeonmania.Game;

public class SpawnerGoal implements GoalStrategy {
    public boolean achieved(Game game) {
        // The goal is achieved if there are no active spawners
        return !game.hasActiveSpawners();
    }

    public String toString(Game game) {
        return ":spawners";
    }

}
