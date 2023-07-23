package dungeonmania.entities.enemies;

import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

import dungeonmania.Game;
import dungeonmania.battles.BattleStatistics;
import dungeonmania.battles.Battleable;
import dungeonmania.entities.Destructible;
import dungeonmania.entities.Entity;
import dungeonmania.entities.MoveAwayable;
import dungeonmania.entities.Overlappable;
import dungeonmania.entities.Player;
import dungeonmania.entities.collectables.potions.InvincibilityPotion;
import dungeonmania.entities.collectables.potions.InvisibilityPotion;
import dungeonmania.map.GameMap;
import dungeonmania.util.Position;

public abstract class Enemy extends Entity implements Battleable, Overlappable, Destructible, MoveAwayable {
    private BattleStatistics battleStatistics;

    public Enemy(Position position, double health, double attack) {
        super(position.asLayer(Entity.CHARACTER_LAYER));
        battleStatistics = new BattleStatistics(health, attack, 0, BattleStatistics.DEFAULT_DAMAGE_MAGNIFIER,
                BattleStatistics.DEFAULT_ENEMY_DAMAGE_REDUCER);
    }

    @Override
    public boolean canMoveOnto(GameMap map, Entity entity) {
        return entity instanceof Player;
    }

    @Override
    public BattleStatistics getBattleStatistics() {
        return battleStatistics;
    }

    public void setEnemyHealth(double health) {
        this.battleStatistics.setHealth(health);
    }

    public double getEnemyHealth() {
        return battleStatistics.getHealth();
    }

    public void increaseHealth(double amount) {
        double newHealth = this.getEnemyHealth() + amount;
        this.setEnemyHealth(newHealth);
    }

    public void receiveDamage(double damage) {
        double newHealth = getEnemyHealth() - damage;
        setEnemyHealth(Math.max(newHealth, 0));
    }

    @Override
    public void onOverlap(GameMap map, Entity entity) {
        if (entity instanceof Player) {
            Player player = (Player) entity;
            map.getGame().battle(player, this);
        }
    }

    @Override
    public void onDestroy(GameMap map) {
        Game g = map.getGame();
        g.unsubscribe(getId());
    }

    @Override
    public void onMovedAway(GameMap map, Entity entity) {
        return;
    }

    // self implemented helper methods:
    protected Position moveRandomly(Game game) {
        Position incrementedPosition;
        Random random = new Random();
        // List<Position> position = getPosition().getCardinallyAdjacentPositions();
        List<Position> position = getEntityCardinallyAdjacentPositions();
        GameMap map = game.getMap();
        position = position.stream().filter(pos -> map.canMoveTo(this, pos)).collect(Collectors.toList());
        if (position.size() == 0) {
            incrementedPosition = getPosition();
            map.moveTo(this, incrementedPosition);
        } else {
            incrementedPosition = position.get(random.nextInt(position.size()));
            map.moveTo(this, incrementedPosition);
        }
        return incrementedPosition;
    }

    protected boolean playerHasInvisibilityPotion(Game game) {
        // return game.getMap().getPlayer().getEffectivePotion() instanceof InvisibilityPotion;
        return game.getGameEffectivePotion() instanceof InvisibilityPotion;
    }

    protected boolean playerHasInvincibilityPotion(Game game) {
        return game.getGameEffectivePotion() instanceof InvincibilityPotion;
    }

    public abstract void move(Game game);
}
