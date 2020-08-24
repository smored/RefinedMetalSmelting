package com.smore_d.rms.client.gui;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.systems.RenderSystem;
import com.smore_d.rms.RefinedMetalSmelting;
import com.smore_d.rms.containers.FurnaceContainerBase;
import net.minecraft.client.gui.screen.inventory.ContainerScreen;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;

public abstract class FurnaceScreenBase <T extends FurnaceContainerBase> extends ContainerScreen<T> {
    public ResourceLocation GUI = new ResourceLocation(RefinedMetalSmelting.MOD_ID + ":" + "textures/gui/mk2_furnace_gui.png");
    PlayerInventory playerInv;
    ITextComponent name;

    public FurnaceScreenBase(T t, PlayerInventory inv, ITextComponent name) {
        super(t, inv, name);
        playerInv = inv;
        this.name = name;
    }

    @Override
    public void render(MatrixStack matrixStack, int mouseX, int mouseY, float partialTicks) {
        this.renderBackground(matrixStack);
        super.render(matrixStack, mouseX, mouseY, partialTicks);
        this.func_230459_a_(matrixStack, mouseX, mouseY);
    }

    @Override
    protected void drawGuiContainerForegroundLayer(MatrixStack matrixStack, int x, int y) {
        this.font.func_238422_b_(matrixStack, this.playerInv.getDisplayName(), 7, this.ySize - 93, 4210752);
        this.font.func_238422_b_(matrixStack, name, 7 + this.xSize / 2 - this.font.func_238414_a_(name) / 2, 6, 4210752);
    }

    @Override
    protected void drawGuiContainerBackgroundLayer(MatrixStack matrixStack, float partialTicks, int x, int y) {
        RenderSystem.color4f(1.0F, 1.0F, 1.0F, 1.0F);
        this.minecraft.getTextureManager().bindTexture(GUI);
        int relX = (this.width - this.xSize) / 2;
        int relY = (this.height- this.ySize) / 2;
        this.blit(matrixStack, relX, relY, 0, 0, this.xSize, this.ySize);
        int i;
        if ((container).isBurning()) {
            i = (container).getBurnScaled(13);
            this.blit(matrixStack, guiLeft + 56, guiTop + 36 + 12 - i, 176, 12 - i, 14, i + 1);
        }

        i = ((FurnaceContainerBase)this.container).getCookScaled(24);
        this.blit(matrixStack, guiLeft + 79, guiTop + 34, 176, 14, i + 1, 16);
    }
}
