package com.github.telvarost.hauntedsands.mixin;

import net.minecraft.block.Block;
import net.minecraft.block.PlantBlock;
import net.minecraft.block.material.Material;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import static com.github.telvarost.hauntedsands.events.init.BlockListener.*;
import static com.github.telvarost.hauntedsands.events.init.BlockListener.GUNPOWDER_CUBE;

@Mixin(PlantBlock.class)
public class PlantBlockMixin extends Block {
    public PlantBlockMixin(int id, int textureId) {
        super(id, Material.PLANT);
    }

    @Inject(
            method = "canPlantOnTop",
            at = @At("RETURN"),
            cancellable = true
    )
    protected void hauntedSands_canPlantOnTop(int id, CallbackInfoReturnable<Boolean> cir) {
        if (  REDSTONE_SAND.id == id
           || SUGAR_CUBE.id == id
        ) {
            cir.setReturnValue(true);
        }
    }
}
