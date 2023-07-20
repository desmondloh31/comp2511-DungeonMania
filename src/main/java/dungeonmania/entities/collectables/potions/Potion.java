package dungeonmania.entities.collectables.potions;

import dungeonmania.Game;
import dungeonmania.battles.BattleStatistics;
import dungeonmania.entities.BattleItem;
import dungeonmania.entities.collectables.Collectable;
import dungeonmania.util.Position;

public abstract class Potion extends Collectable implements BattleItem {
    private int duration;
    private EffectState state;

    public Potion(Position position, int duration, EffectState state) {
        super(position);
        this.duration = duration;
        this.state = state;
    }

    @Override
    public void use(Game game) {
        return;
    }

    public int getDuration() {
        return duration;
    }

    @Override
    public BattleStatistics applyBuff(BattleStatistics origin) {
        return origin;
    }

    @Override
    public int getDurability() {
        return 1;
    }
}
