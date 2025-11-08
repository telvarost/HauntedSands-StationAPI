package com.github.telvarost.hauntedsands.block;

import com.github.telvarost.hauntedsands.HauntedSands;
import com.github.telvarost.hauntedsands.events.init.BlockListener;
import net.minecraft.block.material.Material;
import net.modificationstation.stationapi.api.template.block.TemplateBlock;
import net.modificationstation.stationapi.api.util.Identifier;

import java.util.Random;

public class Columbarium extends TemplateBlock {

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
    public int getDroppedItemCount(Random random) {
        return 2;
    }
}
