package com.github.telvarost.hauntedsands.mixin;

import com.github.telvarost.hauntedsands.blockentity.ColumbariumBlockEntity;
import com.github.telvarost.hauntedsands.blockentity.GraveBlockEntity;
import com.github.telvarost.hauntedsands.events.init.BlockListener;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.ArrayList;

@Mixin(PlayerInventory.class)
public class PlayerInventoryMixin {

    @Shadow public PlayerEntity player;

    @Unique BlockPos gravePosition = null;
    @Unique ArrayList<ItemStack> mainInventoryItemList = new ArrayList<>();

    @Inject(
            method = "dropInventory",
            at = @At("HEAD"),
            cancellable = true
    )
    public void hauntedSands_dropInventoryHead(CallbackInfo ci) {
        int xPlayerDeath = (int)Math.floor(this.player.x);
        int yPlayerDeath = (int)Math.floor(this.player.y);
        int zPlayerDeath = (int)Math.floor(this.player.z);

        for (int yPos = yPlayerDeath; yPos >= 0; yPos--) {
            if (this.player.world.getMaterial(xPlayerDeath, yPos, zPlayerDeath).isReplaceable()) {
                gravePosition = new BlockPos(xPlayerDeath, yPos, zPlayerDeath);
                System.out.println(gravePosition.toString());
            } else {
                break;
            }
        }

        if (null == gravePosition) {
            for (int yPos = yPlayerDeath; yPos < this.player.world.getHeight(); yPos++) {
                if (this.player.world.getMaterial(xPlayerDeath, yPos, zPlayerDeath).isReplaceable()) {
                    gravePosition = new BlockPos(xPlayerDeath, yPos, zPlayerDeath);
                    System.out.println(gravePosition.toString());
                    break;
                }
            }
        }
    }

    @WrapOperation(
            method = "dropInventory",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/entity/player/PlayerEntity;dropItem(Lnet/minecraft/item/ItemStack;Z)V",
                    ordinal = 0
            )
    )
    public void hauntedSands_dropInventoryDropItem(PlayerEntity instance, ItemStack stack, boolean throwRandomly, Operation<Void> original) {
        if (null != gravePosition) {
            mainInventoryItemList.add(stack);
        } else {
            original.call(instance, stack, throwRandomly);
        }
    }

    @Inject(
            method = "dropInventory",
            at = @At("TAIL"),
            cancellable = true
    )
    public void hauntedSands_dropInventoryTail(CallbackInfo ci) {
        if (null != gravePosition && !mainInventoryItemList.isEmpty()) {
            if (18 >= mainInventoryItemList.size()) {
                this.player.world.setBlock(gravePosition.x, gravePosition.y, gravePosition.z, BlockListener.GRAVE.id);
                GraveBlockEntity blockEntity = (GraveBlockEntity)this.player.world.getBlockEntity(gravePosition.x, gravePosition.y, gravePosition.z);

                for (int blockInventoryIndex = 0; blockInventoryIndex < mainInventoryItemList.size(); blockInventoryIndex++) {
                    if (blockEntity.size() <= blockInventoryIndex) {
                        break;
                    } else {
                        blockEntity.contents[blockInventoryIndex] = mainInventoryItemList.get(blockInventoryIndex).copy();
                    }
                }
            } else {
                this.player.world.setBlock(gravePosition.x, gravePosition.y, gravePosition.z, BlockListener.COLUMBARIUM.id);
                ColumbariumBlockEntity blockEntity = (ColumbariumBlockEntity)this.player.world.getBlockEntity(gravePosition.x, gravePosition.y, gravePosition.z);

                for (int blockInventoryIndex = 0; blockInventoryIndex < mainInventoryItemList.size(); blockInventoryIndex++) {
                    if (blockEntity.size() <= blockInventoryIndex) {
                        this.player.dropItem(mainInventoryItemList.get(blockInventoryIndex), true);
                    } else {
                        blockEntity.contents[blockInventoryIndex] = mainInventoryItemList.get(blockInventoryIndex).copy();
                    }
                }
            }
        }

        gravePosition = null;
        mainInventoryItemList = new ArrayList<>();
    }
}
