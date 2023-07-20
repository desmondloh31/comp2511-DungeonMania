package dungeonmania.entities.collectables.potions;

import dungeonmania.battles.BattleStatistics;

public interface EffectState {
    public BattleStatistics applyBuff(BattleStatistics origin);
}
