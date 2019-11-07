package de.cas_ual_ty.visibilis.print.gui.component;

import de.cas_ual_ty.visibilis.print.IPrintProvider;
import de.cas_ual_ty.visibilis.print.Print;
import de.cas_ual_ty.visibilis.print.gui.UiBase;
import de.cas_ual_ty.visibilis.util.RenderUtility;
import de.cas_ual_ty.visibilis.util.RenderUtility.Rectangle;
import net.minecraft.client.gui.ScaledResolution;

public abstract class Component
{
    public final MouseInteractionObject hoverObj = new MouseInteractionObject();
    
    public UiBase guiPrint;
    public RenderUtility util;
    public IPrintProvider helper;
    
    /** Dimensions (Rectangle) of this window component. Controlled by the parent UiBase instance */
    public Rectangle dimensions;
    
    public boolean mouseOverDimensions;
    
    public Component(UiBase guiPrint, RenderUtility util, IPrintProvider helper)
    {
        this.guiPrint = guiPrint;
        this.util = util;
        this.helper = helper;
    }
    
    // --- START ---
    // --- Methods called from UiBase ---
    
    public Component setDimensions(Rectangle dimensions)
    {
        this.dimensions = dimensions;
        return this;
    }
    
    public void updateMouseOverDimensions(int mouseX, int mouseY)
    {
        this.mouseOverDimensions = this.dimensions.isCoordInside(mouseX, mouseY);
    }
    
    /**
     * If <b>true</b> then all other components should not receive any input keys, only this component
     * @return
     */
    public boolean isolateInput()
    {
        return false;
    }
    
    // --- Methods called from UiBase, called from GuiScreen ---
    
    /**
     * Called from {@link UiBase#guiInitGui()}
     */
    public void guiInitGui()
    {
    }
    
    /**
     * Called from {@link UiBase#guiOnGuiClosed()}
     */
    public void guiOnGuiClosed()
    {
    }
    
    /**
     * Called from {@link UiBase#guiDrawScreen(int, int, float)}
     */
    public void guiDrawScreen(int mouseX, int mouseY, float partialTicks)
    {
    }
    
    /**
     * Called from {@link UiBase#guiDrawScreen(int, int, float)}
     */
    public void guiPostDrawScreen(int mouseX, int mouseY, float partialTicks)
    {
    }
    
    /**
     * Called from {@link UiBase#guiKeyTyped(char, int)}
     */
    public void guiKeyTyped(char typedChar, int keyCode)
    {
    }
    
    /**
     * Called from {@link UiBase#guiMouseClicked(int, int, int)}
     */
    public void guiMouseClicked(int mouseX, int mouseY, int mouseButton)
    {
    }
    
    /**
     * Called from {@link UiBase#guiMouseClickMove(int, int, int, long)}
     */
    public void guiMouseClickMove(int mouseX, int mouseY, int clickedMouseButton, long timeSinceLastClick)
    {
    }
    
    /**
     * Called from {@link UiBase#guiMouseReleased(int, int, int)}
     */
    public void guiMouseReleased(int mouseX, int mouseY, int state)
    {
    }
    
    // --- END ---
    
    //Helper method
    public Print getPrint()
    {
        return this.helper.getPrint(this.guiPrint.getParentGui());
    }
    
    //Helper method
    /**
     * @return {@link UiBase#getScaledResolution()}
     */
    public ScaledResolution getSR()
    {
        return this.guiPrint.getScaledResolution();
    }
}
