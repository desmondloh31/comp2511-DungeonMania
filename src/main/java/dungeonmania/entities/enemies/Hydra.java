package dungeonmania.entities.enemies;

import dungeonmania.Game;
import dungeonmania.battles.BattleStatistics;
import dungeonmania.util.Direction;
import dungeonmania.util.Position;

public class Hydra extends Enemy {
    public static final double DEFAULT_HEALTH = 100.0;
    public static final double DEFAULT_ATTACK = 10.0;
    private int headCount;

    public Hydra(Position position, double health, double attack, int headCount) {
        super(position, health, attack);
        this.headCount = headCount;
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

    @Override
    public BattleStatistics getBattleStatistics() {
        return new BattleStatistics(getBattleStatistics().getHealth(), getBattleStatistics().getAttack() * headCount,
                getBattleStatistics().getDefence(), 1, 1);
    }

    public void reduceHeadCount() {
        if (this.headCount > 0) {
            this.headCount -= 1;
        }
    }

    public int getHeadCount() {
        return this.headCount;
    }
}
