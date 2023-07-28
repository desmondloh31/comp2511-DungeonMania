package dungeonmania.entities;

import dungeonmania.map.GameMap;

import dungeonmania.entities.enemies.Spider;
import dungeonmania.util.Position;

public class Wall extends Entity implements Overlappable, MoveAwayable, Destructible {
    public Wall(Position position) {
        super(position.asLayer(Entity.CHARACTER_LAYER));
    }

    @Override
    public boolean canMoveOnto(GameMap map, Entity entity) {
        return entity instanceof Spider;
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
}
