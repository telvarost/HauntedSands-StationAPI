package com.github.telvarost.hauntedsands.block;

import com.github.telvarost.hauntedsands.HauntedSands;
import net.minecraft.entity.Entity;
import net.minecraft.entity.passive.PigEntity;
import net.minecraft.world.World;
import net.modificationstation.stationapi.api.template.block.TemplateSandBlock;
import net.modificationstation.stationapi.api.util.Identifier;

public class SugarCube extends TemplateSandBlock {
    public SugarCube(Identifier identifier, int textureId) {
        super(identifier, textureId);
    }

    @Override
    public int getTexture(int side) {
        return HauntedSands.SUGAR_CUBE_TEXTURE;
    }

    @Override
    public void onSteppedOn(World world, int x, int y, int z, Entity entity) {
        if (entity instanceof PigEntity) {
            PigEntity pigEntity = (PigEntity) entity;
            if (pigEntity.isSaddled()) {
                world.setBlock(x, y, z, 0);
            }
        }
    }
}
