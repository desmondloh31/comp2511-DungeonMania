package dungeonmania.entities.logical;

import java.util.List;

import dungeonmania.entities.Entity;
import dungeonmania.entities.Switch;
import dungeonmania.map.GameMap;

public class OrLogic implements LogicalStrategy {
    @Override
    public boolean isActive(Entity targetEntity, GameMap map) {
        int numActive = 0;
        List<Entity> allCardinalEntities = targetEntity.getCardinallyAdjacentEntities(map);

        System.out.println("Whats cardinal adjacent in isActive: ");
        System.out.println(allCardinalEntities);

        for (Entity entity : allCardinalEntities) {
            if ((entity instanceof Switch) && entity.getActive()) {
                numActive++;
            } else if (entity.isConductor() && entity.isActive(entity, map)) {
                numActive++;
            }
        }

        if (numActive >= 1) {
            targetEntity.setTickActivated(map.getCurrentTick(), map);
            return true;
        }
        return false;
    }
}
