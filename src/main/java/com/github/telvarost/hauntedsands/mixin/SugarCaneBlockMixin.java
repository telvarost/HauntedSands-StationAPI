package com.github.telvarost.hauntedsands.mixin;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import net.minecraft.block.Block;
import net.minecraft.block.SugarCaneBlock;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

import static com.github.telvarost.hauntedsands.events.init.BlockListener.*;

@Mixin(SugarCaneBlock.class)
public class SugarCaneBlockMixin {

    @WrapOperation(
            method = "canPlaceAt",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/world/World;getBlockId(III)I"
            )
    )
    public int hauntedSands_canPlaceAt(World instance, int x, int y, int z, Operation<Integer> original) {
        int blockId = original.call(instance, x, y, z);

        if (  GLOWSTONE_SAND.id == blockId
           || SUGAR_CUBE.id == blockId
        ) {
            blockId = Block.DIRT.id;
        }

        return blockId;
    }
}
