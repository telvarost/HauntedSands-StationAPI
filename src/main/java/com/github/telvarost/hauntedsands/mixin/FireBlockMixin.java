package com.github.telvarost.hauntedsands.mixin;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import net.minecraft.block.Block;
import net.minecraft.block.FireBlock;
import net.minecraft.block.material.Material;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

import static com.github.telvarost.hauntedsands.events.init.BlockListener.GUNPOWDER_CUBE;

@Mixin(FireBlock.class)
public abstract class FireBlockMixin extends Block {
    public FireBlockMixin(int id, Material material) {
        super(id, material);
    }

    @WrapOperation(
            method = "trySpreadingFire",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/world/World;setBlock(IIII)Z"
            )
    )
    public boolean hauntedSands_trySpreadingFire(World instance, int x, int y, int z, int blockId, Operation<Boolean> original) {
        if (GUNPOWDER_CUBE.id != instance.getBlockId(x, y, z)) {
            return original.call(instance, x, y, z, blockId);
        } else {
            return original.call(instance, x, y, z, this.id);
        }
    }
}
