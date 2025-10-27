package com.github.telvarost.hauntedsands.mixin;

import net.minecraft.block.DeadBushBlock;
import net.minecraft.block.PlantBlock;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import static com.github.telvarost.hauntedsands.events.init.BlockListener.*;

@Mixin(DeadBushBlock.class)
public class DeadBushBlockMixin extends PlantBlock {
    public DeadBushBlockMixin(int i, int j) {
        super(i, j);
    }

    @Inject(
            method = "canPlantOnTop",
            at = @At("RETURN"),
            cancellable = true
    )
    protected void hauntedSands_canPlantOnTop(int id, CallbackInfoReturnable<Boolean> cir) {
        if (  GLOWSTONE_SAND.id == id
           || REDSTONE_SAND.id == id
           || SUGAR_CUBE.id == id
           || GUNPOWDER_CUBE.id == id
        ) {
            cir.setReturnValue(true);
        }
    }
}
