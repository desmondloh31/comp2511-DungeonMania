package dungeonmania.entities.logical;

import java.util.List;

import dungeonmania.entities.Entity;
import dungeonmania.map.GameMap;

public class XorLogic implements LogicalStrategy {
    @Override
    public boolean isActive(Entity targetEntity, List<Entity> allCardinalEntities, GameMap map) {
        int numActive = 0;
        for (Entity entity : allCardinalEntities) {
            List<Entity> curEntityCardinal = entity.getCardinallyAdjacentEntities(map);
            if (entity.isConductor() && entity.isActive(targetEntity, curEntityCardinal, map)) {
                numActive++;
            }
        }

        if (numActive == 1) {
            return true;
        }
        return false;
    }
}
