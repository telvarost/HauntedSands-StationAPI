package com.github.telvarost.hauntedsands.block;

import com.github.telvarost.hauntedsands.HauntedSands;
import net.modificationstation.stationapi.api.template.block.TemplateSandBlock;
import net.modificationstation.stationapi.api.util.Identifier;

public class GunpowderCube extends TemplateSandBlock {
    public GunpowderCube(Identifier identifier, int textureId) {
        super(identifier, textureId);
    }

    @Override
    public int getTexture(int side) {
        return HauntedSands.GUNPOWDER_CUBE_TEXTURE;
    }
}
