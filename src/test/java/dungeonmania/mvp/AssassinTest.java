package dungeonmania.mvp;

import dungeonmania.DungeonManiaController;
import dungeonmania.exceptions.InvalidActionException;
import dungeonmania.response.models.DungeonResponse;
import dungeonmania.response.models.EntityResponse;
import dungeonmania.util.Direction;
import dungeonmania.util.Position;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

import java.util.List;

public class AssassinTest {
    @Test
    @Tag("12-1")
    @DisplayName("Test assassin sneaks towards player using least populated route")
    public void simpleMovement() {
        DungeonManiaController dmc = new DungeonManiaController();
        DungeonResponse res = dmc.newGame("d_assassinTest_simpleMovement", "c_assassinTest_simpleMovement");
        assertEquals(new Position(8, 1), getAssassinPos(res));
        res = dmc.tick(Direction.RIGHT);
        assertEquals(new Position(7, 1), getAssassinPos(res));
        res = dmc.tick(Direction.RIGHT);
        assertEquals(new Position(6, 1), getAssassinPos(res));
        res = dmc.tick(Direction.RIGHT);
        assertEquals(new Position(5, 1), getAssassinPos(res));
    }

    @Test
    @Tag("12-2")
    @DisplayName("Test assassin stops if they cannot move any closer to the player")
    public void stopMovement() {
        // Fill in with test logic
        DungeonManiaController dmc = new DungeonManiaController();
        DungeonResponse res = dmc.newGame("d_assassinTest_stopMovement", "c_assassinTest_stopMovement");

        Position startingPos = getAssassinPos(res);

        res = dmc.tick(Direction.RIGHT);
        assertEquals(startingPos, getAssassinPos(res));
    }

    @Test
    @Tag("12-3")
    @DisplayName("Test assassins can move through closed doors")
    public void doorMovement() {
        DungeonManiaController dmc = new DungeonManiaController();
        DungeonResponse res = dmc.newGame("d_assassinTest_doorMovement", "c_assassinTest_doorMovement");

        Position startingPos = getAssassinPos(res);
        res = dmc.tick(Direction.RIGHT);
        assertEquals(startingPos, getAssassinPos(res));
    }

    @Test
    @Tag("12-4")
    @DisplayName("Test assassin moves around a wall to get to the player")
    public void evadeWall() {
        DungeonManiaController dmc = new DungeonManiaController();
        DungeonResponse res = dmc.newGame("d_assassinTest_evadeWall", "c_assassinTest_evadeWall");

        res = dmc.tick(Direction.RIGHT);
        assertTrue(new Position(4, 1).equals(getAssassinPos(res)) || new Position(4, 3).equals(getAssassinPos(res)));
    }

    @Test
    @Tag("12-5")
    @DisplayName("Test assassin cannot be bribed")
    public void bribeAttemptSuccess() {
        DungeonManiaController dmc = new DungeonManiaController();
        DungeonResponse res = dmc.newGame("d_assassinTest_bribeAmount", "c_assassin_test_bribeAmount");

        String assassinId = TestUtils.getEntitiesStream(res, "assassin").findFirst().get().getId();

        // pick up first treasure
        res = dmc.tick(Direction.RIGHT);
        assertEquals(1, TestUtils.getInventory(res, "treasure").size());
        assertEquals(new Position(7, 1), getAssassinPos(res));

        // attempt bribe
        assertThrows(InvalidActionException.class, () -> dmc.interact(assassinId));
        assertEquals(1, TestUtils.getInventory(res, "treasure").size());

        // pick up second treasure
        res = dmc.tick(Direction.RIGHT);
        assertEquals(2, TestUtils.getInventory(res, "treasure").size());
        assertEquals(new Position(6, 1), getAssassinPos(res));

        // attempt bribe
        assertThrows(InvalidActionException.class, () -> dmc.interact(assassinId));
        assertEquals(2, TestUtils.getInventory(res, "treasure").size());

        // achieve bribe
        assertEquals(2, TestUtils.getInventory(res, "treasure").size());
    }

    @Test
    @Tag("12-6")
    @DisplayName("Test assassin cannot be bribed")
    public void bribeFailed() {
        DungeonManiaController dmc = new DungeonManiaController();
        DungeonResponse res = dmc.newGame("d_assassinTest_bribeAmount", "c_assassin_test_bribeAmount");

        String assassinId = TestUtils.getEntitiesStream(res, "assassin").findFirst().get().getId();

        // pick up first treasure
        res = dmc.tick(Direction.RIGHT);
        assertEquals(1, TestUtils.getInventory(res, "treasure").size());
        assertEquals(new Position(7, 1), getAssassinPos(res));

        // attempt bribe, expect it to fail
        assertThrows(InvalidActionException.class, () -> dmc.interact(assassinId));
        assertEquals(1, TestUtils.getInventory(res, "treasure").size());

        // assert that the assassin's position hasn't changed
        assertEquals(new Position(7, 1), getAssassinPos(res));

        // assert the treasure count in player's inventory didn't decrease
        assertEquals(1, TestUtils.getInventory(res, "treasure").size());
    }

    @Test
    @Tag("12-7")
    @DisplayName("Test player battles mercenary and player dies")
    public void testPlayerDiesWhenBattleAssassin() {
        DungeonManiaController controller = new DungeonManiaController();
        DungeonResponse postBattleResponse = TestUtils.genericAssassinSequence(controller, "c_assassinTest_playerDies");
        List<EntityResponse> entities = postBattleResponse.getEntities();

        assertTrue(TestUtils.countEntityOfType(entities, "player") == 0);
    }

    @Test
    @Tag("12-8")
    @DisplayName("Test player battles mercenary and mercenary dies")
    public void testAssassinDiesWhenBattle() {
        DungeonManiaController controller = new DungeonManiaController();
        DungeonResponse postBattleResponse = TestUtils.genericAssassinSequence(controller,
                "c_assassinTest_assassinDies");
        List<EntityResponse> entities = postBattleResponse.getEntities();

        assertTrue(TestUtils.countEntityOfType(entities, "mercenary") == 0);
    }

    @Test
    @Tag("12-9")
    @DisplayName("Testing an assassin does not follow the player")
    public void enemyMovementNoStick() {
        DungeonManiaController dmc = new DungeonManiaController();
        DungeonResponse res = dmc.newGame("d_assassinTest_noStick", "c_assassinTest_noStick");

        // String assassinId = TestUtils.getEntitiesStream(res, "assassin").findFirst().get().getId();

        // pick up treasure
        res = dmc.tick(Direction.LEFT);
        assertEquals(1, TestUtils.getInventory(res, "treasure").size());
        assertEquals(new Position(1, 1), getPlayerPos(res));
        assertEquals(new Position(4, 1), getAssassinPos(res));

        // Wait until the mercenary is next to the player
        res = dmc.tick(Direction.LEFT);
        assertEquals(1, TestUtils.getInventory(res, "treasure").size());
        assertEquals(new Position(1, 1), getPlayerPos(res));
        assertEquals(new Position(3, 1), getAssassinPos(res));
        res = dmc.tick(Direction.LEFT);
        assertEquals(0, TestUtils.getInventory(res, "treasure").size());
        assertEquals(new Position(1, 1), getPlayerPos(res));
        assertEquals(new Position(2, 1), getAssassinPos(res));

        // achieve bribe - success
        // res = assertDoesNotThrow(() -> dmc.interact(assassinId));
        assertEquals(0, TestUtils.getInventory(res, "treasure").size());
        assertEquals(new Position(1, 1), getPlayerPos(res));
        assertEquals(new Position(2, 1), getAssassinPos(res));

        // Ally follows the player
        res = dmc.tick(Direction.LEFT);
        assertEquals(new Position(1, 1), getPlayerPos(res));
        assertNotEquals(new Position(2, 1), getAssassinPos(res));
        res = dmc.tick(Direction.RIGHT);
        assertEquals(new Position(2, 1), getPlayerPos(res));
        assertNotEquals(new Position(1, 1), getAssassinPos(res));
        res = dmc.tick(Direction.RIGHT);
        assertEquals(new Position(3, 1), getPlayerPos(res));
        assertNotEquals(new Position(2, 1), getAssassinPos(res));
        res = dmc.tick(Direction.RIGHT);
        assertEquals(new Position(4, 1), getPlayerPos(res));
        assertNotEquals(new Position(3, 1), getAssassinPos(res));
    }

    @Test
    @Tag("12-10")
    @DisplayName("Testing an assassin finds the player using the least populated route")
    public void enemyMovementFindPlayer() {
        DungeonManiaController dmc = new DungeonManiaController();
        DungeonResponse res = dmc.newGame("d_assassinTest_enemyMovementFindPlayer",
                "c_assassinTest_enemyMovementFindPlayer");

        String assassinId = TestUtils.getEntitiesStream(res, "assassin").findFirst().get().getId();

        // pick up treasure
        res = dmc.tick(Direction.RIGHT);
        assertEquals(1, TestUtils.getInventory(res, "treasure").size());
        assertEquals(new Position(1, 1), getPlayerPos(res));
        assertEquals(new Position(6, 1), getAssassinPos(res));

        // achieve bribe - success
        res = assertDoesNotThrow(() -> dmc.interact(assassinId));
        assertEquals(0, TestUtils.getInventory(res, "treasure").size());
        assertEquals(new Position(5, 1), getAssassinPos(res));

        // Assassin uses dijkstra to find the player
        res = dmc.tick(Direction.RIGHT);
        assertEquals(new Position(1, 1), getPlayerPos(res));
        assertEquals(new Position(4, 1), getAssassinPos(res));

        res = dmc.tick(Direction.RIGHT);
        assertEquals(new Position(1, 1), getPlayerPos(res));
        assertEquals(new Position(3, 1), getAssassinPos(res));

        res = dmc.tick(Direction.RIGHT);
        assertEquals(new Position(1, 1), getPlayerPos(res));
        assertEquals(new Position(3, 2), getAssassinPos(res));

        res = dmc.tick(Direction.RIGHT);
        assertEquals(new Position(1, 1), getPlayerPos(res));
        assertEquals(new Position(3, 3), getAssassinPos(res));

        res = dmc.tick(Direction.RIGHT);
        assertEquals(new Position(1, 1), getPlayerPos(res));
        assertEquals(new Position(2, 3), getAssassinPos(res));

        res = dmc.tick(Direction.RIGHT);
        assertEquals(new Position(1, 1), getPlayerPos(res));
        assertEquals(new Position(1, 3), getAssassinPos(res));

        res = dmc.tick(Direction.RIGHT);
        assertEquals(new Position(1, 1), getPlayerPos(res));
        assertEquals(new Position(0, 3), getAssassinPos(res));

        res = dmc.tick(Direction.RIGHT);
        assertEquals(new Position(1, 1), getPlayerPos(res));
        assertEquals(new Position(0, 2), getAssassinPos(res));

        res = dmc.tick(Direction.RIGHT);
        assertEquals(new Position(1, 1), getPlayerPos(res));
        assertEquals(new Position(0, 1), getAssassinPos(res));

        // Ally follows the player
        res = dmc.tick(Direction.LEFT);
        assertEquals(new Position(0, 1), getPlayerPos(res));
        assertEquals(new Position(1, 1), getAssassinPos(res));

        res = dmc.tick(Direction.DOWN);
        assertEquals(new Position(0, 2), getPlayerPos(res));
        assertEquals(new Position(0, 1), getAssassinPos(res));

        res = dmc.tick(Direction.DOWN);
        assertEquals(new Position(0, 3), getPlayerPos(res));
        assertEquals(new Position(0, 2), getAssassinPos(res));
    }

    private Position getPlayerPos(DungeonResponse res) {
        return TestUtils.getEntities(res, "player").get(0).getPosition();
    }

    private Position getAssassinPos(DungeonResponse res) {
        return TestUtils.getEntities(res, "assassin").get(0).getPosition();
    }
}
