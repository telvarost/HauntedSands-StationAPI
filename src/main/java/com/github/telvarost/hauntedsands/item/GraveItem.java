package com.github.telvarost.hauntedsands.item;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.block.Block;
import net.minecraft.block.SlabBlock;
import net.minecraft.item.ItemStack;
import net.modificationstation.stationapi.api.template.item.TemplateBlockItem;

public class GraveItem extends TemplateBlockItem {

    public GraveItem(int i) {
        super(i);
        this.setMaxDamage(0);
        this.setHasSubtypes(true);
    }

    @Environment(EnvType.CLIENT)
    @Override
    public int getTextureId(int damage) {
        return Block.SLAB.getTexture(2, damage);
    }

    @Override
    public int getPlacementMetadata(int meta) {
        return meta;
    }

    @Environment(EnvType.CLIENT)
    @Override
    public String getTranslationKey(ItemStack stack) {
        return super.getTranslationKey() + "." + SlabBlock.names[stack.getDamage()];
    }
}
