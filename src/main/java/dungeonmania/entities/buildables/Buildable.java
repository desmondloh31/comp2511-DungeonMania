package dungeonmania.entities.buildables;

import dungeonmania.entities.BattleItem;
import dungeonmania.entities.Destructible;
import dungeonmania.entities.Entity;
import dungeonmania.entities.MoveAwayable;
import dungeonmania.entities.Overlappable;
import dungeonmania.entities.inventory.InventoryItem;
import dungeonmania.map.GameMap;
import dungeonmania.util.Position;

public abstract class Buildable extends Entity
        implements InventoryItem, BattleItem, Overlappable, MoveAwayable, Destructible {
    private int wood;
    private int arrows;
    private int treasure;
    private int keys;

    public Buildable(Position position) {
        super(position);
    }

    public Buildable(Position position, int wood, int arrows, int treasure, int keys) {
        super(position);
        this.wood = wood;
        this.arrows = arrows;
        this.treasure = treasure;
        this.keys = keys;
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

    public boolean isActive(Entity targetEntity, GameMap map) {
        return false;
    }

    public boolean isConductor() {
        return false;
    }

    public int getWood() {
        return wood;
    }

    public int getArrows() {
        return arrows;
    }

    public int getTreasure() {
        return treasure;
    }

    public int getKeys() {
        return keys;
    }

    public String setBuild() {
        return "";
    }

    public int woodRequirements() {
        return -1;
    }

    public int arrowRequirements() {
        return -1;
    };

    public int treasureRequirements() {
        return -1;
    }

    public int keyRequirements() {
        return -1;
    }
}
