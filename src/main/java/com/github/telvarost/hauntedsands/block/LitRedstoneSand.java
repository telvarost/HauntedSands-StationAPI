package com.github.telvarost.hauntedsands.block;

import com.github.telvarost.hauntedsands.HauntedSands;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import net.modificationstation.stationapi.api.template.block.TemplateSandBlock;
import net.modificationstation.stationapi.api.util.Identifier;

import java.util.Random;

import static com.github.telvarost.hauntedsands.events.init.BlockListener.REDSTONE_SAND;

public class LitRedstoneSand extends TemplateSandBlock {
    public LitRedstoneSand(Identifier identifier, int textureId) {
        super(identifier, textureId);
        this.setTickRandomly(true);
    }

    @Override
    public int getTexture(int side) {
        return HauntedSands.LIT_REDSTONE_SAND_TEXTURE;
    }

    @Override
    public void onPlaced(World world, int x, int y, int z) {
        if (world.getBlockMeta(x, y, z) == 0) {
            super.onPlaced(world, x, y, z);
        }

        world.notifyNeighbors(x, y - 1, z, this.id);
        world.notifyNeighbors(x, y + 1, z, this.id);
        world.notifyNeighbors(x - 1, y, z, this.id);
        world.notifyNeighbors(x + 1, y, z, this.id);
        world.notifyNeighbors(x, y, z - 1, this.id);
        world.notifyNeighbors(x, y, z + 1, this.id);
    }

    @Override
    public void onTick(World world, int x, int y, int z, Random random) {
        world.setBlock(x, y, z, REDSTONE_SAND.id);
    }

    @Override
    public boolean isEmittingRedstonePowerInDirection(BlockView blockView, int x, int y, int z, int direction) {
        return true;
    }

    @Override
    public boolean canTransferPowerInDirection(World world, int x, int y, int z, int direction) {
        return true;
    }

    @Override
    public boolean canEmitRedstonePower() {
        return true;
    }
}
