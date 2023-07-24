package dungeonmania.entities.logical;

import java.util.List;

import dungeonmania.entities.Entity;
import dungeonmania.map.GameMap;

public class AndLogic implements LogicalStrategy {
    @Override
    public boolean isActive(Entity targetEntity, List<Entity> allCardinalEntities, GameMap map) {
        int numActive = 0;
        for (Entity entity : allCardinalEntities) {
            List<Entity> curEntityCardinal = entity.getCardinallyAdjacentEntities(map);
            if (entity.isConductor() && entity.isActive(targetEntity, curEntityCardinal, map)) {
                numActive++;
            } else if (entity.isConductor() && !entity.isActive(targetEntity, curEntityCardinal, map)) {
                numActive = -1;
                break;
            }
        }

        // activate the entity, set activation time to current tick of game
        if (numActive >= 2) {
            targetEntity.setTickActivated(map.getCurrentTick(), map);
            return true;
        }
        return false;
    }
}
