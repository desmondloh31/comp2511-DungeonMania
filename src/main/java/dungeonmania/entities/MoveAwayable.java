package dungeonmania.entities;

import dungeonmania.map.GameMap;

public interface MoveAwayable {
    void onMovedAway(GameMap map, Entity entity);
}
