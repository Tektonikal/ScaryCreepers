package tektonikal.scarycreepers.mixin;

import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.Targeter;
import net.minecraft.entity.ai.goal.GoalSelector;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.entity.mob.PathAwareEntity;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import tektonikal.scarycreepers.FleeExplodingCreeperGoal;

import static tektonikal.scarycreepers.ScaryCreepers.RUN_SPEED;

@Mixin(MobEntity.class)
public abstract class ExampleMixin extends LivingEntity implements Targeter {
	@Shadow @Final protected GoalSelector goalSelector;

	protected ExampleMixin(EntityType<? extends LivingEntity> entityType, World world) {
		super(entityType, world);
	}
	@Inject(method = "<init>", at = @At("TAIL"))
	private void inject(CallbackInfo ci){
		if((Object)this instanceof PathAwareEntity){
			this.goalSelector.add(2, new FleeExplodingCreeperGoal((PathAwareEntity) (MobEntity)(Object)this, 5.0F,1.0f, this.getWorld().getGameRules().get(RUN_SPEED).get()));
		}
	}
}