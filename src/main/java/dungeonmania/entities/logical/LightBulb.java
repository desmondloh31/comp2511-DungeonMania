package dungeonmania.entities.logical;

import java.util.List;

import dungeonmania.entities.Entity;
import dungeonmania.map.GameMap;
import dungeonmania.util.Position;

public class LightBulb extends LogicalEntity {
    private LogicalStrategy logic;

    public LightBulb(Position position, LogicalStrategy logic) {
        super(position, logic);
        this.logic = logic;
    }

    @Override
    public boolean isConductor() {
        return false;
    }

    @Override
    public boolean isActive(Entity targetEntity, List<Entity> allCardinalEntities, GameMap map) {
        System.out.println("Entity isActive for: ");
        System.out.println(targetEntity);
        System.out.println("Whats cardinal adjacent in isActive: ");
        System.out.println(allCardinalEntities);
        System.out.println("With logic of type: ");
        System.out.println(logic);

        System.out.println("IS THIS ON??? ");
        System.out.println(this.logic.isActive(targetEntity, allCardinalEntities, map));
        return this.logic.isActive(targetEntity, allCardinalEntities, map);
    }
}
