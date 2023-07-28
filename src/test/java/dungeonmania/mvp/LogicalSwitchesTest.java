package dungeonmania.mvp;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import dungeonmania.DungeonManiaController;
import dungeonmania.exceptions.InvalidActionException;
import dungeonmania.response.models.DungeonResponse;
import dungeonmania.response.models.EntityResponse;
import dungeonmania.util.Direction;
import dungeonmania.util.Position;

public class LogicalSwitchesTest {
    @Test
    @Tag("16-1")
    @DisplayName("Test the creation and function of Or LightBulbs")
    public void testOrLightBulb() {
        DungeonManiaController dmc = new DungeonManiaController();
        DungeonResponse initDungonRes = dmc.newGame("d_logicalSwitchTest_testOrLightBulb",
                "c_logicalSwitchTest_testOrLightBulb");

        // assert that the light bulb is initially off
        assertEquals("light_bulb_off", checkOffLight(initDungonRes, 0).getType());

        // Player moves right and moves boulder
        DungeonResponse res = dmc.tick(Direction.RIGHT);

        // assert after movement that lightbulb is now on
        assertEquals("light_bulb_on", checkActiveLight(res, 0).getType());
    }

    @Test
    @Tag("16-2")
    @DisplayName("Test when the or lightbulb is not cardinally adjacent to a active conductor")
    public void testOrLightBulbNotOn() {
        DungeonManiaController dmc = new DungeonManiaController();
        DungeonResponse initDungonRes = dmc.newGame("d_logicalSwitchTest_testOrLightBulbNotOn",
                "c_logicalSwitchTest_testOrLightBulb");

        // assert that the light bulb is initially off
        assertEquals("light_bulb_off", checkOffLight(initDungonRes, 0).getType());

        // Player moves right and moves boulder
        DungeonResponse res = dmc.tick(Direction.RIGHT);

        // assert after movement that lightbulb is still off
        assertEquals("light_bulb_off", checkOffLight(res, 0).getType());
    }

    @Test
    @Tag("16-3")
    @DisplayName("Test lightbulb does not act as conductor")
    public void testLightNotConductor() {
        DungeonManiaController dmc = new DungeonManiaController();
        DungeonResponse initDungonRes = dmc.newGame("d_logicalSwitchTest_testLightNotConductor",
                "c_logicalSwitchTest_testOrLightBulb");

        // assert that the light bulb is initially off
        assertEquals("light_bulb_off", checkOffLight(initDungonRes, 0).getType());
        assertEquals(new Position(7, 1), checkOffLight(initDungonRes, 0).getPosition());

        assertEquals("light_bulb_off", checkOffLight(initDungonRes, 1).getType());
        assertEquals(new Position(6, 1), checkOffLight(initDungonRes, 1).getPosition());

        // Player moves right and moves boulder
        DungeonResponse res = dmc.tick(Direction.RIGHT);

        // assert after movement that lightbulb is now on
        assertEquals("light_bulb_on", checkActiveLight(res, 0).getType());
        assertEquals(new Position(6, 1), checkActiveLight(res, 0).getPosition());

        // assert after movement that this lightbulb is off since light at (6, 1) is not a conductor
        assertEquals("light_bulb_off", checkOffLight(res, 0).getType());
        assertEquals(new Position(7, 1), checkOffLight(res, 0).getPosition());
    }

    @Test
    @Tag("16-4")
    @DisplayName("Test turn on multiple or lightbulbs at same conductor")
    public void testMultipleOrLightBulbs() {
        DungeonManiaController dmc = new DungeonManiaController();
        DungeonResponse initDungonRes = dmc.newGame("d_logicalSwitchTest_testMultipleOrLightBulbs",
                "c_logicalSwitchTest_testOrLightBulb");

        // assert that the light bulb is initially off
        assertEquals("light_bulb_off", checkOffLight(initDungonRes, 0).getType());
        assertEquals("light_bulb_off", checkOffLight(initDungonRes, 1).getType());

        // Player moves right and moves boulder
        DungeonResponse res = dmc.tick(Direction.RIGHT);

        // assert after movement that lightbulb is now on
        assertEquals("light_bulb_on", checkActiveLight(res, 0).getType());
        assertEquals("light_bulb_on", checkActiveLight(res, 1).getType());
    }

    @Test
    @Tag("16-5")
    @DisplayName("Test And lightbulb not on due to one conductor")
    public void testAndLightBulbs() {
        DungeonManiaController dmc = new DungeonManiaController();
        DungeonResponse initDungonRes = dmc.newGame("d_logicalSwitchTest_testAndNotOn",
                "c_logicalSwitchTest_testOrLightBulb");

        // assert that the light bulb is initially off
        assertEquals("light_bulb_off", checkOffLight(initDungonRes, 0).getType());

        // Player moves right and moves boulder
        DungeonResponse res = dmc.tick(Direction.RIGHT);

        // assert after movement that lightbulb is still off since only one conductor
        assertEquals("light_bulb_off", checkOffLight(res, 0).getType());
    }

    @Test
    @Tag("16-6")
    @DisplayName("Test And lightbulb on due to two adjacent conductors")
    public void testAndLightOn() {
        DungeonManiaController dmc = new DungeonManiaController();
        DungeonResponse initDungonRes = dmc.newGame("d_logicalSwitchTest_testAndLightOn",
                "c_logicalSwitchTest_testOrLightBulb");

        // assert that the light bulb is initially off
        assertEquals("light_bulb_off", checkOffLight(initDungonRes, 0).getType());

        // Player moves right and moves boulder
        DungeonResponse res = dmc.tick(Direction.RIGHT);

        // assert after movement that lightbulb is now on
        assertEquals("light_bulb_on", checkActiveLight(res, 0).getType());
    }

    @Test
    @Tag("16-7")
    @DisplayName("Test Xor light on for one conductor")
    public void testXorLightOn() {
        DungeonManiaController dmc = new DungeonManiaController();
        DungeonResponse initDungonRes = dmc.newGame("d_logicalSwitchTest_testXorLightOn",
                "c_logicalSwitchTest_testOrLightBulb");

        // assert that the light bulb is initially off
        assertEquals("light_bulb_off", checkOffLight(initDungonRes, 0).getType());

        // Player moves right and moves boulder
        DungeonResponse res = dmc.tick(Direction.RIGHT);

        // assert after movement that lightbulb is now on
        assertEquals("light_bulb_on", checkActiveLight(res, 0).getType());
    }

    @Test
    @Tag("16-8")
    @DisplayName("Test Xor light off for multiple activated conductors")
    public void testXorLightOff() {
        DungeonManiaController dmc = new DungeonManiaController();
        DungeonResponse initDungonRes = dmc.newGame("d_logicalSwitchTest_testXorLightoff",
                "c_logicalSwitchTest_testOrLightBulb");

        // assert that the light bulb is initially off
        assertEquals("light_bulb_off", checkOffLight(initDungonRes, 0).getType());

        // Player moves right and moves boulder
        DungeonResponse res = dmc.tick(Direction.RIGHT);

        // assert after movement that lightbulb is now on
        assertEquals("light_bulb_off", checkOffLight(res, 0).getType());
    }

    @Test
    @Tag("16-9")
    @DisplayName("Test CoAndLight active for same time conductors")
    public void testCoAndLightActivate() {
        DungeonManiaController dmc = new DungeonManiaController();
        DungeonResponse initDungonRes = dmc.newGame("d_logicalSwitchTest_testCoAndLightActivate",
                "c_logicalSwitchTest_testOrLightBulb");

        // assert that the light bulb is initially off
        assertEquals("light_bulb_off", checkOffLight(initDungonRes, 0).getType());

        // Player moves right and moves boulder
        DungeonResponse res = dmc.tick(Direction.RIGHT);

        // assert after movement that lightbulb is now on
        assertEquals("light_bulb_on", checkActiveLight(res, 0).getType());
    }

    @Test
    @Tag("16-10")
    @DisplayName("Test CoAndLight not on for different tick activated conductors")
    public void testCoAndLightOff() {
        DungeonManiaController dmc = new DungeonManiaController();
        DungeonResponse initDungonRes = dmc.newGame("d_logicalSwitchTest_testCoAndLightOff",
                "c_logicalSwitchTest_testOrLightBulb");

        // assert that the light bulb is initially off
        assertEquals("light_bulb_off", checkOffLight(initDungonRes, 0).getType());

        // Player moves right and moves boulder
        DungeonResponse res = dmc.tick(Direction.RIGHT);

        // assert after movement that lightbulb is now on
        assertEquals("light_bulb_off", checkOffLight(res, 0).getType());
    }

    @Test
    @Tag("16-11")
    @DisplayName("Test cannot move through closed switchDoor")
    public void testcannotWalkClosedDoor() {
        DungeonManiaController dmc;
        dmc = new DungeonManiaController();
        DungeonResponse res = dmc.newGame("d_logicalSwitchTest_closedDoor", "c_DoorsKeysTest_cannotWalkClosedDoor");
        Position pos = TestUtils.getEntities(res, "player").get(0).getPosition();

        // try to walk through door and fail
        res = dmc.tick(Direction.RIGHT);
        assertEquals(pos, TestUtils.getEntities(res, "player").get(0).getPosition());
    }

    @Test
    @Tag("16-12")
    @DisplayName("Test movement through activated Or Switch Door")
    public void testWalkThroughOrDoor() {
        DungeonManiaController dmc;
        dmc = new DungeonManiaController();
        DungeonResponse initDungonRes = dmc.newGame("d_logicalSwitchTest_testWalkThroughOrDoor",
                "c_DoorsKeysTest_cannotWalkClosedDoor");

        // assert that the switch door is initially off
        assertEquals("switch_door_off", checkDoorClosed(initDungonRes, 0).getType());
        // activate switch and thus switch door through logical circuit
        DungeonResponse res = dmc.tick(Direction.RIGHT);
        assertEquals("switch_door_on", checkDoorActive(res, 0).getType());

        // make player walk to open switch door
        res = dmc.tick(Direction.DOWN);
        res = dmc.tick(Direction.RIGHT);
        res = dmc.tick(Direction.RIGHT);
        res = dmc.tick(Direction.RIGHT);
        res = dmc.tick(Direction.RIGHT);
        res = dmc.tick(Direction.UP);

        // player is now on switch door
        Position pos = TestUtils.getEntities(res, "player").get(0).getPosition();
        assertEquals(new Position(6, 1), pos);
    }

    @Test
    @Tag("16-13")
    @DisplayName("Test And Switch Door not on due to one conductor")
    public void testAndSwitchDoorOff() {
        DungeonManiaController dmc = new DungeonManiaController();
        DungeonResponse initDungonRes = dmc.newGame("d_logicalSwitchTest_testAndSwitchDoorOff",
                "c_DoorsKeysTest_cannotWalkClosedDoor");

        // assert that the switch door is initally off
        assertEquals("switch_door_off", checkDoorClosed(initDungonRes, 0).getType());

        // Player moves right and moves boulder
        DungeonResponse res = dmc.tick(Direction.RIGHT);

        // assert after movement that switch door is still off since only one conductor
        assertEquals("switch_door_off", checkDoorClosed(res, 0).getType());
    }

    @Test
    @Tag("16-14")
    @DisplayName("Test And switch door on due to two adjacent conductors")
    public void testAndSwitchDoorOn() {
        DungeonManiaController dmc = new DungeonManiaController();
        DungeonResponse initDungonRes = dmc.newGame("d_logicalSwitchTest_testAndSwitchDoorOn",
                "c_logicalSwitchTest_testOrLightBulb");

        // assert that the switch door is initially off
        assertEquals("switch_door_off", checkDoorClosed(initDungonRes, 0).getType());

        // Player moves right and moves boulder
        DungeonResponse res = dmc.tick(Direction.RIGHT);

        // assert after movement that switch door is now on
        assertEquals("switch_door_on", checkDoorActive(res, 0).getType());
    }

    @Test
    @Tag("16-15")
    @DisplayName("Test Xor switch door on for one conductor")
    public void testXorSwitchDoorOn() {
        DungeonManiaController dmc = new DungeonManiaController();
        DungeonResponse initDungonRes = dmc.newGame("d_logicalSwitchTest_testXorSwitchDoorOn",
                "c_logicalSwitchTest_testOrLightBulb");

        // assert that the light bulb is initially off
        assertEquals("switch_door_off", checkDoorClosed(initDungonRes, 0).getType());

        // Player moves right and moves boulder
        DungeonResponse res = dmc.tick(Direction.RIGHT);

        // assert after movement that lightbulb is now on
        assertEquals("switch_door_on", checkDoorActive(res, 0).getType());
    }

    @Test
    @Tag("16-16")
    @DisplayName("Test Xor switch door off for multiple activated conductors")
    public void testXorSwitchDoorOff() {
        DungeonManiaController dmc = new DungeonManiaController();
        DungeonResponse initDungonRes = dmc.newGame("d_logicalSwitchTest_testXorSwitchDoorOff",
                "c_logicalSwitchTest_testOrLightBulb");

        // assert that the light bulb is initially off
        assertEquals("switch_door_off", checkDoorClosed(initDungonRes, 0).getType());

        // Player moves right and moves boulder
        DungeonResponse res = dmc.tick(Direction.RIGHT);

        // assert after movement that lightbulb is now on
        assertEquals("switch_door_off", checkDoorClosed(res, 0).getType());
    }

    @Test
    @Tag("16-17")
    @DisplayName("Test CoAnd Switch active for same time conductors")
    public void testCoAndSwitchDoorActivate() {
        DungeonManiaController dmc = new DungeonManiaController();
        DungeonResponse initDungonRes = dmc.newGame("d_logicalSwitchTest_testCoAndSwitchDoorActivate",
                "c_logicalSwitchTest_testOrLightBulb");

        // assert that the light bulb is initially off
        assertEquals("switch_door_off", checkDoorClosed(initDungonRes, 0).getType());

        // Player moves right and moves boulder
        DungeonResponse res = dmc.tick(Direction.RIGHT);

        // assert after movement that lightbulb is now on
        assertEquals("switch_door_on", checkDoorActive(res, 0).getType());
    }

    @Test
    @Tag("16-18")
    @DisplayName("Test CoAndLight not on for different tick activated conductors")
    public void testCoAndSwitchDoorOff() {
        DungeonManiaController dmc = new DungeonManiaController();
        DungeonResponse initDungonRes = dmc.newGame("d_logicalSwitchTest_testCoAndSwitchDoorOff",
                "c_logicalSwitchTest_testOrLightBulb");

        // assert that the light bulb is initially off
        assertEquals("switch_door_off", checkDoorClosed(initDungonRes, 0).getType());

        // Player moves right and moves boulder
        DungeonResponse res = dmc.tick(Direction.RIGHT);

        // assert after movement that lightbulb is now on
        assertEquals("switch_door_off", checkDoorClosed(res, 0).getType());
    }

    @Test
    @Tag("16-19")
    @DisplayName("Test deActivation of switch and logical circuit")
    public void testSwitchDeactivateAll() {
        DungeonManiaController dmc = new DungeonManiaController();
        DungeonResponse initDungonRes = dmc.newGame("d_logicalSwitchTest_testSwitchDeactivateAll",
                "c_logicalSwitchTest_testOrLightBulb");

        // assert that the switch door and light bulb is initially off
        assertEquals("switch_door_off", checkDoorClosed(initDungonRes, 0).getType());
        assertEquals("light_bulb_off", checkOffLight(initDungonRes, 0).getType());

        // Player moves right and moves boulder
        DungeonResponse res = dmc.tick(Direction.RIGHT);

        // assert after movement that switch door and lightbulb is now on
        assertEquals("switch_door_on", checkDoorActive(res, 0).getType());
        assertEquals("light_bulb_on", checkActiveLight(res, 0).getType());

        // Player moves boulder off of switch
        res = dmc.tick(Direction.DOWN);
        res = dmc.tick(Direction.RIGHT);
        res = dmc.tick(Direction.UP);

        // assert that the switch door and light bulb is off
        assertEquals("switch_door_off", checkDoorClosed(initDungonRes, 0).getType());
        assertEquals("light_bulb_off", checkOffLight(initDungonRes, 0).getType());
    }

    @Test
    @Tag("16-20")
    @DisplayName("Test placing a logical or bomb cardinally adjacent to an active switch, "
            + "removing surrounding non-player entities")
    public void placeLogicalCardinallyActive() throws InvalidActionException {
        DungeonManiaController dmc;
        dmc = new DungeonManiaController();
        DungeonResponse res = dmc.newGame("d_bombTest_placeLogicalCardinallyActive",
                "c_bombTest_placeCardinallyActive");

        // Activate Switch
        res = dmc.tick(Direction.RIGHT);

        // Pick up Bomb
        res = dmc.tick(Direction.DOWN);
        assertEquals(1, TestUtils.getInventory(res, "bomb").size());

        // Place Cardinally Adjacent
        res = dmc.tick(Direction.RIGHT);
        res = dmc.tick(TestUtils.getInventory(res, "bomb").get(0).getId());

        // Check Bomb exploded
        assertEquals(0, TestUtils.getEntities(res, "bomb").size());
        assertEquals(0, TestUtils.getEntities(res, "boulder").size());
        assertEquals(0, TestUtils.getEntities(res, "switch").size());
        assertEquals(0, TestUtils.getEntities(res, "wall").size());
        assertEquals(0, TestUtils.getEntities(res, "treasure").size());
        assertEquals(1, TestUtils.getEntities(res, "player").size());
    }

    private EntityResponse checkActiveLight(DungeonResponse res, int ind) {
        return TestUtils.getEntities(res, "light_bulb_on").get(ind);
    }

    private EntityResponse checkOffLight(DungeonResponse res, int ind) {
        return TestUtils.getEntities(res, "light_bulb_off").get(ind);
    }

    private EntityResponse checkDoorActive(DungeonResponse res, int ind) {
        return TestUtils.getEntities(res, "switch_door_on").get(ind);
    }

    private EntityResponse checkDoorClosed(DungeonResponse res, int ind) {
        return TestUtils.getEntities(res, "switch_door_off").get(ind);
    }
}
