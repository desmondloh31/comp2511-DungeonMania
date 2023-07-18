package dungeonmania.goals;

import dungeonmania.Game;
import dungeonmania.entities.Switch;

public class BoulderGoal implements GoalStrategy {
    public boolean achieved(Game game) {
        // return game.getMap().getEntities(Switch.class).stream().allMatch(s -> s.isActivated());
        return game.getGameEntities(Switch.class).stream().allMatch(s -> s.isActivated());
    }

    public String toString(Game game) {
        return ":boulders";
    }
}
