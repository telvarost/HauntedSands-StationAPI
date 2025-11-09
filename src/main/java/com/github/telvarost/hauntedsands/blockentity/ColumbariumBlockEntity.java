package com.github.telvarost.hauntedsands.blockentity;

import net.minecraft.block.entity.BlockEntity;
import net.minecraft.client.resource.language.I18n;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.Inventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtList;

public class ColumbariumBlockEntity extends BlockEntity implements Inventory {
    public ItemStack[] contents = new ItemStack[36];

    @Override
    public int size() {
        return contents.length;
    }

    @Override
    public ItemStack getStack(int slot) {
        return this.contents[slot];
    }

    @Override
    public ItemStack removeStack(int slot, int amount) {
        if (this.contents[slot] != null) {
            ItemStack var3;
            if (this.contents[slot].count <= amount) {
                var3 = this.contents[slot];
                this.contents[slot] = null;
                this.markDirty();
                return var3;
            } else {
                var3 = this.contents[slot].split(amount);
                if (this.contents[slot].count == 0) {
                    this.contents[slot] = null;
                }

                this.markDirty();
                return var3;
            }
        } else {
            return null;
        }
    }

    @Override
    public void setStack(int slot, ItemStack stack) {
        this.contents[slot] = stack;
        if (stack != null && stack.count > this.getMaxCountPerStack()) {
            stack.count = this.getMaxCountPerStack();
        }

        this.markDirty();
    }

    @Override
    public String getName() {
        return I18n.getTranslation("gui.hauntedsands.columbarium");
    }

    @Override
    public void readNbt(NbtCompound tag) {
        super.readNbt(tag);
        NbtList var2 = tag.getList("Items");
        this.contents = new ItemStack[this.size()];

        for(int var3 = 0; var3 < var2.size(); ++var3) {
            NbtCompound var4 = (NbtCompound)var2.get(var3);
            int var5 = var4.getByte("Slot") & 255;
            if (var5 >= 0 && var5 < this.contents.length) {
                this.contents[var5] = new ItemStack(var4);
            }
        }
    }

    @Override
    public void writeNbt(NbtCompound arg) {
        super.writeNbt(arg);
        NbtList var2 = new NbtList();

        for(int var3 = 0; var3 < this.contents.length; ++var3) {
            if (this.contents[var3] != null) {
                NbtCompound var4 = new NbtCompound();
                var4.putByte("Slot", (byte)var3);
                this.contents[var3].writeNbt(var4);
                var2.add(var4);
            }
        }

        arg.put("Items", var2);
    }

    @Override
    public int getMaxCountPerStack() {
        return 64;
    }

    @Override
    public boolean canPlayerUse(PlayerEntity arg) {
        if (this.world.getBlockEntity(this.x, this.y, this.z) != this) {
            return false;
        } else {
            return !(arg.getSquaredDistance((double)this.x + 0.5, (double)this.y + 0.5, (double)this.z + 0.5) > 64.0);
        }
    }
}
