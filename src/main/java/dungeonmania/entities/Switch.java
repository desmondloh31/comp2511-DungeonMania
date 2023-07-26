package dungeonmania.entities;

import java.util.ArrayList;
import java.util.List;

import dungeonmania.entities.collectables.Bomb;
import dungeonmania.map.GameMap;
import dungeonmania.util.Position;

public class Switch extends Entity implements Overlappable, MoveAwayable, Destructible {
    private boolean activated;
    private List<Bomb> bombs = new ArrayList<>();
    private List<Bomb> logicalBombs = new ArrayList<>();

    public Switch(Position position) {
        super(position.asLayer(Entity.ITEM_LAYER));
    }

    public void subscribe(Bomb b) {
        if (b.getBombStrategy() == null) {
            bombs.add(b);
        }
        logicalBombs.add(b);
    }

    public void subscribe(Bomb bomb, GameMap map) {
        if (bomb.getBombStrategy() == null) {
            bombs.add(bomb);
        }
        logicalBombs.add(bomb);

        // check if the explosion condition for regular and logical bomb is met
        if (activated) {
            bombs.stream().forEach(b -> b.notify(map));
            for (Bomb b : logicalBombs) {
                if (b.canLogicalExplode(map)) {
                    b.notify(map);
                }
            }
        }
    }

    public void unsubscribe(Bomb b) {
        bombs.remove(b);
    }

    @Override
    public boolean canMoveOnto(GameMap map, Entity entity) {
        return true;
    }

    @Override
    public void onOverlap(GameMap map, Entity entity) {
        if (entity instanceof Boulder) {
            activated = true;
            bombs.stream().forEach(b -> b.notify(map));
        }
    }

    @Override
    public void onMovedAway(GameMap map, Entity entity) {
        if (entity instanceof Boulder) {
            activated = false;
        }
    }

    public boolean isActivated() {
        return activated;
    }

    @Override
    public void onDestroy(GameMap gameMap) {
        return;
    }

    // switch is a defined conductor
    public boolean isActive(Entity targetEntity, List<Entity> allCardinalEntities, GameMap map) {
        if (activated == true) {
            setTickActivated(map.getCurrentTick(), map);
        }
        return activated;
    }

    public boolean isConductor() {
        return true;
    }
}
