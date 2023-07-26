package dungeonmania.map;

import org.json.JSONObject;

import dungeonmania.entities.EntityFactory;

public class GraphNodeFactory {
    public static GraphNode createEntity(JSONObject jsonEntity, EntityFactory factory) {
        return constructEntity(jsonEntity, factory);
    }

    private static GraphNode constructEntity(JSONObject jsonEntity, EntityFactory factory) {
        switch (jsonEntity.getString("type")) {
        case "player":
        case "zombie_toast":
        case "zombie_toast_spawner":
        case "mercenary":
        case "wall":
        case "boulder":
        case "switch":
        case "exit":
        case "treasure":
        case "wood":
        case "arrow":
        case "bomb":
        case "invisibility_potion":
        case "invincibility_potion":
        case "portal":
        case "sword":
        case "spider":
        case "door":
        case "key":
        case "wire":
        case "light_bulb_AND":
        case "light_bulb_OR":
        case "light_bulb_XOR":
        case "light_bulb_COAND":
        case "switch_door_AND":
        case "switch_door_OR":
        case "switch_door_XOR":
        case "switch_door_COAND":
        case "logical_bomb_AND":
        case "logical_bomb_OR":
        case "logical_bomb_XOR":
        case "logical_bomb_COAND":
            return new GraphNode(factory.createEntity(jsonEntity));
        default:
            return null;
        }
    }
}
