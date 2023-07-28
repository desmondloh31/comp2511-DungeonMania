package dungeonmania.entities.logical;

import java.util.List;

import dungeonmania.entities.Destructible;
import dungeonmania.entities.Entity;
import dungeonmania.entities.MoveAwayable;
import dungeonmania.entities.Overlappable;
import dungeonmania.entities.Player;
import dungeonmania.entities.enemies.Spider;
import dungeonmania.map.GameMap;
import dungeonmania.util.Position;

public class SwitchDoor extends LogicalEntity implements Overlappable, MoveAwayable, Destructible {
    private LogicalStrategy logic;
    private boolean open;

    public SwitchDoor(Position position, LogicalStrategy logic) {
        super(position, logic);
        this.logic = logic;
        this.open = false;
    }

    @Override
    public boolean canMoveOnto(GameMap map, Entity entity) {
        if (open || entity instanceof Spider) {
            return true;
        }
        return (entity instanceof Player && isActive(this, map));
    }

    @Override
    public void onOverlap(GameMap map, Entity entity) {
        if (!(entity instanceof Player))
            return;

        if (isActive(this, map)) {
            open();
        }
    }

    @Override
    public boolean isConductor() {
        return false;
    }

    @Override
    public boolean isActive(Entity targetEntity, GameMap map) {

        System.out.println("SwitchDoor isActive?");
        return this.logic.isActive(targetEntity, map);
    }

    public boolean isOpen() {
        return open;
    }

    public void open() {
        this.open = true;
    }
}
