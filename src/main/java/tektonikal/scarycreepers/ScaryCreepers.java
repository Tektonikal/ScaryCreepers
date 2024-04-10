package tektonikal.scarycreepers;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.gamerule.v1.GameRuleFactory;
import net.fabricmc.fabric.api.gamerule.v1.GameRuleRegistry;
import net.fabricmc.fabric.api.gamerule.v1.rule.DoubleRule;
import net.minecraft.world.GameRules;

public class ScaryCreepers implements ModInitializer {

	public static final GameRules.Key<GameRules.BooleanRule> MOD_ENABLED =
			GameRuleRegistry.register("SC_modEnabled", GameRules.Category.MOBS, GameRuleFactory.createBooleanRule(true));
	public static final GameRules.Key<DoubleRule> RUN_SPEED =
			GameRuleRegistry.register("SC_runSpeed", GameRules.Category.MOBS, GameRuleFactory.createDoubleRule(1.3, 0.5, 3));
	public static final GameRules.Key<GameRules.IntRule> REACTION_TIME =
			GameRuleRegistry.register("SC_reactionTimeTicks", GameRules.Category.MOBS, GameRuleFactory.createIntRule(5, 0, 60));
	public static final GameRules.Key<GameRules.IntRule> FLEE_DISTANCE =
			GameRuleRegistry.register("SC_fleeDistance", GameRules.Category.MOBS, GameRuleFactory.createIntRule(5, 2, 10));

	@Override
	public void onInitialize() {
	}
}