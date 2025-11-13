package com.github.telvarost.hauntedsands.events.init;

import com.github.telvarost.hauntedsands.HauntedSands;
import com.github.telvarost.hauntedsands.entity.LostSoulEntity;
import net.mine_diver.unsafeevents.listener.EventListener;
import net.modificationstation.stationapi.api.event.entity.EntityRegister;
import net.modificationstation.stationapi.api.event.registry.MobHandlerRegistryEvent;

public class EntityListener {

    @EventListener
    public void registerEntities(EntityRegister event) {
        event.register(LostSoulEntity.class, "LostSoul");
    }

    @EventListener
    public void registerMobHandlers(MobHandlerRegistryEvent event) {
        event.register(HauntedSands.HAUNTED_SANDS.id("LostSoul"), LostSoulEntity::new);
    }
}
