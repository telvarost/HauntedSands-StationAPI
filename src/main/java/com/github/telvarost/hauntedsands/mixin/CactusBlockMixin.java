package com.github.telvarost.hauntedsands.mixin;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import net.minecraft.block.Block;
import net.minecraft.block.CactusBlock;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

import static com.github.telvarost.hauntedsands.events.init.BlockListener.*;

@Mixin(CactusBlock.class)
public class CactusBlockMixin {

    @WrapOperation(
            method = "canGrow",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/world/World;getBlockId(III)I"
            )
    )
    public int hauntedSands_canGrow(World instance, int x, int y, int z, Operation<Integer> original) {
        int blockId = original.call(instance, x, y, z);

        if (  GLOWSTONE_SAND.id == blockId
           || GUNPOWDER_CUBE.id == blockId
        ) {
            blockId = Block.SAND.id;
        }

        return blockId;
    }
}
