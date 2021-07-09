package baguchan.hunterillager.entity.ai;

import baguchan.hunterillager.entity.HunterIllagerEntity;
import baguchan.hunterillager.init.HunterItems;
import baguchan.hunterillager.item.BoomerangItem;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.goal.Goal;
import net.minecraft.entity.projectile.ProjectileHelper;
import net.minecraft.item.BowItem;

public class BoomeranAttackGoal extends Goal {
	private final HunterIllagerEntity mob;
	private int attackIntervalMin;
	private final float attackRadiusSqr;
	private int attackTime = -1;
	private int seeTime;

	public BoomeranAttackGoal(HunterIllagerEntity hunterIllagerEntity, int attackIntervalMin, float attackRadiusSqr) {
		this.mob = hunterIllagerEntity;
		this.attackIntervalMin = attackIntervalMin;
		this.attackRadiusSqr = attackRadiusSqr * attackRadiusSqr;
	}

	@Override
	public boolean canUse() {
		LivingEntity entity = mob.getTarget();
		return (mob.getOffhandItem().getItem() instanceof BoomerangItem) && !(mob.getMainHandItem().getItem() instanceof BowItem) && entity != null && entity.isAlive() && entity.distanceToSqr(mob) > 16D;
	}

	@Override
	public void start() {
		super.start();
	}

	@Override
	public void stop() {
		super.stop();
		this.seeTime = 0;
		this.attackTime = -1;
		this.mob.stopUsingItem();
	}

	public void tick() {
		LivingEntity livingentity = this.mob.getTarget();
		if (livingentity != null) {
			double d0 = this.mob.distanceToSqr(livingentity.getX(), livingentity.getY(), livingentity.getZ());
			boolean flag = this.mob.getSensing().canSee(livingentity);
			boolean flag1 = this.seeTime > 0;
			if (flag != flag1) {
				this.seeTime = 0;
			}

			if (flag) {
				++this.seeTime;
			} else {
				--this.seeTime;
			}

			if (this.mob.isUsingItem()) {
				if (!flag && this.seeTime < -60) {
					this.mob.stopUsingItem();
				} else if (flag) {
					int i = this.mob.getTicksUsingItem();
					if (i >= 20) {
						this.mob.stopUsingItem();
						this.mob.performBoomeranAttack(livingentity, BowItem.getPowerForTime(i));
						this.attackTime = this.attackIntervalMin;
					}
				}
			} else if (--this.attackTime <= 0 && this.seeTime >= -60) {
				this.mob.startUsingItem(ProjectileHelper.getWeaponHoldingHand(this.mob, HunterItems.BOOMERANG));
			}

		}
	}
}
