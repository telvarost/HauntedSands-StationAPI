package com.github.telvarost.hauntedsands.mixin;

import net.minecraft.block.Block;
import net.minecraft.entity.Entity;
import net.minecraft.entity.ItemEntity;
import net.minecraft.entity.passive.AnimalEntity;
import net.minecraft.entity.passive.PigEntity;
import net.minecraft.item.Item;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;

import java.util.List;

import static com.github.telvarost.hauntedsands.events.init.BlockListener.SUGAR_CUBE;

@Mixin(PigEntity.class)
public abstract class PigEntityMixin extends AnimalEntity {
    @Shadow public abstract boolean isSaddled();

    public PigEntityMixin(World world) {
        super(world);
    }

    @Override
    public void tickMovement() {
        if (this.target != null) {
            boolean targetFound = false;
            List nearbyEntities = this.world.getEntities(this, this.boundingBox.expand(0.5, 0.0, 0.5));

            for (Object entityObject : nearbyEntities) {
                if (entityObject instanceof ItemEntity itemEntity) {
                    if (itemEntity.id == this.target.id) {
                        if (null != itemEntity.stack && Item.SUGAR.id == itemEntity.stack.itemId) {
                            if (!this.world.isRemote) {
                                targetFound = true;
                                this.target.markDead();
                            }

                            if (this.health < 10) {
                                this.health++;
                            }
                        }
                    }
                }
            }

            if (targetFound) {
                this.target = null;
            }
        }

        super.tickMovement();
    }

    @Override
    protected Entity getTargetInRange() {
        ItemEntity var1 = hauntedSands_getClosestSugarItemEntity(16.0);
        return var1 != null && this.canSee(var1) ? var1 : null;
    }

    @Unique
    public ItemEntity hauntedSands_getClosestSugarItemEntity(double range) {
        double var9 = -1.0;
        ItemEntity closestSugarItemEntity = null;
        List nearbyEntities = this.world.getEntities(this, this.boundingBox.expand(range, range, range));

        for (Object entityObject : nearbyEntities) {
            if (entityObject instanceof ItemEntity itemEntity) {
                if (null != itemEntity.stack && Item.SUGAR.id == itemEntity.stack.itemId || Block.BROWN_MUSHROOM.asItem().id == itemEntity.stack.itemId) {
                    double var14 = itemEntity.getSquaredDistance(this.x, this.y, this.z);
                    if ((range < 0.0 || var14 < range * range) && (var9 == -1.0 || var14 < var9)) {
                        var9 = var14;
                        closestSugarItemEntity = itemEntity;
                    }
                }
            }
        }

        return closestSugarItemEntity;
    }

    @Override
    protected float getPathfindingFavor(int x, int y, int z) {
        if (this.isSaddled()) {
            return this.world.getBlockId(x, y - 1, z) == SUGAR_CUBE.id ? 10.0F : this.world.method_1782(x, y, z) - 0.5F;
        } else {
            return this.world.getBlockId(x, y - 1, z) == Block.GRASS_BLOCK.id ? 10.0F : this.world.method_1782(x, y, z) - 0.5F;
        }
    }
}
