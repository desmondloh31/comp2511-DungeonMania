package dungeonmania.entities.logical;

import java.util.List;

import dungeonmania.entities.Entity;
import dungeonmania.map.GameMap;
import dungeonmania.util.Position;

public class LightBulb extends LogicalEntity {
    private LogicalStrategy logic;

    public LightBulb(Position position, LogicalStrategy logic) {
        super(position, logic);
        this.logic = logic;
    }

    @Override
    public boolean isConductor() {
        return false;
    }

    @Override
    public boolean isActive(Entity targetEntity, GameMap map) {

        System.out.println("Light isActive?");
        return this.logic.isActive(targetEntity, map);
    }
}
