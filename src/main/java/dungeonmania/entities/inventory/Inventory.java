package dungeonmania.entities.inventory;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import dungeonmania.Game;
import dungeonmania.entities.BattleItem;
import dungeonmania.entities.Entity;
import dungeonmania.entities.EntityFactory;
import dungeonmania.entities.Player;
import dungeonmania.entities.buildables.Bow;
import dungeonmania.entities.collectables.Arrow;
import dungeonmania.entities.collectables.Key;
import dungeonmania.entities.collectables.Sword;
import dungeonmania.entities.collectables.Treasure;
import dungeonmania.entities.collectables.Wood;

public class Inventory {
    private List<InventoryItem> items = new ArrayList<>();
    private static final int SHIELDWOOD = 2;
    private static final int SHIELDTREASURE = 1;
    private static final int SHIELDKEYS = 1;
    private static final int BOWWOOD = 1;
    private static final int BOWARROW = 3;

    public boolean add(InventoryItem item) {
        items.add(item);
        return true;
    }

    public void remove(InventoryItem item) {
        items.remove(item);
    }

    public List<String> getBuildables() {
        int wood = count(Wood.class);
        int arrows = count(Arrow.class);
        int treasure = count(Treasure.class);
        int keys = count(Key.class);
        List<String> result = new ArrayList<>();

        if (checkBowReq(wood, arrows)) {
            result.add("bow");
        }
        if (checkShieldReq(wood, treasure, keys)) {
            result.add("shield");
        }
        return result;
    }

    public InventoryItem checkBuildCriteria(Player p, boolean remove, boolean forceShield, EntityFactory factory) {

        List<Wood> wood = getEntities(Wood.class);
        List<Arrow> arrows = getEntities(Arrow.class);
        List<Treasure> treasure = getEntities(Treasure.class);
        List<Key> keys = getEntities(Key.class);

        if (wood.size() >= getBowWood() && arrows.size() >= getBowArrow() && !forceShield) {
            if (remove) {
                items.remove(wood.get(0));
                items.removeAll(arrows.subList(0, 3));
            }
            return factory.buildBow();

        } else if (wood.size() >= getShieldWood()
                && (treasure.size() >= getShieldTeasure() || keys.size() >= getShieldKeys())) {
            if (remove) {
                items.removeAll(wood.subList(0, 2));
                if (treasure.size() >= getShieldTeasure()) {
                    items.remove(treasure.get(0));
                } else {
                    items.remove(keys.get(0));
                }
            }
            return factory.buildShield();
        }
        return null;
    }

    public <T extends InventoryItem> T getFirst(Class<T> itemType) {
        for (InventoryItem item : items)
            if (itemType.isInstance(item))
                return itemType.cast(item);
        return null;
    }

    public <T extends InventoryItem> int count(Class<T> itemType) {
        int count = 0;
        for (InventoryItem item : items)
            if (itemType.isInstance(item))
                count++;
        return count;
    }

    public Entity getEntity(String itemUsedId) {
        for (InventoryItem item : items)
            if (((Entity) item).getId().equals(itemUsedId))
                return (Entity) item;
        return null;
    }

    public List<Entity> getEntities() {
        return items.stream().map(Entity.class::cast).collect(Collectors.toList());
    }

    public <T> List<T> getEntities(Class<T> clz) {
        return items.stream().filter(clz::isInstance).map(clz::cast).collect(Collectors.toList());
    }

    public boolean hasWeapon() {
        return getFirst(Sword.class) != null || getFirst(Bow.class) != null;
    }

    public BattleItem getWeapon() {
        BattleItem weapon = getFirst(Sword.class);
        if (weapon == null)
            return getFirst(Bow.class);
        return weapon;
    }

    public void playerUse(Game game) {
        BattleItem weapon = getFirst(Sword.class);
        weapon.use(game);
    }

    public boolean checkBowReq(int wood, int arrows) {
        if (wood >= getBowWood() && arrows >= getBowArrow()) {
            return true;
        }

        return false;
    }

    public boolean checkShieldReq(int wood, int treasure, int keys) {
        if (wood >= getShieldWood() && (treasure >= getShieldTeasure() || keys >= getShieldKeys())) {
            return true;
        }

        return false;
    }

    public int getBowWood() {
        return BOWWOOD;
    }

    public int getBowArrow() {
        return BOWARROW;
    }

    public int getShieldWood() {
        return SHIELDWOOD;
    }

    public int getShieldTeasure() {
        return SHIELDTREASURE;
    }

    public int getShieldKeys() {
        return SHIELDKEYS;
    }
}
