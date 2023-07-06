package dungeonmania.entities.enemies;

import dungeonmania.Game;
import dungeonmania.util.Direction;
import dungeonmania.util.Position;

public class ZombieToast extends Enemy {
    public static final double DEFAULT_HEALTH = 5.0;
    public static final double DEFAULT_ATTACK = 6.0;

    public ZombieToast(Position position, double health, double attack) {
        super(position, health, attack);
    }

    @Override
    public void move(Game game) {
        Position incrementedPosition;
        if (playerHasInvincibilityPotion(game)) {
            incrementedPosition = movePlayer(game);
        } else {
            incrementedPosition = moveRandomly(game);
        }
        game.getMap().moveTo(this, incrementedPosition);
    }

    private Position movePlayer(Game game) {
        Position playerDifference = Position.calculatePositionBetween(game.getMap().getPlayer().getPosition(),
                getPosition());
        Position moveX = (playerDifference.getX() >= 0) ? Position.translateBy(getPosition(), Direction.RIGHT)
                : Position.translateBy(getPosition(), Direction.LEFT);
        Position moveY = (playerDifference.getY() >= -0) ? Position.translateBy(getPosition(), Direction.UP)
                : Position.translateBy(getPosition(), Direction.DOWN);

        if (playerDifference.getY() == 0 && game.getMap().canMoveTo(this, moveX)) {
            return moveX;
        } else if (playerDifference.getX() == 0 && game.getMap().canMoveTo(this, moveY)) {
            return moveY;
        } else if (Math.abs(playerDifference.getX()) >= Math.abs(playerDifference.getY())) {
            return game.getMap().canMoveTo(this, moveX) ? moveX : getPosition();
        } else {
            return game.getMap().canMoveTo(this, moveY) ? moveY : getPosition();
        }
    }
}
