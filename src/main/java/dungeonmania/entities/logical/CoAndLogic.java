package dungeonmania.entities.logical;

import java.util.ArrayList;
import java.util.List;

import dungeonmania.entities.Entity;
import dungeonmania.entities.Switch;
import dungeonmania.map.GameMap;

public class CoAndLogic implements LogicalStrategy {
    @Override
    public boolean isActive(Entity targetEntity, GameMap map) {
        int numActive = 0;
        List<Entity> activeEntities = new ArrayList<>();
        List<Entity> allCardinalEntities = targetEntity.getCardinallyAdjacentEntities(map);

        for (Entity entity : allCardinalEntities) {
            if ((entity instanceof Switch) && entity.getActive()) {
                activeEntities.add(entity);
            } else if (entity.isConductor() && entity.isActive(entity, map)) {
                activeEntities.add(entity);
            } else if (entity.isConductor() && !entity.isActive(entity, map)) {
                numActive = -1;
                break;
            }
        }

        if (!activeEntities.isEmpty() && activeEntities.size() >= 2 && numActive != -1) {
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

        numActive = numActive + 1;
        // activate the entity, set activation time to current tick of game
        if (numActive >= 2) {
            targetEntity.setTickActivated(map);
            return true;
        }
        return false;
    }
}
