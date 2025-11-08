package com.github.telvarost.hauntedsands.gui;

import com.github.telvarost.hauntedsands.blockentity.ColumbariumBlockEntity;
import net.minecraft.client.gui.screen.ingame.HandledScreen;
import net.minecraft.entity.player.PlayerInventory;
import org.lwjgl.opengl.GL11;

public class GuiColumbarium extends HandledScreen {
    private final String name;

    public GuiColumbarium(PlayerInventory inv, ColumbariumBlockEntity columbarium) {
        super(new ContainerColumbarium(inv, columbarium));
        name = columbarium.getName();
        this.backgroundWidth = 176;
        this.backgroundHeight = 222;
    }

    @Override
    protected void drawForeground() {
        textRenderer.draw(name, 8, this.backgroundHeight - 207, 4210752);
        textRenderer.draw("Inventory", 8, this.backgroundHeight - 121, 4210752);
    }

    @Override
    protected void drawBackground(float f) {
        int var2 = this.minecraft.textureManager.getTextureId("/assets/hauntedsands/stationapi/textures/gui/columbariumgui.png");
        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
        this.minecraft.textureManager.bindTexture(var2);
        int var3 = (this.width - this.backgroundWidth) / 2;
        int var4 = (this.height - this.backgroundHeight) / 2;
        this.drawTexture(var3, var4 + 8, 0, 0, this.backgroundWidth, 222);
    }
}
