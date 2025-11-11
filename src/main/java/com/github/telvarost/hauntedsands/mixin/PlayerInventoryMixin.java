package com.github.telvarost.hauntedsands.mixin;

import com.github.telvarost.hauntedsands.Config;
import com.github.telvarost.hauntedsands.blockentity.ColumbariumBlockEntity;
import com.github.telvarost.hauntedsands.blockentity.GraveBlockEntity;
import com.github.telvarost.hauntedsands.entity.LostSoulEntity;
import com.github.telvarost.hauntedsands.events.init.BlockListener;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import net.minecraft.block.entity.BlockEntity;
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
        if (!Config.config.playerDeathCreatesMainInventoryGrave) {
            return;
        }

        int xPlayerDeath = (int)Math.floor(this.player.x);
        int yPlayerDeath = (int)Math.floor(this.player.y);
        int zPlayerDeath = (int)Math.floor(this.player.z);

        for (int yPos = yPlayerDeath; yPos >= 0; yPos--) {
            if (this.player.world.getMaterial(xPlayerDeath, yPos, zPlayerDeath).isReplaceable()) {
                gravePosition = new BlockPos(xPlayerDeath, yPos, zPlayerDeath);
            } else {
                break;
            }
        }

        if (null == gravePosition) {
            for (int yPos = yPlayerDeath; yPos < this.player.world.getHeight(); yPos++) {
                if (this.player.world.getMaterial(xPlayerDeath, yPos, zPlayerDeath).isReplaceable()) {
                    gravePosition = new BlockPos(xPlayerDeath, yPos, zPlayerDeath);
                    break;
                }
            }
        }

        if (gravePosition != null && !this.player.world.isRemote && this.player.world.difficulty > 0) {
            LostSoulEntity playerLostSoul = new LostSoulEntity(this.player.world, gravePosition.x, gravePosition.y, gravePosition.z);
            playerLostSoul.setPositionAndAnglesKeepPrevAngles(this.player.x, this.player.y, this.player.z, this.player.world.random.nextFloat() * 360.0F, 0.0F);
            for (int armorIndex = 0; armorIndex < playerLostSoul.armor.length; armorIndex++) {
                if (null != this.player.inventory.armor[armorIndex]) {
                    playerLostSoul.armor[armorIndex] = this.player.inventory.armor[armorIndex].copy();
                }
            }
            this.player.world.spawnEntity(playerLostSoul);
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
    public void hauntedSands_dropMainInventoryItem(PlayerEntity instance, ItemStack stack, boolean throwRandomly, Operation<Void> original) {
        if (null != gravePosition) {
            mainInventoryItemList.add(stack);
        } else {
            original.call(instance, stack, throwRandomly);
        }
    }

    @WrapOperation(
            method = "dropInventory",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/entity/player/PlayerEntity;dropItem(Lnet/minecraft/item/ItemStack;Z)V",
                    ordinal = 1
            )
    )
    public void hauntedSands_dropArmorItem(PlayerEntity instance, ItemStack stack, boolean throwRandomly, Operation<Void> original) {
        if (gravePosition != null && !this.player.world.isRemote && this.player.world.difficulty > 0) {
            // Do nothing
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
                this.player.world.setBlock(gravePosition.x, gravePosition.y, gravePosition.z, BlockListener.GRAVE.id, 1);
                GraveBlockEntity graveBlockEntity = (GraveBlockEntity)this.player.world.getBlockEntity(gravePosition.x, gravePosition.y, gravePosition.z);

                if (null != graveBlockEntity) {
                    for (int blockInventoryIndex = 0; blockInventoryIndex < mainInventoryItemList.size(); blockInventoryIndex++) {
                        if (graveBlockEntity.size() <= blockInventoryIndex) {
                            break;
                        } else {
                            graveBlockEntity.contents[blockInventoryIndex] = mainInventoryItemList.get(blockInventoryIndex).copy();
                        }
                    }
                } else {
                    BlockEntity blockEntity = this.player.world.getBlockEntity(gravePosition.x, gravePosition.y - 1, gravePosition.z);

                    if (blockEntity instanceof ColumbariumBlockEntity columbariumBlockEntity) {
                        for (int blockInventoryIndex = 0; blockInventoryIndex < mainInventoryItemList.size(); blockInventoryIndex++) {
                            if ((columbariumBlockEntity.size() - 18) <= blockInventoryIndex) {
                                break;
                            } else {
                                columbariumBlockEntity.contents[blockInventoryIndex] = mainInventoryItemList.get(blockInventoryIndex).copy();
                            }
                        }
                    }
                }
            } else {
                this.player.world.setBlock(gravePosition.x, gravePosition.y, gravePosition.z, BlockListener.COLUMBARIUM.id, 1);
                ColumbariumBlockEntity columbariumBlockEntity = (ColumbariumBlockEntity)this.player.world.getBlockEntity(gravePosition.x, gravePosition.y, gravePosition.z);

                for (int blockInventoryIndex = 0; blockInventoryIndex < mainInventoryItemList.size(); blockInventoryIndex++) {
                    if (columbariumBlockEntity.size() <= blockInventoryIndex) {
                        this.player.dropItem(mainInventoryItemList.get(blockInventoryIndex), true);
                    } else {
                        columbariumBlockEntity.contents[blockInventoryIndex] = mainInventoryItemList.get(blockInventoryIndex).copy();
                    }
                }
            }
        }

        gravePosition = null;
        mainInventoryItemList = new ArrayList<>();
    }
}
