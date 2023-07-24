package dungeonmania.entities.logical;

import java.util.ArrayList;
import java.util.List;

import dungeonmania.entities.Entity;
import dungeonmania.map.GameMap;

public class CoAndLogic implements LogicalStrategy {
    @Override
    public boolean isActive(Entity targetEntity, List<Entity> allCardinalEntities, GameMap map) {
        int numActive = 0;
        List<Entity> activeEntities = new ArrayList<>();

        for (Entity entity : allCardinalEntities) {
            List<Entity> curEntityCardinal = entity.getCardinallyAdjacentEntities(map);
            if (entity.isConductor() && entity.isActive(targetEntity, curEntityCardinal, map)) {
                activeEntities.add(entity);
            } else if (entity.isConductor() && !entity.isActive(targetEntity, curEntityCardinal, map)) {
                numActive = -1;
                break;
            }
        }

        if (!activeEntities.isEmpty() && activeEntities.size() >= 2) {
            int curTick = activeEntities.get(0).getTickActivated();
            for (int i = 1; i < activeEntities.size(); i++) {
                if (curTick == activeEntities.get(i).getTickActivated()) {
                    numActive++;
                } else {
                    numActive = -1;
                    break;
                }
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
