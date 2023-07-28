package dungeonmania.entities.logical;

import dungeonmania.entities.Entity;
import dungeonmania.map.GameMap;

public interface LogicalStrategy {
    boolean isActive(Entity targetEntity, GameMap map);
}
