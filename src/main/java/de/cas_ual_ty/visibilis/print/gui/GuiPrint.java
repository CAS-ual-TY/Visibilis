package de.cas_ual_ty.visibilis.print.gui;

import java.io.IOException;

import de.cas_ual_ty.visibilis.print.IPrintProvider;
import net.minecraft.client.gui.GuiScreen;

public class GuiPrint extends GuiScreen
{
    public IPrintProvider helper;
    public UiBase uiLogic;
    
    public GuiPrint(IPrintProvider helper)
    {
        this.helper = helper;
        this.uiLogic = new UiBase(this, this.helper);
        
        this.helper.onGuiOpen(this);
    }
    
    @Override
    public void initGui()
    {
        this.uiLogic.guiInitGui();
        super.initGui();
    }
    
    @Override
    public void onGuiClosed()
    {
        this.uiLogic.guiOnGuiClosed();
        this.helper.onGuiClose(this);
        super.onGuiClosed();
    }
    
    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks)
    {
        this.uiLogic.guiDrawScreen(mouseX, mouseY, partialTicks);
        super.drawScreen(mouseX, mouseY, partialTicks);
    }
    
    @Override
    protected void keyTyped(char typedChar, int keyCode) throws IOException
    {
        this.uiLogic.guiKeyTyped(typedChar, keyCode);
        super.keyTyped(typedChar, keyCode);
    }
    
    @Override
    protected void mouseClicked(int mouseX, int mouseY, int mouseButton) throws IOException
    {
        this.uiLogic.guiMouseClicked(mouseX, mouseY, mouseButton);
        super.mouseClicked(mouseX, mouseY, mouseButton);
    }
    
    @Override
    protected void mouseClickMove(int mouseX, int mouseY, int clickedMouseButton, long timeSinceLastClick)
    {
        this.uiLogic.guiMouseClickMove(mouseX, mouseY, clickedMouseButton, timeSinceLastClick);
        super.mouseClickMove(mouseX, mouseY, clickedMouseButton, timeSinceLastClick);
    }
    
    @Override
    protected void mouseReleased(int mouseX, int mouseY, int state)
    {
        this.uiLogic.guiMouseReleased(mouseX, mouseY, state);
        super.mouseReleased(mouseX, mouseY, state);
    }
}
