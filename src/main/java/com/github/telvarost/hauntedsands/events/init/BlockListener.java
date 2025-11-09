package com.github.telvarost.hauntedsands.events.init;

import com.github.telvarost.hauntedsands.HauntedSands;
import com.github.telvarost.hauntedsands.block.*;
import com.github.telvarost.hauntedsands.blockentity.ColumbariumBlockEntity;
import com.github.telvarost.hauntedsands.blockentity.GraveBlockEntity;
import net.mine_diver.unsafeevents.listener.EventListener;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.modificationstation.stationapi.api.event.block.FireBurnableRegisterEvent;
import net.modificationstation.stationapi.api.event.block.entity.BlockEntityRegisterEvent;
import net.modificationstation.stationapi.api.event.registry.BlockRegistryEvent;
import net.modificationstation.stationapi.api.util.Identifier;

import static net.minecraft.block.Block.*;

public class BlockListener {
    public static Block[] blocks;

    public static Block GLOWSTONE_SAND;
    public static Block REDSTONE_SAND;
    public static Block LIT_REDSTONE_SAND;
    public static Block SUGAR_CUBE;
    public static Block GUNPOWDER_CUBE;
    public static Block GRAVE;
    public static Block COLUMBARIUM;

    @EventListener
    public void registerFire(FireBurnableRegisterEvent event) {
        event.addBurnable(GUNPOWDER_CUBE.id, 1, 100);
    }

    @EventListener
    public void registerBlockEntities(BlockEntityRegisterEvent event) {
        event.register(ColumbariumBlockEntity.class, Identifier.of(HauntedSands.HAUNTED_SANDS, "columbarium").toString());
        event.register(GraveBlockEntity.class, Identifier.of(HauntedSands.HAUNTED_SANDS, "grave").toString());
    }

    @EventListener
    public void registerBlocks(BlockRegistryEvent event) {
        GLOWSTONE_SAND    = new GlowstoneSand  (Identifier.of(HauntedSands.HAUNTED_SANDS, "glowstone_sand")   , HauntedSands.GLOWSTONE_SAND_TEXTURE)   .setHardness(0.6F).setSoundGroup(GRAVEL_SOUND_GROUP).setLuminance(1.0F).setTranslationKey(HauntedSands.HAUNTED_SANDS, "glowstone_sand");
        REDSTONE_SAND     = new RedstoneSand   (Identifier.of(HauntedSands.HAUNTED_SANDS, "redstone_sand")    , HauntedSands.REDSTONE_SAND_TEXTURE)    .setHardness(0.7F).setSoundGroup(GRAVEL_SOUND_GROUP).setTranslationKey(HauntedSands.HAUNTED_SANDS, "redstone_sand");
        LIT_REDSTONE_SAND = new LitRedstoneSand(Identifier.of(HauntedSands.HAUNTED_SANDS, "lit_redstone_sand"), HauntedSands.LIT_REDSTONE_SAND_TEXTURE).setHardness(0.7F).setSoundGroup(GRAVEL_SOUND_GROUP).setLuminance(0.625F).setTranslationKey(HauntedSands.HAUNTED_SANDS, "redstone_sand");
        SUGAR_CUBE        = new SugarCube      (Identifier.of(HauntedSands.HAUNTED_SANDS, "sugar_cube")       , HauntedSands.SUGAR_CUBE_TEXTURE)       .setHardness(0.4F).setSoundGroup(SAND_SOUND_GROUP).setTranslationKey(HauntedSands.HAUNTED_SANDS, "sugar_cube");
        GUNPOWDER_CUBE    = new GunpowderCube  (Identifier.of(HauntedSands.HAUNTED_SANDS, "gunpowder_cube")   , HauntedSands.GUNPOWDER_CUBE_TEXTURE)   .setHardness(0.5F).setSoundGroup(SAND_SOUND_GROUP).setTranslationKey(HauntedSands.HAUNTED_SANDS, "gunpowder_cube");
        GRAVE             = new Grave          (Identifier.of(HauntedSands.HAUNTED_SANDS, "grave")            , Material.STONE)                        .setHardness(1.0F).setResistance(20.0F).setSoundGroup(STONE_SOUND_GROUP).setTranslationKey(HauntedSands.HAUNTED_SANDS, "grave").ignoreMetaUpdates();
        COLUMBARIUM       = new Columbarium    (Identifier.of(HauntedSands.HAUNTED_SANDS, "columbarium")      , Material.STONE)                        .setHardness(1.0F).setResistance(20.0F).setSoundGroup(STONE_SOUND_GROUP).setTranslationKey(HauntedSands.HAUNTED_SANDS, "columbarium");

        blocks = new Block[]
        { GLOWSTONE_SAND
        , REDSTONE_SAND
        , SUGAR_CUBE
        , GUNPOWDER_CUBE
        , GRAVE
        , COLUMBARIUM
        };
    }
}
