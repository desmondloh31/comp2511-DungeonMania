package dungeonmania.entities.logical;

import java.util.List;

import dungeonmania.entities.Entity;
import dungeonmania.map.GameMap;
import dungeonmania.util.Position;

public class Wire extends Entity {
    public Wire(Position position) {
        super(position);
    }

    @Override
    public void onOverlap(GameMap map, Entity entity) {
        return;
    }

    @Override
    public void onMovedAway(GameMap map, Entity entity) {
        return;
    }

    @Override
    public void onDestroy(GameMap gameMap) {
        return;
    }

    @Override
    public boolean isConductor() {
        return true;
    }

    @Override
    public boolean isActive(Entity targetEntity, List<Entity> allCardinalEntities, GameMap map) {
        List<Entity> adjacentEntities = this.getCardinallyAdjacentEntities(map);
        for (Entity entity : adjacentEntities) {
            if (entity.isConductor()) {
                return true;
            }
        }
        return false;
    }
}
