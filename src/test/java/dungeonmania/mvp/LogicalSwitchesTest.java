package dungeonmania.mvp;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import dungeonmania.DungeonManiaController;
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

        // assert after movement that lightbulb is still off since only one conductor
        assertEquals("light_bulb_on", checkActiveLight(res, 0).getType());
    }

    private EntityResponse checkActiveLight(DungeonResponse res, int ind) {
        return TestUtils.getEntities(res, "light_bulb_on").get(ind);
    }

    private EntityResponse checkOffLight(DungeonResponse res, int ind) {
        return TestUtils.getEntities(res, "light_bulb_off").get(ind);
    }
}
