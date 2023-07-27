package dungeonmania.mvp;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

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
        public void testCreateLightBulb() {
                DungeonManiaController dmc = new DungeonManiaController();
                DungeonResponse initDungonRes = dmc.newGame("d_logicalSwitchTest_testOrLightBulb",
                                "c_logicalSwitchTest_testOrLightBulb");

                EntityResponse initPlayer = TestUtils.getPlayer(initDungonRes).get();

                // Player moves right and moves boulder
                DungeonResponse res = dmc.tick(Direction.RIGHT);
                EntityResponse actualPlayer = TestUtils.getPlayer(res).get();

                // assert after movement
        }
}
