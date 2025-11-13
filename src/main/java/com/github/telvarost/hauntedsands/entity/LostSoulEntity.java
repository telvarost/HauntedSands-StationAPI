package com.github.telvarost.hauntedsands.entity;

import com.github.telvarost.hauntedsands.HauntedSands;
import net.minecraft.block.Block;
import net.minecraft.entity.Entity;
import net.minecraft.entity.FlyingEntity;
import net.minecraft.entity.Monster;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ArmorItem;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtList;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;
import net.modificationstation.stationapi.api.server.entity.HasTrackingParameters;
import net.modificationstation.stationapi.api.server.entity.MobSpawnDataProvider;
import net.modificationstation.stationapi.api.util.Identifier;
import net.modificationstation.stationapi.api.util.TriState;

@HasTrackingParameters(updatePeriod = 2, sendVelocity = TriState.TRUE, trackingDistance = 30)
public class LostSoulEntity extends FlyingEntity implements Monster, MobSpawnDataProvider {
	/**
	 * Time before the Ghast will pick a new target location to float to
	 */
	public int floatDuration = 0;
	public double targetX;
	public double targetY;
	public double targetZ;
	private Entity target = null;
	private int angerCooldown = 0;
	public int lastChargeTime = 0;
	public int chargeTime = 0;
	public int followCooldown = 0;
	public int graveX = 0;
	public int graveY = 0;
	public int graveZ = 0;
	public ItemStack[] armor = new ItemStack[4];
	protected int attackDamage = 4;
	private int damageSpill = 0;

	public LostSoulEntity(World world) {
		super(world);
		this.texture = "/assets/hauntedsands/stationapi/textures/entity/lostsoul.png";
		this.setBoundingBoxSpacing(0.6F, 1.8F);
		this.fireImmune = true;
		this.noClip = true;
		this.prevHealth = 10;
		this.health = 10;
	}

	public LostSoulEntity(World world, int graveX, int graveY, int graveZ) {
		this(world);
		this.graveX = graveX;
		this.graveY = graveY;
		this.graveZ = graveZ;
		this.x = (double)graveX + 0.5;
		this.y = (double)graveY + 0.5;
		this.z = (double)graveZ + 0.5;
	}
	@Override
	public Identifier getHandlerIdentifier()
	{
		return Identifier.of(HauntedSands.HAUNTED_SANDS, "LostSoul");
	}

	@Override
	public void writeNbt(NbtCompound nbt) {
		super.writeNbt(nbt);

		nbt.putInt("GraveX", this.graveX);
		nbt.putInt("GraveY", this.graveY);
		nbt.putInt("GraveZ", this.graveZ);

		NbtList armorNbtList = new NbtList();
		for (int var4 = 0; var4 < this.armor.length; var4++) {
			if (this.armor[var4] != null) {
				NbtCompound var5 = new NbtCompound();
				var5.putByte("Armor", (byte)(var4));
				this.armor[var4].writeNbt(var5);
				armorNbtList.add(var5);
			}
		}
		nbt.put("Inventory", armorNbtList);
	}

	@Override
	public void readNbt(NbtCompound nbt) {
		this.armor = new ItemStack[4];
		super.readNbt(nbt);

		this.graveX = nbt.getInt("GraveX");
		this.graveY = nbt.getInt("GraveY");
		this.graveZ = nbt.getInt("GraveZ");

		NbtList armorNbtList = nbt.getList("Inventory");
		for (int var2 = 0; var2 < armorNbtList.size(); var2++) {
			NbtCompound var3 = (NbtCompound)armorNbtList.get(var2);
			int var4 = var3.getByte("Armor") & 255;
			ItemStack var5 = new ItemStack(var3);
			if (var5.getItem() != null) {
				if (var4 < this.armor.length) {
					this.armor[var4] = var5;
				}
			}
		}
	}

	@Override
	protected boolean canDespawn() {
		return false;
	}

	@Override
	public boolean canBreatheInWater() {
		return true;
	}

	@Override
	public boolean isInsideWall() {
		for (int var1 = 0; var1 < 8; var1++) {
			float var2 = ((float)((var1 >> 0) % 2) - 0.5F) * this.width * 0.9F;
			float var3 = ((float)((var1 >> 1) % 2) - 0.5F) * 0.1F;
			float var4 = ((float)((var1 >> 2) % 2) - 0.5F) * this.width * 0.9F;
			int var5 = MathHelper.floor(this.x + (double)var2);
			int var6 = MathHelper.floor(this.y + (double)this.getEyeHeight() + (double)var3);
			int var7 = MathHelper.floor(this.z + (double)var4);
			if (!this.world.isRemote && this.world.shouldSuffocate(var5, var6, var7)) {
				if (0 >= followCooldown) {
					if (null != this.target) {
						double squaredDistance = this.target.getSquaredDistance(this) / 16;
						double var11 = this.target.x - this.x;
						double var13 = this.target.boundingBox.minY + (double)(this.target.height) - (this.y + (double)(this.height / 2.0F));
						double var15 = this.target.z - this.z;
						this.velocityX += Math.min(2.0, (var11 / squaredDistance));
						this.velocityY += Math.min(2.0, (var13 / squaredDistance));
						this.velocityZ += Math.min(2.0, (var15 / squaredDistance));
						followCooldown += 40;
					} else if (this.graveX == 0 && this.graveY == 0 && this.graveZ == 0) {
						System.out.println("Lost soul unable to find its grave and has departed from the world!");
						this.markDead();
					} else {
						double relocateX = this.graveX + 0.5 + (Math.random() - 0.5) * 2.0;
						double relocateY = this.graveY + 1.5 + (Math.random() - 0.5);
						double relocateZ = this.graveZ + 0.5 + (Math.random() - 0.5) * 2.0;
						this.setPositionAndAnglesKeepPrevAngles(
								(double)relocateX,
								(double)relocateY,
								(double)relocateZ,
								this.world.random.nextFloat() * 360.0F,
								0.0F);
					}
				} else {
					followCooldown--;
				}
			}
		}

		return false;
	}

	@Override
	protected void tickInVoid() {
		if (this.graveX == 0 && this.graveY == 0 && this.graveZ == 0) {
			System.out.println("Lost soul unable to find its grave and has left for the underworld!");
			this.markDead();
		} else {
			double relocateX = this.graveX + 0.5 + (Math.random() - 0.5) * 2.0;
			double relocateY = this.graveY + 1.5 + (Math.random() - 0.5);
			double relocateZ = this.graveZ + 0.5 + (Math.random() - 0.5) * 2.0;
			this.setPositionAndAnglesKeepPrevAngles(
					(double)relocateX,
					(double)relocateY,
					(double)relocateZ,
					this.world.random.nextFloat() * 360.0F,
					0.0F);
		}
	}

	@Override
	protected void initDataTracker() {
		super.initDataTracker();
		this.dataTracker.startTracking(16, (byte)0);
	}

	@Override
	public void tick() {
		super.tick();
		byte var1 = this.dataTracker.getByte(16);
		//this.texture = var1 == 1 ? "/mob/ghast_fire.png" : "/mob/ghast.png";
	}

	@Override
	protected void tickLiving() {
		if (!this.world.isRemote && this.world.difficulty == 0) {
			this.markDead();
		}

		this.tryDespawn();
		this.lastChargeTime = this.chargeTime;
		double var1 = this.targetX - this.x;
		double var3 = this.targetY - this.y;
		double var5 = this.targetZ - this.z;
		double var7 = (double)MathHelper.sqrt(var1 * var1 + var3 * var3 + var5 * var5);
		if (var7 < 1.0 || var7 > 60.0) {
			this.targetX = this.x + (double)((this.random.nextFloat() * 2.0F - 1.0F) * 16.0F);
			this.targetY = this.y + (double)((this.random.nextFloat() * 2.0F - 1.0F) * 16.0F);
			this.targetZ = this.z + (double)((this.random.nextFloat() * 2.0F - 1.0F) * 16.0F);
		}

		if (this.floatDuration-- <= 0) {
			this.floatDuration = this.floatDuration + this.random.nextInt(5) + 2;
			if (this.canReach(this.targetX, this.targetY, this.targetZ, var7)) {
				if (0 < this.random.nextInt(2)) {
					this.velocityX += var1 / var7 * 0.1;
					this.velocityY += var3 / var7 * 0.1;
					this.velocityZ += var5 / var7 * 0.1;
				} else {
					this.velocityX += var1 / var7 * -0.1;
					this.velocityY += var3 / var7 * -0.1;
					this.velocityZ += var5 / var7 * -0.1;
				}
			} else {
				this.targetX = this.x;
				this.targetY = this.y;
				this.targetZ = this.z;
			}
		}

		if (this.target != null && this.target.dead) {
			this.target = null;
		} else if (this.target != null) {
			float var2 = this.target.getDistance(this);
			if (this.canSee(this.target)) {
				this.attack(this.target, var2);
			} else {
				this.resetAttack(this.target, var2);
			}
		}

		if (this.target == null || this.angerCooldown-- <= 0) {
			this.target = this.world.getClosestPlayer(this, 100.0);
			if (this.target != null) {
				this.angerCooldown = 20;
			}
		}

		double var9 = 64.0;
		if (this.target != null && this.target.getSquaredDistance(this) < var9 * var9) {
			double squaredDistance = this.target.getSquaredDistance(this) / 16;
			double var11 = this.target.x - this.x;
			double var13 = this.target.boundingBox.minY + (double)(this.target.height) - (this.y + (double)(this.height / 2.0F));
			double var15 = this.target.z - this.z;
			this.bodyYaw = this.yaw = -((float)Math.atan2(var11, var15)) * 180.0F / (float) Math.PI;
			if (this.canReach(this.target.x, this.targetY, this.targetZ, 64)) { // canSee
				if (this.chargeTime == 10) {
					this.world.playSound(this, "hauntedsands:entity.lostsoul.charge", this.getSoundVolume(), (this.random.nextFloat() - this.random.nextFloat()) * 0.2F + 1.0F);
				}

				this.chargeTime++;
				if (this.chargeTime == 20) {
					this.world.playSound(this, "hauntedsands:entity.lostsoul.attack", this.getSoundVolume(), (this.random.nextFloat() - this.random.nextFloat()) * 0.2F + 1.0F);
					this.velocityX += Math.min(2.0, (var11 / squaredDistance));
					this.velocityY += Math.min(2.0, (var13 / squaredDistance));
					this.velocityZ += Math.min(2.0, (var15 / squaredDistance));
					this.chargeTime = -40;
				}
			} else if (this.chargeTime > 0) {
				this.chargeTime--;
			}
		} else {
			this.bodyYaw = this.yaw = -((float)Math.atan2(this.velocityX, this.velocityZ)) * 180.0F / (float) Math.PI;
			if (this.chargeTime > 0) {
				this.chargeTime--;
			}
		}

		if (!this.world.isRemote) {
			byte var21 = this.dataTracker.getByte(16);
			byte var12 = (byte)(this.chargeTime > 10 ? 1 : 0);
			if (var21 != var12) {
				this.dataTracker.set(16, var12);
			}
		}
	}

	/**
	 * @return Returns true if the entity can reach destination without collision
	 */
	private boolean canReach(double x, double y, double z, double steps) {
		double var9 = (this.targetX - this.x) / steps;
		double var11 = (this.targetY - this.y) / steps;
		double var13 = (this.targetZ - this.z) / steps;
		Box var15 = this.boundingBox.copy();

		for (int var16 = 1; (double)var16 < steps; var16++) {
			var15.translate(var9, var11, var13);
			if (!this.world.getEntityCollisions(this, var15).isEmpty()) {
				return false;
			}
		}

		return true;
	}

//	@Override
//	protected String getRandomSound() {
//		return "mob.ghast.moan";
//	}

	@Override
	protected String getHurtSound() {
		return "hauntedsands:entity.lostsoul.hurt";
	}

	@Override
	protected String getDeathSound() {
		return "hauntedsands:entity.lostsoul.death";
	}

	@Override
	protected void dropItems() {
		int var1 = this.getDroppedItemId();
		if (var1 > 0) {
			this.dropItem(var1, 1);
		}
	}

	@Override
	protected int getDroppedItemId() {
		return Block.LOCKED_CHEST.asItem().id;
	}

	@Override
	protected float getSoundVolume() {
		return 10.0F;
	}

	@Override
	public boolean canSpawn() {
		return this.random.nextInt(20) == 0 && super.canSpawn() && this.world.difficulty > 0;
	}

	@Override
	public int getLimitPerChunk() {
		return 1;
	}

	@Override
	public void onKilledBy(Entity adversary) {
		super.onKilledBy(adversary);

		for (int armorIndex = 0; armorIndex < this.armor.length; armorIndex++) {
			if (null != this.armor[armorIndex]) {
				this.dropItem(this.armor[armorIndex].copy(), 1);
			}
		}
	}

	@Override
	public boolean damage(Entity damageSource, int amount) {
		if (super.damage(damageSource, amount)) {
			if (this.passenger != damageSource && this.vehicle != damageSource) {
				if (damageSource != this) {
					this.target = damageSource;
				}

				return true;
			} else {
				return true;
			}
		} else {
			return false;
		}
	}

	protected void attack(Entity other, float distance) {
		if (this.attackCooldown <= 0 && distance < 2.0F && other.boundingBox.maxY > this.boundingBox.minY && other.boundingBox.minY < this.boundingBox.maxY) {
			this.attackCooldown = 20;
			int amount = this.attackDamage;

			if (other instanceof PlayerEntity) {
				if (this.world.difficulty == 0) {
					amount = 0;
				}

				if (this.world.difficulty == 1) {
					amount = amount / 3 + 1;
				}

				if (this.world.difficulty == 3) {
					amount = amount * 3 / 2;
				}
			}

			other.damage(this, amount);
		}
	}

	protected void resetAttack(Entity other, float distance) {
	}

	public int getTotalArmorDurability() {
		int var1 = 0;
		int var2 = 0;
		int var3 = 0;

		for (int var4 = 0; var4 < this.armor.length; var4++) {
			if (this.armor[var4] != null && this.armor[var4].getItem() instanceof ArmorItem) {
				int var5 = this.armor[var4].getMaxDamage();
				int var6 = this.armor[var4].getDamage2();
				int var7 = var5 - var6;
				var2 += var7;
				var3 += var5;
				int var8 = ((ArmorItem)this.armor[var4].getItem()).maxProtection;
				var1 += var8;
			}
		}

		return var3 == 0 ? 0 : (var1 - 1) * var2 / var3 + 1;
	}

	@Override
	protected void applyDamage(int amount) {
		int var2 = 25 - getTotalArmorDurability();
		int var3 = amount * var2 + this.damageSpill;
		amount = var3 / 25;
		this.damageSpill = var3 % 25;
		super.applyDamage(amount);
	}
}
