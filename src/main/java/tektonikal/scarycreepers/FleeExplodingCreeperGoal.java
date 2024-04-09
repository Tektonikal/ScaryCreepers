package tektonikal.scarycreepers;

import net.minecraft.entity.Entity;
import net.minecraft.entity.ai.NoPenaltyTargeting;
import net.minecraft.entity.ai.goal.Goal;
import net.minecraft.entity.ai.pathing.EntityNavigation;
import net.minecraft.entity.ai.pathing.Path;
import net.minecraft.entity.mob.CreeperEntity;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.entity.mob.PathAwareEntity;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.Vec3d;
import org.jetbrains.annotations.Nullable;

import java.util.EnumSet;

import static tektonikal.scarycreepers.ScaryCreepers.*;

public class FleeExplodingCreeperGoal extends Goal {
    protected final PathAwareEntity mob;
    private final double slowSpeed;
    private final double fastSpeed;
    protected final float fleeDistance;
    @Nullable
    protected Path fleePath;
    protected final EntityNavigation fleeingEntityNavigation;
    private MobEntity targetEntity;


    public FleeExplodingCreeperGoal(PathAwareEntity mob, float distance, double slowSpeed, double fastSpeed) {
        this.mob = mob;
        this.fleeDistance = distance;
        this.slowSpeed = slowSpeed;
        this.fastSpeed = fastSpeed;
        this.fleeingEntityNavigation = mob.getNavigation();
        this.setControls(EnumSet.of(Control.MOVE));
    }

    public boolean canStart() {
        if(!this.mob.getWorld().getGameRules().getBoolean(MOD_ENABLED)){
            return false;
        }
        this.targetEntity = getClosestCreeper();
        if (this.targetEntity == null) {
            return false;
        } else {
            //reaction time? :epictroll:
            if(((CreeperEntity)targetEntity).currentFuseTime > this.mob.getWorld().getGameRules().getInt(REACTION_TIME)){
                Vec3d vec3d = NoPenaltyTargeting.findFrom(this.mob, 16, 7, this.targetEntity.getPos());
                if (vec3d == null) {
                    return false;
                } else if (this.targetEntity.squaredDistanceTo(vec3d.x, vec3d.y, vec3d.z) < this.targetEntity.squaredDistanceTo(this.mob)) {
                    return false;
                } else {
                    this.fleePath = this.fleeingEntityNavigation.findPathTo(vec3d.x, vec3d.y, vec3d.z, 0);
                    return this.fleePath != null;
                }
            }
        }
        return false;
    }
    private MobEntity getClosestCreeper() {
        CreeperEntity temp = null;
        for(Entity e : mob.getWorld().getOtherEntities(mob, new Box(mob.getBlockPos()).expand(fleeDistance))){
            if(e instanceof CreeperEntity){
                if(((CreeperEntity)e).currentFuseTime > 0){
                    if(temp == null){
                        temp = (CreeperEntity) e;
                    }
                    if(temp.distanceTo(mob) > e.distanceTo(mob)){
                        temp = (CreeperEntity) e;
                    }
                }
            }
        }
        return temp;
    }

    public boolean shouldContinue() {
        return !this.fleeingEntityNavigation.isIdle();
    }

    public void start() {
        this.fleeingEntityNavigation.startMovingAlong(this.fleePath, this.slowSpeed);
    }

    public void stop() {
        this.targetEntity = null;
    }

    public void tick() {
        if (this.mob.squaredDistanceTo(this.targetEntity) < 49.0) {
            this.mob.getNavigation().setSpeed(this.mob.getWorld().getGameRules().get(RUN_SPEED).get());
        } else {
            this.mob.getNavigation().setSpeed(this.slowSpeed);
        }

    }
}