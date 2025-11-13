package com.github.telvarost.hauntedsands.mixin;

import com.github.telvarost.hauntedsands.Config;
import com.github.telvarost.hauntedsands.entity.LostSoulEntity;
import net.minecraft.block.Block;
import net.minecraft.block.SoulSandBlock;
import net.minecraft.block.material.Material;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(SoulSandBlock.class)
public class SoulSandBlockMixin extends Block {

    public SoulSandBlockMixin(int id, int textureId) {
        super(id, textureId, Material.SAND);
    }

    @Override
    public void onBreak(World world, int x, int y, int z) {
        if (  (!world.isRemote)
           && (world.difficulty > 0)
           && (Config.config.chanceBreakingSoulSandSpawnsLostSoul > world.random.nextFloat())
        ) {
            LostSoulEntity playerLostSoul;
            playerLostSoul = new LostSoulEntity(world, x, y, z);
            playerLostSoul.setPositionAndAnglesKeepPrevAngles(x + 0.5, y + 0.5, z + 0.5, world.random.nextFloat() * 360.0F, 0.0F);
            world.spawnEntity(playerLostSoul);
        }
    }
}
