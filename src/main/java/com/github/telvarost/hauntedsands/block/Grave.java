package com.github.telvarost.hauntedsands.block;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import net.modificationstation.stationapi.api.template.block.TemplateBlock;
import net.modificationstation.stationapi.api.util.Identifier;

import java.util.Random;

public class Grave extends TemplateBlock {
    public static final String[] names = new String[]{"stone", "sand", "wood", "cobble"};
    private boolean doubleSlab;

    public Grave(Identifier identifier, Material material) {
        super(identifier, 6, material);
        this.doubleSlab = doubleSlab;
        if (!doubleSlab) {
            this.setBoundingBox(0.0F, 0.0F, 0.0F, 1.0F, 0.5F, 1.0F);
        }

        this.setOpacity(255);
    }

    @Override
    public int getTexture(int side, int meta) {
        if (meta == 0) {
            return side <= 1 ? 6 : 5;
        } else if (meta == 1) {
            if (side == 0) {
                return 208;
            } else {
                return side == 1 ? 176 : 192;
            }
        } else if (meta == 2) {
            return 4;
        } else {
            return meta == 3 ? 16 : 6;
        }
    }

    @Override
    public int getTexture(int side) {
        return this.getTexture(side, 0);
    }

    @Override
    public boolean isOpaque() {
        return this.doubleSlab;
    }

    @Override
    public void onPlaced(World world, int x, int y, int z) {
        if (this != Block.SLAB) {
            super.onPlaced(world, x, y, z);
        }

        int var5 = world.getBlockId(x, y - 1, z);
        int var6 = world.getBlockMeta(x, y, z);
        int var7 = world.getBlockMeta(x, y - 1, z);
        if (var6 == var7) {
            if (var5 == SLAB.id) {
                world.setBlock(x, y, z, 0);
                world.setBlock(x, y - 1, z, Block.DOUBLE_SLAB.id, var6);
            }
        }
    }

    @Override
    public int getDroppedItemId(int blockMeta, Random random) {
        return Block.SLAB.id;
    }

    @Override
    public int getDroppedItemCount(Random random) {
        return this.doubleSlab ? 2 : 1;
    }

    @Override
    protected int getDroppedItemMeta(int blockMeta) {
        return blockMeta;
    }

    @Override
    public boolean isFullCube() {
        return this.doubleSlab;
    }

    @Environment(EnvType.CLIENT)
    @Override
    public boolean isSideVisible(BlockView blockView, int x, int y, int z, int side) {
        if (this != Block.SLAB) {
            super.isSideVisible(blockView, x, y, z, side);
        }

        if (side == 1) {
            return true;
        } else if (!super.isSideVisible(blockView, x, y, z, side)) {
            return false;
        } else {
            return side == 0 ? true : blockView.getBlockId(x, y, z) != this.id;
        }
    }
}
