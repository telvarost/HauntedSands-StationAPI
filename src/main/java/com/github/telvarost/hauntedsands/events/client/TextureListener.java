package com.github.telvarost.hauntedsands.events.client;

import com.github.telvarost.hauntedsands.HauntedSands;
import net.mine_diver.unsafeevents.listener.EventListener;
import net.modificationstation.stationapi.api.client.event.texture.TextureRegisterEvent;
import net.modificationstation.stationapi.api.client.texture.atlas.Atlases;
import net.modificationstation.stationapi.api.util.Identifier;

public class TextureListener {

    @EventListener
    public void registerTextures(TextureRegisterEvent event) {
        HauntedSands.GLOWSTONE_SAND_TEXTURE    = Atlases.getTerrain().addTexture(Identifier.of(HauntedSands.HAUNTED_SANDS, "block/glowstone_sand")   ).index;
        HauntedSands.REDSTONE_SAND_TEXTURE     = Atlases.getTerrain().addTexture(Identifier.of(HauntedSands.HAUNTED_SANDS, "block/redstone_sand")    ).index;
        HauntedSands.LIT_REDSTONE_SAND_TEXTURE = Atlases.getTerrain().addTexture(Identifier.of(HauntedSands.HAUNTED_SANDS, "block/lit_redstone_sand")).index;
        HauntedSands.SUGAR_CUBE_TEXTURE        = Atlases.getTerrain().addTexture(Identifier.of(HauntedSands.HAUNTED_SANDS, "block/sugar_cube")       ).index;
        HauntedSands.GUNPOWDER_CUBE_TEXTURE    = Atlases.getTerrain().addTexture(Identifier.of(HauntedSands.HAUNTED_SANDS, "block/gunpowder_cube")   ).index;
    }
}
