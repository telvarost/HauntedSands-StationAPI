package com.github.telvarost.hauntedsands.block;

import com.github.telvarost.hauntedsands.HauntedSands;
import com.github.telvarost.hauntedsands.blockentity.GraveBlockEntity;
import com.github.telvarost.hauntedsands.events.init.BlockListener;
import com.github.telvarost.hauntedsands.gui.ContainerGrave;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.material.Material;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import net.modificationstation.stationapi.api.gui.screen.container.GuiHelper;
import net.modificationstation.stationapi.api.template.block.TemplateBlockWithEntity;
import net.modificationstation.stationapi.api.util.Identifier;

import java.util.Random;

public class Grave extends TemplateBlockWithEntity {

    public Grave(Identifier identifier, Material material) {
        super(identifier, material);
        this.setBoundingBox(0.0F, 0.0F, 0.0F, 1.0F, 0.5F, 1.0F);
        this.setOpacity(0);
    }

    @Override
    public int getTexture(int side, int meta) {
        return side <= 1 ? HauntedSands.GRAVE_TOP_TEXTURE : HauntedSands.GRAVE_SIDE_TEXTURE;
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
        super.onPlaced(world, x, y, z);

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

    @Override
    protected BlockEntity createBlockEntity() {
        return new GraveBlockEntity();
    }

    @Override
    public void onBreak(World world, int x, int y, int z) {
        GraveBlockEntity grave = (GraveBlockEntity)world.getBlockEntity(x, y, z);

        for(int i = 0; i < grave.size(); ++i) {
            if (grave.contents[i] != null) {
                dropStack(world, x, y, z, grave.contents[i]);
            }
        }
    }

    @Override
    public int getDroppedItemCount(Random random) {
        return 0;
    }

    @Override
    public boolean onUse(World world, int x, int y, int z, PlayerEntity player) {
        BlockEntity blockEntity = world.getBlockEntity(x, y, z);
        if (blockEntity instanceof GraveBlockEntity grave) {
            GuiHelper.openGUI(player, HauntedSands.HAUNTED_SANDS.id("grave"), grave, new ContainerGrave(player.inventory, grave));
        }
        return true;
    }
}
