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
    public WindowNodeList windowNodeList;
    
    public final RenderUtility util;
    public final IPrintHelper helper;
    
    public int lastMousePosX;
    public int lastMousePosY;
    
    public PrintUILogic(GuiScreen gui, IPrintHelper helper)
    {
        this.gui = gui;
        this.util = new RenderUtility(this.gui);
        this.helper = helper;
        
        this.windowPrint = new WindowPrint(this, this.util, this.helper);
        this.windowNodeList = new WindowNodeList(this, this.util, this.helper);
    }
    
    // All the following functions MUST be invoked by the GuiScreen in order to make this work
    // --- START ---
    
    public void guiInitGui()
    {
        this.sr = new ScaledResolution(Minecraft.getMinecraft());
        
        int w = (int) (this.util.nodeWidth * this.windowNodeList.zoom);
        this.windowPrint.setDimensions(Rectangle.fromXYWH(0, 0, this.sr.getScaledWidth() - w, this.sr.getScaledHeight()));
        this.windowNodeList.setDimensions(Rectangle.fromXYWH(this.sr.getScaledWidth() - w, 0, w, this.sr.getScaledHeight()));
        
        this.windowPrint.guiInitGui();
        this.windowNodeList.guiInitGui();
    }
    
    public void guiOnGuiClosed()
    {
        this.windowPrint.guiOnGuiClosed();
    }
    
    public void guiDrawScreen(int mouseX, int mouseY, float partialTicks)
    {
        this.setLastMousePos(mouseX, mouseY);
        
        this.windowPrint.updateMouseOverDimensions(mouseX, mouseY);
        this.windowNodeList.updateMouseOverDimensions(mouseX, mouseY);
        
        this.windowPrint.guiDrawScreen(mouseX, mouseY, partialTicks);
        this.windowNodeList.guiDrawScreen(mouseX, mouseY, partialTicks);
        
        this.windowPrint.guiPostDrawScreen(mouseX, mouseY, partialTicks);
        this.windowNodeList.guiPostDrawScreen(mouseX, mouseY, partialTicks);
    }
    
    public void guiKeyTyped(char typedChar, int keyCode)
    {
        if(this.windowPrint.isolateInput())
        {
            this.windowPrint.guiKeyTyped(typedChar, keyCode);
            return;
        }
        else if(this.windowNodeList.isolateInput())
        {
            this.windowNodeList.guiKeyTyped(typedChar, keyCode);
            return;
        }
        
        this.windowPrint.guiKeyTyped(typedChar, keyCode);
        this.windowNodeList.guiKeyTyped(typedChar, keyCode);
    }
    
    public void guiMouseClicked(int mouseX, int mouseY, int mouseButton)
    {
        this.setLastMousePos(mouseX, mouseY);
        this.windowPrint.guiMouseClicked(mouseX, mouseY, mouseButton);
        this.windowNodeList.guiMouseClicked(mouseX, mouseY, mouseButton);
    }
    
    public void guiMouseClickMove(int mouseX, int mouseY, int clickedMouseButton, long timeSinceLastClick)
    {
        this.setLastMousePos(mouseX, mouseY);
        this.windowPrint.guiMouseClickMove(mouseX, mouseY, clickedMouseButton, timeSinceLastClick);
        this.windowNodeList.guiMouseClickMove(mouseX, mouseY, clickedMouseButton, timeSinceLastClick);
    }
    
    public void guiMouseReleased(int mouseX, int mouseY, int state)
    {
        this.setLastMousePos(mouseX, mouseY);
        this.windowPrint.guiMouseReleased(mouseX, mouseY, state);
        this.windowNodeList.guiMouseReleased(mouseX, mouseY, state);
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
