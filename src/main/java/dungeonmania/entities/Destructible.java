package dungeonmania.entities;

import dungeonmania.map.GameMap;

public interface Destructible {
    void onDestroy(GameMap map);
}
