package dungeonmania.entities.logical;

import java.util.List;

import dungeonmania.entities.Entity;
import dungeonmania.map.GameMap;

public interface LogicalStrategy {
    boolean isActive(Entity targetEntity, List<Entity> allCardinalEntities, GameMap map);
}
