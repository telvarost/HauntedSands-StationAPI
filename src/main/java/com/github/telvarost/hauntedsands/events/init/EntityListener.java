package com.github.telvarost.hauntedsands.events.init;

import com.github.telvarost.hauntedsands.HauntedSands;
import com.github.telvarost.hauntedsands.entity.LostSoulEntity;
import com.github.telvarost.hauntedsands.entity.LostSoulEntityRenderer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.mine_diver.unsafeevents.listener.EventListener;
import net.modificationstation.stationapi.api.client.event.render.entity.EntityRendererRegisterEvent;
import net.modificationstation.stationapi.api.event.entity.EntityRegister;
import net.modificationstation.stationapi.api.event.registry.MobHandlerRegistryEvent;

public class EntityListener {

    @EventListener
    public void registerEntities(EntityRegister event) {
        event.register(LostSoulEntity.class, "lostsoul");
    }

    @EventListener
    public void registerMobHandlers(MobHandlerRegistryEvent event) {
        event.register(HauntedSands.HAUNTED_SANDS.id("lostsoul"), LostSoulEntity::new);
    }

    @Environment(EnvType.CLIENT)
    @EventListener
    public void registerEntityRenderer(EntityRendererRegisterEvent event) {
        event.renderers.put(LostSoulEntity.class, new LostSoulEntityRenderer());
    }
}
