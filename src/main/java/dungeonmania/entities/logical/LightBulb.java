package dungeonmania.entities.logical;

import dungeonmania.util.Position;

public class LightBulb extends LogicalEntity {
    private boolean light;

    public LightBulb(Position position, LogicalStrategy logic) {
        super(position, logic);
    }

    public void switchLight() {
        this.light = !this.light;
    }
}
