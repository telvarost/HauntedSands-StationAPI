package com.github.telvarost.hauntedsands.block;

import com.github.telvarost.hauntedsands.HauntedSands;
import net.minecraft.world.World;
import net.modificationstation.stationapi.api.template.block.TemplateSandBlock;
import net.modificationstation.stationapi.api.util.Identifier;

public class RedstoneSand extends TemplateSandBlock {
    public RedstoneSand(Identifier identifier, int textureId) {
        super(identifier, textureId);
    }

    @Override
    public int getTexture(int side) {
        return HauntedSands.REDSTONE_SAND_TEXTURE;
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
}
