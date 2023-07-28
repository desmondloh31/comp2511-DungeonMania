package dungeonmania.entities;

import dungeonmania.Game;
import dungeonmania.entities.logical.LightBulb;
import dungeonmania.entities.logical.SwitchDoor;
import dungeonmania.map.GameMap;
import dungeonmania.util.Direction;
import dungeonmania.util.Position;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public abstract class Entity {
    public static final int FLOOR_LAYER = 0;
    public static final int ITEM_LAYER = 1;
    public static final int DOOR_LAYER = 2;
    public static final int CHARACTER_LAYER = 3;

    private Position position;
    private Position previousPosition;
    private Position previousDistinctPosition;
    private Direction facing;
    private String entityId;

    private int tickActivated;
    private boolean isActive;

    public Entity(Position position) {
        this.position = position;
        this.previousPosition = position;
        this.previousDistinctPosition = null;
        this.entityId = UUID.randomUUID().toString();
        this.facing = null;

        this.tickActivated = -1;
        this.isActive = false;
    }

    public boolean canMoveOnto(GameMap map, Entity entity) {
        return false;
    }

    public abstract boolean isActive(Entity targetEntity, GameMap map);

    public abstract boolean isConductor();

    // use setPosition
    @Deprecated(forRemoval = true)
    public void translate(Direction direction) {
        previousPosition = this.position;
        this.position = Position.translateBy(this.position, direction);
        if (!previousPosition.equals(this.position)) {
            previousDistinctPosition = previousPosition;
        }
    }

    // use setPosition
    @Deprecated(forRemoval = true)
    public void translate(Position offset) {
        this.position = Position.translateBy(this.position, offset);
    }

    public abstract void onOverlap(GameMap map, Entity entity);

    public abstract void onMovedAway(GameMap map, Entity entity);

    public abstract void onDestroy(GameMap gameMap);

    public Position getPosition() {
        return position;
    }

    // return list of all cardinally adjacent entities
    public List<Entity> getCardinallyAdjacentEntities(GameMap map) {
        List<Position> allAdjacentPositions = getEntityCardinallyAdjacentPositions();
        List<Entity> allEntities = map.getEntities();

        List<Entity> cardinalAdjacent = new ArrayList<>();

        for (Entity e : allEntities) {
            if (allAdjacentPositions.contains(e.getPosition())) {
                cardinalAdjacent.add(e);
            }
        }

        return cardinalAdjacent;
    }

    public List<Position> getEntityCardinallyAdjacentPositions() {
        return position.getCardinallyAdjacentPositions();
    }

    public List<Position> getEntityAdjacentPositions() {
        return position.getAdjacentPositions();
    }

    public Position getPreviousPosition() {
        return previousPosition;
    }

    public Position getPreviousDistinctPosition() {
        return previousDistinctPosition;
    }

    public String getId() {
        return entityId;
    }

    public void setPosition(Position position) {
        previousPosition = this.position;
        this.position = position;
        if (!previousPosition.equals(this.position)) {
            previousDistinctPosition = previousPosition;
        }
    }

    public void setFacing(Direction facing) {
        this.facing = facing;
    }

    public void setTickActivated(GameMap map) {
        this.tickActivated = map.getCurrentTick();
    }

    public int getTickActivated() {
        return tickActivated;
    }

    public Direction getFacing() {
        return this.facing;
    }

    public void setActive(boolean active) {
        this.isActive = active;
    }

    public boolean getActive() {
        return isActive;
    }

    public void configureActive(List<String> visited, GameMap map) {
        boolean active = isActive(this, map);
        visited.add(getId());
        setActive(active);
        setTickActivated(map);

        List<Entity> allAdjacent = getCardinallyAdjacentEntities(map);
        for (Entity entity : allAdjacent) {
            if (!visited.contains(entity.getId()) && !(entity instanceof Switch)) {
                entity.configureActive(visited, map);
            }
        }
    }

    public void deActivate(List<String> visited, GameMap map) {
        visited.add(getId());
        setActive(false);

        List<Entity> allAdjacent = getCardinallyAdjacentEntities(map);
        for (Entity entity : allAdjacent) {
            if (!visited.contains(entity.getId()) && !(entity instanceof Switch)) {
                if (entity instanceof SwitchDoor || entity instanceof LightBulb) {
                    entity.deActivate(visited, map);
                    break;
                }
                entity.deActivate(visited, map);
            }
        }
    }
}
