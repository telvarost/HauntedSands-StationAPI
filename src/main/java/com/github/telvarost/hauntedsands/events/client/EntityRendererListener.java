package com.github.telvarost.hauntedsands.events.client;

import com.github.telvarost.hauntedsands.entity.LostSoulEntity;
import com.github.telvarost.hauntedsands.entity.LostSoulEntityRenderer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.mine_diver.unsafeevents.listener.EventListener;
import net.modificationstation.stationapi.api.client.event.render.entity.EntityRendererRegisterEvent;

public class EntityRendererListener {

    @Environment(EnvType.CLIENT)
    @EventListener
    public void registerEntityRenderer(EntityRendererRegisterEvent event) {
        event.renderers.put(LostSoulEntity.class, new LostSoulEntityRenderer());
    }
}
