package dungeonmania.entities;

import dungeonmania.map.GameMap;

public interface Overlappable {
    void onOverlap(GameMap map, Entity entity);
}
