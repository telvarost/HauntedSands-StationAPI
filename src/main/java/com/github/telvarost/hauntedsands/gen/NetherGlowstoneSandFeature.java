package com.github.telvarost.hauntedsands.gen;

import java.util.Random;
import net.minecraft.block.Block;
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.Feature;

public class NetherGlowstoneSandFeature extends Feature {
    private int oreBlockId;
    private int oreCount;

    public NetherGlowstoneSandFeature(int oreBlockId, int oreCount) {
        this.oreBlockId = oreBlockId;
        this.oreCount = oreCount;
    }

    public boolean generate(World world, Random random, int x, int y, int z) {
        for(int var19 = 0; var19 < this.oreCount; ++var19) {
            drawDiamond(world, x, y, z, var19, this.oreBlockId);
        }

        return true;
    }

    private void drawDiamond(World world, int x, int y, int z, int size, int blockId) {
        if (0 == size) {
            if (world.getBlockId(x, y, z) == Block.NETHERRACK.id && world.getBlockId(x, y - 1, z) != 0) {
                world.setBlock(x, y, z, blockId);
            }
        } else {
            for (int drawIndex = 0; drawIndex < size; drawIndex++) {
                if (  world.getBlockId((x + drawIndex) - size + 1, y, (z + drawIndex) + 1) == Block.NETHERRACK.id
                   && world.getBlockId((x + drawIndex) - size + 1, y - 1, (z + drawIndex) + 1) != 0
                ) {
                    world.setBlock((x + drawIndex) - size + 1, y, (z + drawIndex) + 1, blockId);
                }
                //     n
                //   n   _
                // _       _
                //   _   _
                //     _

                if (  world.getBlockId((x + drawIndex), y, (z + drawIndex) - size) == Block.NETHERRACK.id
                   && world.getBlockId((x + drawIndex), y - 1, (z + drawIndex) - size) != 0
                ) {
                    world.setBlock((x + drawIndex), y, (z + drawIndex) - size, blockId);
                }
                //     _
                //   _   _
                // _       _
                //   _   n
                //     n

                if (  world.getBlockId((x + drawIndex) - size, y, (z - drawIndex)) == Block.NETHERRACK.id
                   && world.getBlockId((x + drawIndex) - size, y - 1, (z - drawIndex)) != 0
                ) {
                    world.setBlock((x + drawIndex) - size, y, (z - drawIndex), blockId);
                }
                //     _
                //   _   _
                // n       _
                //   n   _
                //     _

                if (  world.getBlockId((x + drawIndex) + 1, y, (z - drawIndex) + size - 1) == Block.NETHERRACK.id
                   && world.getBlockId((x + drawIndex) + 1, y - 1, (z - drawIndex) + size - 1) != 0
                ) {
                    world.setBlock((x + drawIndex) + 1, y, (z - drawIndex) + size - 1, blockId);
                }
                //     _
                //   _   n
                // _       n
                //   _   _
                //     _
            }
        }
    }
}
