package dungeonmania.entities.enemies;

import dungeonmania.Game;
import dungeonmania.entities.Player;
import dungeonmania.entities.collectables.Treasure;
import dungeonmania.map.GameMap;
import dungeonmania.util.Position;

import java.util.Random;

public class Assassin extends Mercenary {
    public static final double ASSASSIN_ATTACK = 15.0;
    public static final double ASSASSIN_HEALTH = 20.0;
    public static final double BRIBE_FAILURE_RATE = 0.2;
    public static final int DEFAULT_BRIBE_AMOUNT = 1;
    public static final int DEFAULT_BRIBE_RADIUS = 1;
    public static final double ALLY_ATTACK = 7.0;
    public static final double ALLY_DEFENCE = 5.0;

    private int bribeAmount = Mercenary.DEFAULT_BRIBE_AMOUNT;
    private boolean isAdjacentToPlayer = false;

    public Assassin(Position position, double health, double attack, int bribeAmount, int bribeRadius,
            double allyAttack, double allyDefence) {
        super(position, health, attack, bribeAmount, bribeRadius, allyAttack, allyDefence);
    }

    @Override
    public void interact(Player player, Game game) {
        Random random = new Random();
        if (random.nextDouble() < BRIBE_FAILURE_RATE) {
            for (int count = 0; count < bribeAmount; count++) {
                player.use(Treasure.class);
            }
        } else {
            super.interact(player, game);
        }
    }

    @Override
    public void move(Game game) {
        Position incrementedPosition;
        GameMap map = game.getMap();
        Player player = game.getPlayer();

        if (isAllied()) {
            incrementedPosition = isAdjacentToPlayer() ? player.getPreviousDistinctPosition()
                    : map.dijkstraPathFind(getPosition(), player.getPosition(), this);
            if (!isAdjacentToPlayer() && Position.isAdjacent(player.getPosition(), incrementedPosition)) {
                setAdjacentToPlayer(true);
            }
        } else if (playerHasInvisibilityPotion(game)) {
            incrementedPosition = moveRandomly(game);
        } else {
            // Follow hostile, with more damage due to being an Assassin
            incrementedPosition = map.dijkstraPathFind(getPosition(), player.getPosition(), this);
            if (Position.isAdjacent(player.getPosition(), incrementedPosition)) {
                // player.getBattleStatistics().setAttack(player.getBattleStatistics().getAttack() - ASSASSIN_ATTACK);
                player.setPlayerAttack(player.getPlayerAttack() - ASSASSIN_ATTACK);
                player.setPlayerHealth(player.getPlayerHealth() - ASSASSIN_ATTACK);
            }
        }

        map.moveTo(this, incrementedPosition);
        if (Position.isAdjacent(player.getPosition(), incrementedPosition)) {
            interact(player, game);
        }
    }

    // helper methods to get and set Adjacent Players:
    public boolean isAdjacentToPlayer() {
        return this.isAdjacentToPlayer;
    }

    public void setAdjacentToPlayer(boolean isAdjacentToPlayer) {
        this.isAdjacentToPlayer = isAdjacentToPlayer;
    }
}
