package com.github.telvarost.hauntedsands.events.init;

import com.github.telvarost.hauntedsands.HauntedSands;
import com.github.telvarost.hauntedsands.entity.LostSoulEntity;
import com.matthewperiut.elementalcreepers.datafixer.EntityIdentifierFix;
import com.matthewperiut.elementalcreepers.datafixer.RemainderSchema;
import com.mojang.datafixers.DataFixerBuilder;
import com.mojang.datafixers.schemas.Schema;
import net.mine_diver.unsafeevents.listener.EventListener;
import net.modificationstation.stationapi.api.datafixer.DataFixers;
import net.modificationstation.stationapi.api.event.datafixer.DataFixerRegisterEvent;
import net.modificationstation.stationapi.api.event.entity.EntityRegisterEvent;
import net.modificationstation.stationapi.api.event.registry.MobHandlerRegistryEvent;

public class EntityListener {
    // bump whenever a new data fix is added
    private static final int DATA_VERSION = 1;

    @EventListener
    public void registerEntities(EntityRegisterEvent event) {
        event.register(HauntedSands.HAUNTED_SANDS.id("LostSoul"), LostSoulEntity.class);
    }

    @EventListener
    public void registerMobHandlers(MobHandlerRegistryEvent event) {
        event.register(HauntedSands.HAUNTED_SANDS.id("LostSoul"), LostSoulEntity::new);
    }

    @EventListener
    public void registerDataFixer(DataFixerRegisterEvent event) {
        DataFixers.registerFixer(HauntedSands.HAUNTED_SANDS, executor -> {
            DataFixerBuilder builder = new DataFixerBuilder(DATA_VERSION);
            builder.addSchema(0, RemainderSchema::new);
            Schema v1 = builder.addSchema(1, Schema::new);
            builder.addFixer(new EntityIdentifierFix(v1));
            return builder.build().fixer();
        }, DATA_VERSION);
    }
}
