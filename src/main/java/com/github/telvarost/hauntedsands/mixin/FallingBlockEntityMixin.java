package com.github.telvarost.hauntedsands.mixin;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import net.minecraft.block.material.Material;
import net.minecraft.entity.Entity;
import net.minecraft.entity.FallingBlockEntity;
import net.minecraft.entity.ItemEntity;
import net.minecraft.item.Item;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

import static com.github.telvarost.hauntedsands.events.init.BlockListener.*;

@Mixin(FallingBlockEntity.class)
public abstract class FallingBlockEntityMixin extends Entity {
    public FallingBlockEntityMixin(World world) {
        super(world);
    }

    @WrapOperation(
            method = "tick",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/world/World;setBlock(IIII)Z",
                    ordinal = 1
            )
    )
    public boolean hauntedSands_setBlock(World instance, int x, int y, int z, int blockId, Operation<Boolean> original) {
        if (REDSTONE_SAND.id == blockId) {
            return original.call(instance, x, y, z, LIT_REDSTONE_SAND.id);
        } else if (GUNPOWDER_CUBE.id == blockId) {
            if (Material.WATER == instance.getMaterial(x, y, z)) {
                return original.call(instance, x, y, z, blockId);
            } else {
                instance.createExplosion(this, this.x, this.y, this.z, 1.0F, true);
                return true;
            }
        } else if (SUGAR_CUBE.id == blockId) {
            Material blockMaterial = instance.getMaterial(x, y, z);
            if (Material.WATER == blockMaterial || Material.LAVA == blockMaterial) {
                int sugarAmount = instance.random.nextInt(3);
                if (0 < sugarAmount) {
                    this.dropItem(Item.SUGAR.id, sugarAmount);
                }
                return true;
            } else {
                return original.call(instance, x, y, z, blockId);
            }
        } else {
            return original.call(instance, x, y, z, blockId);
        }
    }

    @WrapOperation(
            method = "tick",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/entity/FallingBlockEntity;dropItem(II)Lnet/minecraft/entity/ItemEntity;"
            )
    )
    public ItemEntity hauntedSands_dropItem(FallingBlockEntity instance, int id, int amount, Operation<ItemEntity> original) {
        if (GUNPOWDER_CUBE.id == id) {
            this.world.createExplosion(this, this.x, this.y, this.z, 1.0F, true);
            return null;
        } else {
            return original.call(instance, id, amount);
        }
    }
}
