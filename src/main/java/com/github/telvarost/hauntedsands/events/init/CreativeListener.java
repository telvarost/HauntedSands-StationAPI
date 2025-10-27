package com.github.telvarost.hauntedsands.events.init;

import com.github.telvarost.hauntedsands.HauntedSands;
import net.mine_diver.unsafeevents.listener.EventListener;
import net.minecraft.block.Block;
import net.minecraft.item.ItemStack;
import paulevs.bhcreative.api.CreativeTab;
import paulevs.bhcreative.api.SimpleTab;
import paulevs.bhcreative.registry.TabRegistryEvent;

public class CreativeListener {
    public static CreativeTab tabHauntedSands;

    @EventListener
    public void onTabInit(TabRegistryEvent event){
        tabHauntedSands = new SimpleTab(HauntedSands.HAUNTED_SANDS.id("glowstone_sand"), BlockListener.GLOWSTONE_SAND.asItem());
        event.register(tabHauntedSands);

        for (Block block : BlockListener.blocks){
            tabHauntedSands.addItem(new ItemStack(block.asItem(), 1));
        }
    }
}
