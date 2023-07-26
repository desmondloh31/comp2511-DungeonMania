package dungeonmania.entities.logical;

import java.util.List;

import dungeonmania.entities.Entity;
import dungeonmania.map.GameMap;
import dungeonmania.util.Position;

public class SwitchDoor extends LogicalEntity {
    private LogicalStrategy logic;

    public SwitchDoor(Position position, LogicalStrategy logic) {
        super(position, logic);
        this.logic = logic;
    }

    @Override
    public boolean isConductor() {
        return false;
    }

    @Override
    public boolean isActive(Entity targetEntity, List<Entity> allCardinalEntities, GameMap map) {
        return this.logic.isActive(targetEntity, allCardinalEntities, map);
    }
}
