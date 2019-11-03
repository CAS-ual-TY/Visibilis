package de.cas_ual_ty.visibilis.print.gui;

import de.cas_ual_ty.visibilis.print.IPrintHelper;
import de.cas_ual_ty.visibilis.util.RenderUtility;
import de.cas_ual_ty.visibilis.util.RenderUtility.Rectangle;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.ScaledResolution;

public class PrintUILogic
{
    protected GuiScreen gui;
    public ScaledResolution sr;
    
    public WindowPrint windowPrint;
    
    public final RenderUtility util;
    public final IPrintHelper helper;
    
    public int lastMousePosX;
    public int lastMousePosY;
    
    public PrintUILogic(GuiScreen gui, IPrintHelper helper)
    {
        this.gui = gui;
        this.util = new RenderUtility(this.gui);
        this.helper = helper;
        
        this.windowPrint = new WindowPrint(this, this.util, helper);
    }
    
    // --- START ---
    
    public void guiInitGui()
    {
        this.sr = new ScaledResolution(Minecraft.getMinecraft());
        this.windowPrint.setDimensions(Rectangle.fromXYWH(0, 0, sr.getScaledWidth(), sr.getScaledHeight()));
        this.windowPrint.guiInitGui();
    }
    
    public void guiOnGuiClosed()
    {
        this.windowPrint.guiOnGuiClosed();
    }
    
    public void guiDrawScreen(int mouseX, int mouseY, float partialTicks)
    {
        this.setLastMousePos(mouseX, mouseY);
        this.windowPrint.updateMouseOverDimensions(mouseX, mouseY);
        this.windowPrint.guiDrawScreen(mouseX, mouseY, partialTicks);
    }
    
    public void guiKeyTyped(char typedChar, int keyCode)
    {
        this.windowPrint.guiKeyTyped(typedChar, keyCode);
    }
    
    public void guiMouseClicked(int mouseX, int mouseY, int mouseButton)
    {
        this.setLastMousePos(mouseX, mouseY);
        this.windowPrint.guiMouseClicked(mouseX, mouseY, mouseButton);
    }
    
    public void guiMouseClickMove(int mouseX, int mouseY, int clickedMouseButton, long timeSinceLastClick)
    {
        this.setLastMousePos(mouseX, mouseY);
        this.windowPrint.guiMouseClickMove(mouseX, mouseY, clickedMouseButton, timeSinceLastClick);
    }
    
    public void guiMouseReleased(int mouseX, int mouseY, int state)
    {
        this.setLastMousePos(mouseX, mouseY);
        this.windowPrint.guiMouseReleased(mouseX, mouseY, state);
    }
    
    // --- END ---
    
    public void setLastMousePos(int mouseX, int mouseY)
    {
        this.lastMousePosX = mouseX;
        this.lastMousePosY = mouseY;
    }
    
    public GuiScreen getParentGui()
    {
        return this.gui;
    }
    
    public ScaledResolution getScaledResolution()
    {
        return this.sr;
    }
}
