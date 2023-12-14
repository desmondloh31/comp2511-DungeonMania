package dungeonmania.entities.collectables.potions;

import dungeonmania.battles.BattleStatistics;

public class InvisibleState implements EffectState {
    public BattleStatistics applyBuff(BattleStatistics origin) {
        return BattleStatistics.applyBuff(origin, new BattleStatistics(0, 0, 0, 1, 1, false, false));
    }
}