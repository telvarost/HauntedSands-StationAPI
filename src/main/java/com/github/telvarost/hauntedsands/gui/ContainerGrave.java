package com.github.telvarost.hauntedsands.gui;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.Inventory;
import net.minecraft.item.ItemStack;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.screen.slot.Slot;

public class ContainerGrave extends ScreenHandler {
    private Inventory inventory;
    private int rows;

    public ContainerGrave(Inventory playerInventory, Inventory inventory) {
        this.inventory = inventory;
        this.rows = inventory.size() / 9;
        int var3 = (this.rows - 4) * 18;
        int offset = 45;

        for (int var4 = 0; var4 < this.rows; var4++) {
            for (int var5 = 0; var5 < 9; var5++) {
                this.addSlot(new Slot(inventory, var5 + var4 * 9, 8 + var5 * 18, offset + 18 + var4 * 18));
            }
        }

        for (int var6 = 0; var6 < 3; var6++) {
            for (int var8 = 0; var8 < 9; var8++) {
                this.addSlot(new Slot(playerInventory, var8 + var6 * 9 + 9, 8 + var8 * 18, offset + 103 + var6 * 18 + var3));
            }
        }

        for (int var7 = 0; var7 < 9; var7++) {
            this.addSlot(new Slot(playerInventory, var7, 8 + var7 * 18, offset + 161 + var3));
        }
    }

    @Override
    public boolean canUse(PlayerEntity player) {
        return this.inventory.canPlayerUse(player);
    }

    @Override
    public ItemStack quickMove(int slot) {
        ItemStack var2 = null;
        Slot var3 = (Slot)this.slots.get(slot);
        if (var3 != null && var3.hasStack()) {
            ItemStack var4 = var3.getStack();
            var2 = var4.copy();
            if (slot < this.rows * 9) {
                this.insertItem(var4, this.rows * 9, this.slots.size(), true);
            } else {
                this.insertItem(var4, 0, this.rows * 9, false);
            }

            if (var4.count == 0) {
                var3.setStack(null);
            } else {
                var3.markDirty();
            }
        }

        return var2;
    }
}
