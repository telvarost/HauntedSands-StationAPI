package com.github.telvarost.hauntedsands.block;

import com.github.telvarost.hauntedsands.HauntedSands;
import com.github.telvarost.hauntedsands.events.init.BlockListener;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.block.material.Material;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import net.modificationstation.stationapi.api.template.block.TemplateBlock;
import net.modificationstation.stationapi.api.util.Identifier;

public class Grave extends TemplateBlock {

    public Grave(Identifier identifier, Material material) {
        super(identifier, material);
        this.setBoundingBox(0.0F, 0.0F, 0.0F, 1.0F, 0.5F, 1.0F);
        this.setOpacity(0);
    }

    @Override
    public int getTexture(int side, int meta) {
        return side <= 1 ? HauntedSands.REDSTONE_SAND_TEXTURE : HauntedSands.LIT_REDSTONE_SAND_TEXTURE;
    }

    @Override
    public int getTexture(int side) {
        return this.getTexture(side, 0);
    }

    @Override
    public boolean isOpaque() {
        return false;
    }

    @Override
    public boolean isFullCube() {
        return false;
    }

    @Environment(EnvType.CLIENT)
    @Override
    public boolean isSideVisible(BlockView blockView, int x, int y, int z, int side) {
        if (side == 1) {
            return true;
        } else if (!super.isSideVisible(blockView, x, y, z, side)) {
            return false;
        } else {
            return side == 0 ? true : blockView.getBlockId(x, y, z) != this.id;
        }
    }

    @Override
    public void onPlaced(World world, int x, int y, int z) {
        int var5 = world.getBlockId(x, y - 1, z);
        int var6 = world.getBlockMeta(x, y, z);
        int var7 = world.getBlockMeta(x, y - 1, z);
        if (var6 == var7) {
            if (var5 == BlockListener.GRAVE.id) {
                world.setBlock(x, y, z, 0);
                world.setBlock(x, y - 1, z, BlockListener.COLUMBARIUM.id, var6);
            }
        }
    }
}
