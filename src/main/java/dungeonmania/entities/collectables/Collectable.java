package dungeonmania.entities.collectables;

import dungeonmania.util.Position;

import dungeonmania.entities.Destructible;
import dungeonmania.entities.Entity;
import dungeonmania.entities.MoveAwayable;
import dungeonmania.entities.Overlappable;
import dungeonmania.entities.Player;
import dungeonmania.entities.inventory.InventoryItem;
import dungeonmania.map.GameMap;

public class Collectable extends Entity implements InventoryItem, Overlappable, MoveAwayable, Destructible {
    public Collectable(Position position) {
        super(position);
    }

    @Override
    public boolean canMoveOnto(GameMap map, Entity entity) {
        return true;
    }

    @Override
    public void onOverlap(GameMap map, Entity entity) {
        if (entity instanceof Player) {
            if (!((Player) entity).pickUp(this))
                return;
            map.destroyEntity(this);
        }
    }

    @Override
    public void onMovedAway(GameMap map, Entity entity) {
        return;
    }

    @Override
    public void onDestroy(GameMap gameMap) {
        return;
    }

    public boolean isActive(Entity targetEntity, GameMap map) {
        return false;
    }

    public boolean isConductor() {
        return false;
    }
}
