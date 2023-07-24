package dungeonmania.entities.enemies;

import dungeonmania.Game;
import dungeonmania.battles.BattleStatistics;
import dungeonmania.entities.Entity;
import dungeonmania.entities.Interactable;
import dungeonmania.entities.Player;
import dungeonmania.entities.collectables.Treasure;
import dungeonmania.map.GameMap;
import dungeonmania.util.Direction;
import dungeonmania.util.Position;

public class Mercenary extends Enemy implements Interactable {
    public static final int DEFAULT_BRIBE_AMOUNT = 1;
    public static final int DEFAULT_BRIBE_RADIUS = 1;
    public static final double DEFAULT_ATTACK = 5.0;
    public static final double DEFAULT_HEALTH = 10.0;

    private int bribeAmount = Mercenary.DEFAULT_BRIBE_AMOUNT;
    private int bribeRadius = Mercenary.DEFAULT_BRIBE_RADIUS;

    private double allyAttack;
    private double allyDefence;
    private boolean allied = false;
    private boolean isAdjacentToPlayer = false;

    public Mercenary(Position position, double health, double attack, int bribeAmount, int bribeRadius,
            double allyAttack, double allyDefence) {
        super(position, health, attack);
        this.bribeAmount = bribeAmount;
        this.bribeRadius = bribeRadius;
        this.allyAttack = allyAttack;
        this.allyDefence = allyDefence;
    }

    public boolean isAllied() {
        return allied;
    }

    @Override
    public void onOverlap(GameMap map, Entity entity) {
        if (allied)
            return;
        super.onOverlap(map, entity);
    }

    /**
     * check whether the current merc can be bribed
     * @param player
     * @return
     */
    private boolean canBeBribed(Player player) {
        return bribeRadius >= 0 && player.countEntityOfType(Treasure.class) >= bribeAmount;
    }

    /**
     * bribe the merc
     */
    private void bribe(Player player) {
        for (int i = 0; i < bribeAmount; i++) {
            player.use(Treasure.class);
        }

    }

    @Override
    public void interact(Player player, Game game) {
        allied = true;
        bribe(player);
        if (!isAdjacentToPlayer && Position.isAdjacent(player.getPosition(), getPosition()))
            isAdjacentToPlayer = true;
    }

    @Override
    public void move(Game game) {
        Position incrementedPosition;
        GameMap map = game.getMap();
        Player player = game.getPlayer();

        if (allied) {
            incrementedPosition = moveAlly(map, player);
        } else if (playerHasInvisibilityPotion(game)) {
            incrementedPosition = moveRandomly(game);
        } else if (playerHasInvincibilityPotion(game)) {
            incrementedPosition = moveInvisibile(map, player);
        } else {
            // Follow hostile
            incrementedPosition = map.dijkstraPathFind(getPosition(), player.getPosition(), this);
        }

        map.moveTo(this, incrementedPosition);
    }

    @Override
    public boolean isInteractable(Player player) {
        return !allied && canBeBribed(player);
    }

    @Override
    public BattleStatistics getBattleStatistics() {
        if (!allied)
            return super.getBattleStatistics();
        return new BattleStatistics(0, allyAttack, allyDefence, 1, 1);
    }

    private Position moveAlly(GameMap map, Player player) {
        Position incrementedPosition;
        incrementedPosition = isAdjacentToPlayer ? player.getPreviousDistinctPosition()
                : map.dijkstraPathFind(getPosition(), player.getPosition(), this);
        if (!isAdjacentToPlayer && Position.isAdjacent(player.getPosition(), incrementedPosition))
            setIsAdjacentToPlayer(true);
        return incrementedPosition;
    }

    private Position moveInvisibile(GameMap map, Player player) {
        Position incrementedPosition;
        Position playerDifference = Position.calculatePositionBetween(map.getPlayerPosition(), getPosition());
        Position moveX = (playerDifference.getX() >= 0) ? Position.translateBy(getPosition(), Direction.RIGHT)
                : Position.translateBy(getPosition(), Direction.LEFT);
        Position moveY = (playerDifference.getY() >= 0) ? Position.translateBy(getPosition(), Direction.UP)
                : Position.translateBy(getPosition(), Direction.DOWN);
        Position offset = getPosition();
        if (playerDifference.getY() == 0 && map.canMoveTo(this, moveX))
            offset = moveX;
        else if (playerDifference.getX() == 0 && map.canMoveTo(this, moveY))
            offset = moveY;
        else if (Math.abs(playerDifference.getX()) >= Math.abs(playerDifference.getY())) {
            if (map.canMoveTo(this, moveX))
                offset = moveX;
            else if (map.canMoveTo(this, moveY))
                offset = moveY;
            else
                offset = getPosition();
        } else {
            if (map.canMoveTo(this, moveY))
                offset = moveY;
            else if (map.canMoveTo(this, moveX))
                offset = moveX;
            else
                offset = getPosition();
        }
        incrementedPosition = offset;
        return incrementedPosition;
    }

    public void setIsAdjacentToPlayer(boolean value) {
        this.isAdjacentToPlayer = value;
    }
}
