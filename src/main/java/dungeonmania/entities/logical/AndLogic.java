package dungeonmania.entities.logical;

import java.util.List;

import dungeonmania.entities.Entity;
import dungeonmania.entities.Switch;
import dungeonmania.map.GameMap;

public class AndLogic implements LogicalStrategy {
    @Override
    public boolean isActive(Entity targetEntity, GameMap map) {
        int numActive = 0;
        List<Entity> allCardinalEntities = targetEntity.getCardinallyAdjacentEntities(map);

        for (Entity entity : allCardinalEntities) {
            if ((entity instanceof Switch) && entity.getActive()) {
                numActive++;
            } else if (entity.isConductor() && entity.isActive(entity, map)) {
                numActive++;
            } else if (entity.isConductor() && !entity.isActive(entity, map)) {
                numActive = -1;
                break;
            }
        }

        // activate the entity, set activation time to current tick of game
        if (numActive >= 2) {
            targetEntity.setTickActivated(map);
            return true;
        }
        return false;
    }
}
