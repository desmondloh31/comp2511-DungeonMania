package dungeonmania.mvp;

import dungeonmania.DungeonManiaController;
import dungeonmania.response.models.DungeonResponse;
import dungeonmania.util.Direction;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class ComplexGoalsTest {
    @Test
    @Tag("14-1")
    @DisplayName("Testing a map with 4 conjunction goal")
    public void andAll() {
        DungeonManiaController dmc;
        dmc = new DungeonManiaController();
        DungeonResponse res = dmc.newGame("d_complexGoalsTest_andAll", "c_complexGoalsTest_andAll");

        assertTrue(TestUtils.getGoals(res).contains(":exit"));
        assertTrue(TestUtils.getGoals(res).contains(":treasure"));
        assertTrue(TestUtils.getGoals(res).contains(":boulders"));

        // kill spider
        res = dmc.tick(Direction.RIGHT);
        assertTrue(TestUtils.getGoals(res).contains(":exit"));
        assertTrue(TestUtils.getGoals(res).contains(":treasure"));
        assertTrue(TestUtils.getGoals(res).contains(":boulders"));

        // move boulder onto switch
        res = dmc.tick(Direction.RIGHT);
        assertTrue(TestUtils.getGoals(res).contains(":exit"));
        assertTrue(TestUtils.getGoals(res).contains(":treasure"));
        assertFalse(TestUtils.getGoals(res).contains(":boulders"));

        // pickup treasure
        res = dmc.tick(Direction.DOWN);
        assertTrue(TestUtils.getGoals(res).contains(":exit"));
        assertFalse(TestUtils.getGoals(res).contains(":treasure"));
        assertFalse(TestUtils.getGoals(res).contains(":boulders"));

        // move to exit
        res = dmc.tick(Direction.DOWN);
        assertEquals("", TestUtils.getGoals(res));
    }

    @Test
    @Tag("14-2")
    @DisplayName("Testing a map with 4 disjunction goal")
    public void orAll() {
        DungeonManiaController dmc;
        dmc = new DungeonManiaController();
        DungeonResponse res = dmc.newGame("d_complexGoalsTest_orAll", "c_complexGoalsTest_orAll");

        assertTrue(TestUtils.getGoals(res).contains(":exit"));
        assertTrue(TestUtils.getGoals(res).contains(":treasure"));
        assertTrue(TestUtils.getGoals(res).contains(":boulders"));

        // move onto exit
        res = dmc.tick(Direction.RIGHT);

        // assert goal met
        assertEquals("", TestUtils.getGoals(res));
    }

    @Test
    @Tag("14-3")
    @DisplayName("Testing that the exit goal must be achieved last in EXIT and TREASURE")
    public void exitAndTreasureOrder() {
        DungeonManiaController dmc;
        dmc = new DungeonManiaController();
        DungeonResponse res = dmc.newGame("d_complexGoalsTest_exitAndTreasureOrder",
                "c_complexGoalsTest_exitAndTreasureOrder");

        assertTrue(TestUtils.getGoals(res).contains(":exit"));
        assertTrue(TestUtils.getGoals(res).contains(":treasure"));

        // move player onto exit
        res = dmc.tick(Direction.RIGHT);

        // don't check state of exit goal in string
        assertTrue(TestUtils.getGoals(res).contains(":treasure"));

        // move player to pick up treasure
        res = dmc.tick(Direction.RIGHT);

        // assert treasure goal met, but goal string is not empty
        assertFalse(TestUtils.getGoals(res).contains(":treasure"));
        assertNotEquals("", TestUtils.getGoals(res));

        // move player back onto exit
        res = dmc.tick(Direction.LEFT);

        // assert goal met
        assertEquals("", TestUtils.getGoals(res));
    }

    @Test
    @Tag("14-4")
    @DisplayName("Testing that the exit goal must be achieved last and EXIT and TREASURE")
    public void exitAndBouldersAndTreasureOrder() {
        DungeonManiaController dmc;
        dmc = new DungeonManiaController();
        DungeonResponse res = dmc.newGame("d_complexGoalsTest_exitAndBouldersAndTreasureOrder",
                "c_complexGoalsTest_exitAndBouldersAndTreasureOrder");

        assertTrue(TestUtils.getGoals(res).contains(":exit"));
        assertTrue(TestUtils.getGoals(res).contains(":treasure"));
        assertTrue(TestUtils.getGoals(res).contains(":boulders"));

        // move player onto treasure
        res = dmc.tick(Direction.RIGHT);

        // assert treasure goal met
        assertTrue(TestUtils.getGoals(res).contains(":exit"));
        assertTrue(TestUtils.getGoals(res).contains(":boulders"));
        assertFalse(TestUtils.getGoals(res).contains(":treasure"));

        // move player onto exit
        res = dmc.tick(Direction.RIGHT);

        // assert treasure goal remains achieved
        // don't check state of exit goal in string
        assertTrue(TestUtils.getGoals(res).contains(":boulders"));
        assertFalse(TestUtils.getGoals(res).contains(":treasure"));

        // move boulder onto switch, but goal string is not empty
        res = dmc.tick(Direction.RIGHT);
        assertFalse(TestUtils.getGoals(res).contains(":boulders"));
        assertFalse(TestUtils.getGoals(res).contains(":treasure"));
        assertNotEquals("", TestUtils.getGoals(res));

        // move back onto exit
        res = dmc.tick(Direction.LEFT);

        // assert goal met
        assertEquals("", TestUtils.getGoals(res));
    }

    @Test
    @Tag("14-6")
    @DisplayName("Testing a switch goal can be achieved and then become unachieved")
    public void switchUnachieved() {
        DungeonManiaController dmc;
        dmc = new DungeonManiaController();
        DungeonResponse res = dmc.newGame("d_complexGoalsTest_switchUnachieved", "c_complexGoalsTest_switchUnachieved");

        assertTrue(TestUtils.getGoals(res).contains(":exit"));
        assertTrue(TestUtils.getGoals(res).contains(":boulders"));

        // move boulder onto switch
        res = dmc.tick(Direction.RIGHT);

        // assert boulder goal met
        assertTrue(TestUtils.getGoals(res).contains(":exit"));
        assertFalse(TestUtils.getGoals(res).contains(":boulders"));

        // move boulder off switch
        res = dmc.tick(Direction.RIGHT);

        // assert boulder goal unmet
        assertTrue(TestUtils.getGoals(res).contains(":exit"));
        assertTrue(TestUtils.getGoals(res).contains(":boulders"));
    }

    @Test
    @Tag("14-7")
    @DisplayName("Testing complex EnemyGoal with multiple enemies")
    public void multipleEnemiesGoalTest() {
        DungeonManiaController dmc = new DungeonManiaController();
        DungeonResponse res = dmc.newGame("d_complexGoalsTest_multipleEnemies", "c_complexGoalsTest_multipleEnemies");

        assertNotNull(res);
        assertNotNull(TestUtils.getGoals(res));
        assertTrue(TestUtils.getGoals(res).contains(":enemies"));

        res = dmc.tick(Direction.RIGHT);
        assertNotNull(res);
        assertNotNull(TestUtils.getGoals(res));
        assertFalse(TestUtils.getGoals(res).contains(":enemies"));

        res = dmc.tick(Direction.UP);
        assertNotNull(res);
        assertNotNull(TestUtils.getGoals(res));
        assertFalse(TestUtils.getGoals(res).contains(":enemies"));

        res = dmc.tick(Direction.LEFT);
        assertNotNull(res);
        assertNotNull(TestUtils.getGoals(res));
        assertEquals("", TestUtils.getGoals(res));
    }

    @Test
    @Tag("14-8")
    @DisplayName("Testing complex SpawnerGoal with multiple spawners")
    public void multipleSpawnerGoalTest() {
        DungeonManiaController dmc = new DungeonManiaController();
        DungeonResponse res = dmc.newGame("d_complexGoalsTest_multipleSpawners", "c_complexGoalsTest_multipleSpawners");

        assertNotNull(res);
        assertNotNull(TestUtils.getGoals(res));
        assertTrue(TestUtils.getGoals(res).contains(":spawners"));

        res = dmc.tick(Direction.RIGHT);
        assertNotNull(res);
        assertNotNull(TestUtils.getGoals(res));
        assertTrue(TestUtils.getGoals(res).contains(":spawners"));

        res = dmc.tick(Direction.UP);
        assertNotNull(res);
        assertNotNull(TestUtils.getGoals(res));
        assertTrue(TestUtils.getGoals(res).contains(":spawners"));

        res = dmc.tick(Direction.LEFT);
        assertNotNull(res);
        assertNotNull(TestUtils.getGoals(res));
        assertEquals(":spawners", TestUtils.getGoals(res));
    }

    @Test
    @Tag("14-10")
    @DisplayName("Testing a complex goal with both enemies and spawners")
    public void enemiesAndSpawners() {
        DungeonManiaController dmc = new DungeonManiaController();
        DungeonResponse res = dmc.newGame("d_complexGoalsTest_enemiesAndSpawners",
                "c_complexGoalsTest_enemiesAndSpawners");

        assertTrue(TestUtils.getGoals(res).contains(":enemies"));
        assertTrue(TestUtils.getGoals(res).contains(":spawners"));

        res = dmc.tick(Direction.RIGHT);
        assertTrue(TestUtils.getGoals(res).contains(":enemies"));
        assertTrue(TestUtils.getGoals(res).contains(":spawners"));

        res = dmc.tick(Direction.UP);
        assertTrue(TestUtils.getGoals(res).contains(":enemies"));
        assertTrue(TestUtils.getGoals(res).contains(":spawners"));

        res = dmc.tick(Direction.LEFT);
        assertEquals("(:enemies AND :spawners)", TestUtils.getGoals(res));
    }

    @Test
    @Tag("14-11")
    @DisplayName("Testing that the exit goal must be achieved last and EXIT and TREASURE")
    public void exitAndEnemiesAndSpawnerOrder() {
        DungeonManiaController dmc;
        dmc = new DungeonManiaController();
        DungeonResponse res = dmc.newGame("d_complexGoalsTest_exitAndBouldersAndTreasureOrder",
                "c_complexGoalsTest_exitAndBouldersAndTreasureOrder");

        assertTrue(TestUtils.getGoals(res).contains(":exit"));
        assertFalse(TestUtils.getGoals(res).contains(":spawners"));
        assertFalse(TestUtils.getGoals(res).contains(":enemies"));

        // move player onto treasure
        res = dmc.tick(Direction.RIGHT);

        // assert treasure goal met
        assertTrue(TestUtils.getGoals(res).contains(":exit"));
        assertFalse(TestUtils.getGoals(res).contains(":enemies"));
        assertFalse(TestUtils.getGoals(res).contains(":spawners"));

        // move player onto exit
        res = dmc.tick(Direction.RIGHT);

        // assert treasure goal remains achieved
        // don't check state of exit goal in string
        assertFalse(TestUtils.getGoals(res).contains(":enemies"));
        assertFalse(TestUtils.getGoals(res).contains(":spawners"));

        // move boulder onto switch, but goal string is not empty
        res = dmc.tick(Direction.RIGHT);
        assertFalse(TestUtils.getGoals(res).contains(":enemies"));
        assertFalse(TestUtils.getGoals(res).contains(":spawners"));
        assertNotEquals("", TestUtils.getGoals(res));

        // move back onto exit
        res = dmc.tick(Direction.LEFT);

        // assert goal met
        assertEquals("", TestUtils.getGoals(res));
    }

}
