package dungeonmania.entities.logical;

import dungeonmania.entities.Entity;
import dungeonmania.map.GameMap;
import dungeonmania.util.Position;

public abstract class LogicalEntity extends Entity {
    private LogicalStrategy logic;

    public LogicalEntity(Position position, LogicalStrategy logic) {
        super(position);
        this.logic = logic;
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
}
