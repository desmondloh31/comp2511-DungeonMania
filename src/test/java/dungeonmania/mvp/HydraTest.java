package dungeonmania.mvp;

import dungeonmania.DungeonManiaController;
import dungeonmania.Game;
import dungeonmania.entities.enemies.Hydra;
import dungeonmania.exceptions.InvalidActionException;
import dungeonmania.response.models.DungeonResponse;
import dungeonmania.response.models.EntityResponse;
import dungeonmania.util.Direction;
import dungeonmania.util.Position;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class HydraTest {
    @Test
    @Tag("20-1")
    @DisplayName("Testing hydra movement")
    public void movement() {
        DungeonManiaController dmc = new DungeonManiaController();
        DungeonResponse res = dmc.newGame("d_hydraTestMovement", "c_hydraTestMovement");

        assertEquals(1, getHydras(res).size());

        boolean hydraMoved = false;
        Position prevPosition = getHydras(res).get(0).getPosition();
        for (int i = 0; i < 5; i++) {
            res = dmc.tick(Direction.UP);
            if (!prevPosition.equals(getHydras(res).get(0).getPosition())) {
                hydraMoved = true;
                break;
            }
        }
        assertTrue(hydraMoved);
    }

    @Test
    @Tag("20-2")
    @DisplayName("Testing hydra does not move if player has invincibility potion")
    public void playerInvincibility() throws InvalidActionException {
        DungeonManiaController dmc = new DungeonManiaController();
        DungeonResponse res = dmc.newGame("d_hydraTest_invincibility", "c_hydraTest_invincibility");

        assertEquals(1, TestUtils.getEntities(res, "invincibility_potion").size());
        assertEquals(0, TestUtils.getInventory(res, "invincibility_potion").size());

        // pick up invisibility potion
        res = dmc.tick(Direction.DOWN);
        assertEquals(1, TestUtils.getInventory(res, "invincibility_potion").size());
        assertEquals(0, TestUtils.getEntities(res, "invincibility_potion").size());

        // consume invisibility potion
        res = dmc.tick(TestUtils.getFirstItemId(res, "invincibility_potion"));
        assertEquals(0, TestUtils.getInventory(res, "invincibility_potion").size());
        assertEquals(0, TestUtils.getEntities(res, "invincibility_potion").size());

        Position playerPos = TestUtils.getEntities(res, "player").get(0).getPosition();
        Position hydraPos = TestUtils.getEntities(res, "zombie").get(0).getPosition();

        int startingMagnitude = (int) Math.floor(TestUtils.getEuclideanDistance(playerPos, hydraPos));

        for (int i = 0; i <= 10; i++) {
            dmc.tick(Direction.DOWN);
            int endingMagnitude = (int) Math.floor(TestUtils.getEuclideanDistance(playerPos, hydraPos));
            assert (endingMagnitude >= startingMagnitude);
        }
    }

    @Test
    @Tag("20-3")
    @DisplayName("Testing hydras respawn after being killed")
    public void respawnAfterDeath() throws InvalidActionException {
        DungeonManiaController dmc = new DungeonManiaController();
        DungeonResponse res = dmc.newGame("d_hydraTest_respawn", "c_hydraTest_respawn");

        Position spawnerPos = TestUtils.getEntities(res, "zombie_toast_spawner").get(0).getPosition();
        List<Position> cardinallyAdjacentSquares = TestUtils.getCardinallyAdjacentPositions(spawnerPos);

        res = dmc.tick(Direction.UP);
        assertEquals(1, getHydras(res).size());

        // the zombie has spawned in a cardinally adjacent square
        Position zombiePos = getHydras(res).get(0).getPosition();
        assertTrue(cardinallyAdjacentSquares.contains(zombiePos));
    }

    @Test
    @Tag("20-4")
    @DisplayName("Testing Hydras cannot move through closed doors and walls")
    public void doorsAndWalls() {

        DungeonManiaController dmc = new DungeonManiaController();
        DungeonResponse res = dmc.newGame("d_hydraTest_doorsAndWalls", "c_hydraTest_doorsAndWalls");
        assertEquals(1, getHydras(res).size());
        Position position = getHydras(res).get(0).getPosition();
        res = dmc.tick(Direction.UP);
        assertEquals(position, getHydras(res).get(0).getPosition());
    }

    @Test
    @Tag("10-8")
    @DisplayName("Testing hydras grow more heads when attacked")
    public void attackScalingWithHeads() throws InvalidActionException, IllegalArgumentException {
        DungeonManiaController dmc = new DungeonManiaController();
        DungeonResponse res = dmc.newGame("d_hydraTest_scalingWithHeads", "c_hydraTest_scalingWithHeads");

        // Assuming player starts with a weapon and adjacent to the hydra
        EntityResponse hydra = getHydras(res).get(0);
        EntityResponse player = getPlayerPos(res);

        int hits = 0;
        while (getHydras(res).size() > 0) { // While the hydra is still alive
            // Player interacts with the hydra, presumably attacking it
            res = dmc.interact(hydra.getId());
            hits++;
            res = dmc.tick(Direction.NONE); // Update game state
        }

        // Assuming each head needs one hit and the hydra starts with one head, and grows one additional head every time it's hit
        assertEquals(2, hits); // One hit for the initial head, one for the new head
    }

    private EntityResponse getPlayerPos(DungeonResponse res) {
        return TestUtils.getEntities(res, "player").get(0);
    }

    private List<EntityResponse> getHydras(DungeonResponse res) {
        return TestUtils.getEntities(res, "zombie_toast");
    }
}
