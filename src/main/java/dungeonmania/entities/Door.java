package dungeonmania.entities;

import dungeonmania.map.GameMap;

import java.util.List;

import dungeonmania.entities.collectables.Key;
import dungeonmania.entities.enemies.Spider;
import dungeonmania.entities.inventory.Inventory;
import dungeonmania.util.Position;

public class Door extends Entity implements Overlappable, MoveAwayable, Destructible {
    private boolean open = false;
    private int number;

    public Door(Position position, int number) {
        super(position.asLayer(Entity.DOOR_LAYER));
        this.number = number;
    }

    @Override
    public boolean canMoveOnto(GameMap map, Entity entity) {
        if (open || entity instanceof Spider) {
            return true;
        }
        return (entity instanceof Player && hasKey((Player) entity));
    }

    @Override
    public void onOverlap(GameMap map, Entity entity) {
        if (!(entity instanceof Player))
            return;

        Player player = (Player) entity;
        Inventory inventory = player.getInventory();
        Key key = inventory.getFirst(Key.class);

        if (hasKey(player)) {
            inventory.remove(key);
            open();
        }
    }

    private boolean hasKey(Player player) {
        Inventory inventory = player.getInventory();
        Key key = inventory.getFirst(Key.class);

        return (key != null && key.getnumber() == number);
    }

    public boolean isOpen() {
        return open;
    }

    public void open() {
        open = true;
    }

    @Override
    public void onMovedAway(GameMap map, Entity entity) {
        return;
    }

    @Override
    public void onDestroy(GameMap gameMap) {
        return;
    }

    public boolean isActive(Entity targetEntity, List<Entity> allCardinalEntities, GameMap map) {
        return false;
    }

    public boolean isConductor() {
        return false;
    }
}
