package com.github.telvarost.hauntedsands.block;

import com.github.telvarost.hauntedsands.HauntedSands;
import com.github.telvarost.hauntedsands.blockentity.ColumbariumBlockEntity;
import com.github.telvarost.hauntedsands.blockentity.GraveBlockEntity;
import com.github.telvarost.hauntedsands.events.init.BlockListener;
import com.github.telvarost.hauntedsands.gui.ContainerColumbarium;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.material.Material;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.world.World;
import net.modificationstation.stationapi.api.gui.screen.container.GuiHelper;
import net.modificationstation.stationapi.api.template.block.TemplateBlockWithEntity;
import net.modificationstation.stationapi.api.util.Identifier;

import java.util.Random;

public class Columbarium extends TemplateBlockWithEntity {

    public Columbarium(Identifier identifier, Material material) {
        super(identifier, material);
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
    public int getDroppedItemId(int blockMeta, Random random) {
        return BlockListener.GRAVE.asItem().id;
    }

    @Override
    protected BlockEntity createBlockEntity() {
        return new ColumbariumBlockEntity();
    }

    @Override
    public void onBreak(World world, int x, int y, int z) {
        ColumbariumBlockEntity columbarium = (ColumbariumBlockEntity)world.getBlockEntity(x, y, z);

        for(int i = 0; i < columbarium.size(); ++i) {
            if (columbarium.contents[i] != null) {
                dropStack(world, x, y, z, columbarium.contents[i]);
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
        if (blockEntity instanceof ColumbariumBlockEntity grave) {
            GuiHelper.openGUI(player, HauntedSands.HAUNTED_SANDS.id("columbarium"), grave, new ContainerColumbarium(player.inventory, grave));
        }
        return true;
    }
}
