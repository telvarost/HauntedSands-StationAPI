package com.github.telvarost.hauntedsands.events.client;

import com.github.telvarost.hauntedsands.HauntedSands;
import com.github.telvarost.hauntedsands.blockentity.ColumbariumBlockEntity;
import com.github.telvarost.hauntedsands.blockentity.GraveBlockEntity;
import com.github.telvarost.hauntedsands.gui.GuiColumbarium;
import com.github.telvarost.hauntedsands.gui.GuiGrave;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.mine_diver.unsafeevents.listener.EventListener;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.Inventory;
import net.modificationstation.stationapi.api.client.gui.screen.GuiHandler;
import net.modificationstation.stationapi.api.client.registry.GuiHandlerRegistry;
import net.modificationstation.stationapi.api.event.registry.GuiHandlerRegistryEvent;
import net.modificationstation.stationapi.api.registry.Registry;

public class GuiListener {

    @Environment(EnvType.CLIENT)
    @EventListener
    public void registerGuiHandlers(GuiHandlerRegistryEvent event) {
        GuiHandlerRegistry registry = event.registry;
        Registry.register(event.registry, HauntedSands.HAUNTED_SANDS.id("columbarium"), new GuiHandler((GuiHandler.ScreenFactoryNoMessage) this::openColumbarium, ColumbariumBlockEntity::new));
        Registry.register(event.registry, HauntedSands.HAUNTED_SANDS.id("grave"), new GuiHandler((GuiHandler.ScreenFactoryNoMessage) this::openGrave, GraveBlockEntity::new));
    }

    @Environment(EnvType.CLIENT)
    public Screen openColumbarium(PlayerEntity player, Inventory inventoryBase) {
        return new GuiColumbarium(player.inventory, (ColumbariumBlockEntity) inventoryBase);
    }

    @Environment(EnvType.CLIENT)
    public Screen openGrave(PlayerEntity player, Inventory inventoryBase) {
        return new GuiGrave(player.inventory, (GraveBlockEntity) inventoryBase);
    }
}
