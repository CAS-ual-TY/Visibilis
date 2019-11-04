package de.cas_ual_ty.visibilis.print.gui;

import de.cas_ual_ty.visibilis.print.IPrintHelper;
import de.cas_ual_ty.visibilis.print.Print;
import de.cas_ual_ty.visibilis.util.RenderUtility;
import de.cas_ual_ty.visibilis.util.RenderUtility.Rectangle;
import net.minecraft.client.gui.ScaledResolution;

public abstract class WindowBase
{
    public final MouseInteractionObject hoverObj = new MouseInteractionObject();
    
    public PrintUILogic guiPrint;
    public RenderUtility util;
    public IPrintHelper helper;
    public Rectangle dimensions;
    
    public boolean mouseOverDimensions;
    
    public WindowBase(PrintUILogic guiPrint, RenderUtility util, IPrintHelper helper)
    {
        this.guiPrint = guiPrint;
        this.util = util;
        this.helper = helper;
    }
    
    public void deselect()
    {
    }
    
    public WindowBase setDimensions(Rectangle dimensions)
    {
        this.dimensions = dimensions;
        return this;
    }
    
    public void updateMouseOverDimensions(int mouseX, int mouseY)
    {
        this.mouseOverDimensions = this.dimensions.isCoordInside(mouseX, mouseY);
    }
    
    public void guiInitGui()
    {
    }
    
    public void guiOnGuiClosed()
    {
    }
    
    public void guiDrawScreen(int mouseX, int mouseY, float partialTicks)
    {
    }
    
    public void guiPostDrawScreen(int mouseX, int mouseY, float partialTicks)
    {
    }
    
    public void guiKeyTyped(char typedChar, int keyCode)
    {
    }
    
    public void guiMouseClicked(int mouseX, int mouseY, int mouseButton)
    {
    }
    
    public void guiMouseClickMove(int mouseX, int mouseY, int clickedMouseButton, long timeSinceLastClick)
    {
    }
    
    public void guiMouseReleased(int mouseX, int mouseY, int state)
    {
    }
    
    public boolean isolateInput()
    {
        return false;
    }
    
    public Print getPrint()
    {
        return this.helper.getPrint(this.guiPrint.getParentGui()); //TODO Rewrite
    }
    
    public ScaledResolution getSR()
    {
        return this.guiPrint.getScaledResolution();
    }
}
