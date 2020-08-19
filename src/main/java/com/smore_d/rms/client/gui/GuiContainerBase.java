package com.smore_d.rms.client.gui;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.systems.RenderSystem;
import com.smore_d.rms.client.util.GuiUtils;
import com.smore_d.rms.client.util.PointXY;
import com.smore_d.rms.containers.ContainerBase;
import com.smore_d.rms.network.NetworkHandler;
import com.smore_d.rms.network.packets.PacketGuiButton;
import com.smore_d.rms.tile.entity.*;
import net.minecraft.client.MainWindow;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.screen.inventory.ContainerScreen;
import net.minecraft.client.renderer.texture.ITickable;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;

import java.util.*;


public abstract class GuiContainerBase<C extends ContainerBase<T>, T extends TileEntityBase> extends ContainerScreen<C> {
    public final T te;
    boolean firstUpdate = true;
    private int sendDelay = -1;
    boolean redstoneAllows;

    public GuiContainerBase(C container, PlayerInventory inv, ITextComponent displayString) {
        super(container, inv, displayString);
        this.te = container.te;
    }

    @Override
    public void init() {
        super.init();
    }

    protected int getBackgroundTint() { return 0xFFFFFF; }

    protected boolean shouldDrawBackground() {
        return true;
    }

    @Override
    protected void drawGuiContainerBackgroundLayer(MatrixStack matrixStack, float partialTicks, int i, int j) {
        if (shouldDrawBackground()) {
            GuiUtils.glColorHex(0xFF000000 | getBackgroundTint());
            bindGuiTexture();
            int xStart = (width - xSize) / 2;
            int yStart = (height - ySize) / 2;
            blit(matrixStack, xStart, yStart, 0, 0, xSize, ySize);
        }
    }

    @Override
    protected void drawGuiContainerForegroundLayer(MatrixStack matrixStack, int x, int y) {
        if (getInvNameOffset() != null) {
            font.func_238422_b_(matrixStack, title, xSize / 2f - font.func_238414_a_(title) / 2f + getInvNameOffset().x, 5 + getInvNameOffset().y, getTitleColor());
        }

        if (getInvTextOffset() != null) {
            font.drawString(matrixStack, I18n.format("container.inventory"), 8 + getInvTextOffset().x, ySize - 94 + getInvTextOffset().y, 0x404040);
        }
    }

    void bindGuiTexture() {
        ResourceLocation guiTexture = getGuiTexture();
        if (guiTexture != null) {
            minecraft.getTextureManager().bindTexture(guiTexture);
            RenderSystem.enableTexture();
        }
    }

    protected abstract ResourceLocation getGuiTexture();

    @Override
    public void render(MatrixStack matrixStack, int x, int y, float partialTick) {
        renderBackground(matrixStack);

        super.render(matrixStack, x, y, partialTick);

        // renderHoveredTooltip
        func_230459_a_(matrixStack, x, y);

        List<ITextComponent> tooltip = new ArrayList<>();
        RenderSystem.color4f(1, 1, 1, 1);
        RenderSystem.disableLighting();
    }

    protected int getTitleColor() { return 0x404040; }

    protected PointXY getInvNameOffset() {
        return PointXY.ZERO;
    }

    protected PointXY getInvTextOffset() {
        return PointXY.ZERO;
    }

    @Override
    public void tick() {
        super.tick();

        if (sendDelay > 0 && --sendDelay <= 0) {
            doDelayedAction();
            sendDelay = -1;
        }
        if (te instanceof IRedstoneControlled) {
            redstoneAllows = te.redstoneAllows();
        }

        firstUpdate = false;
    }

    void sendGUIButtonPacketToServer(String tag) {
        NetworkHandler.sendToServer(new PacketGuiButton(tag));
    }

    void drawHoveringString(MatrixStack matrixStack, List<ITextComponent> text, int x, int y, FontRenderer fontRenderer) {
        net.minecraftforge.fml.client.gui.GuiUtils.drawHoveringText(matrixStack, text, x, y, width, height, -1, fontRenderer);
    }

    @Override
    public int getGuiLeft() {
        return guiLeft;
    }

    @Override
    public int getGuiTop() {
        return guiTop;
    }

    void refreshScreen() {
        MainWindow mw = Minecraft.getInstance().getMainWindow();
        int i = mw.getScaledWidth();
        int j = mw.getScaledHeight();
        init(Minecraft.getInstance(), i, j);
        buttons.stream().filter(widget -> widget instanceof ITickable).forEach(w -> ((ITickable) w).tick());
    }

    /**
     * Schedule a delayed action to be done some time in the future. Calling this again will reset the delay.
     * Useful to avoid excessive network traffic if sending updates to the server from a textfield change.
     * @param ticks number of ticks to delay
     */
    protected void sendDelayed(int ticks) {
        sendDelay = ticks;
    }

    /**
     * Run the delayed action set up by sendDelayed()
     */
    protected void doDelayedAction() {
        // nothing; override in subclasses
    }

    @Override
    public void onClose() {
        if (sendDelay > 0) doDelayedAction();  // ensure any pending delayed action is done

        super.onClose();
    }

    protected boolean shouldParseVariablesInTooltips() {
        return false;
    }
}