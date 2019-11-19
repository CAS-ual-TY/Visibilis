package de.cas_ual_ty.visibilis.print.ui.component;

import de.cas_ual_ty.visibilis.print.Print;
import de.cas_ual_ty.visibilis.print.impl.IPrintProvider;
import de.cas_ual_ty.visibilis.print.ui.RenderUtility;
import de.cas_ual_ty.visibilis.print.ui.RenderUtility.Rectangle;
import de.cas_ual_ty.visibilis.print.ui.UiBase;
import de.cas_ual_ty.visibilis.print.ui.util.MouseInteractionObject;
import net.minecraft.client.MainWindow;
import net.minecraft.client.gui.IGuiEventListener;
import net.minecraft.client.gui.screen.Screen;

public abstract class Component implements IGuiEventListener
{
    public final MouseInteractionObject hoverObj = new MouseInteractionObject();
    
    public UiBase guiPrint;
    public RenderUtility util;
    public IPrintProvider provider;
    
    /** Dimensions (Rectangle) of this window component. Controlled by the parent UiBase instance */
    public Rectangle dimensions;
    
    public boolean mouseOverDimensions;
    
    public Component(UiBase guiPrint, RenderUtility util, IPrintProvider helper)
    {
        this.guiPrint = guiPrint;
        this.util = util;
        this.provider = helper;
        
        this.guiPrint.children.add(this);
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
    
    /**
     * Called from {@link UiBase#guiInit()}
     */
    public void guiInitGui()
    {
    }
    
    /**
     * Called from {@link UiBase#guiOnClose()}
     */
    public void guiOnGuiClosed()
    {
    }
    
    /**
     * Called from {@link UiBase#guiDrawScreen(int, int, float)}
     */
    public void guiRender(int mouseX, int mouseY, float partialTicks)
    {
    }
    
    /**
     * Called from {@link UiBase#guiDrawScreen(int, int, float)}
     */
    public void guiPostRender(int mouseX, int mouseY, float partialTicks)
    {
    }
    
    /**
     * Called from {@link UiBase#guiTick()}
     */
    public void guiTick()
    {
    }
    
    //Helper method
    public Print getPrint()
    {
        return this.provider.getPrint(this.getParentGui());
    }
    
    //Helper method
    /**
     * @return {@link UiBase#getScaledResolution()}
     */
    public MainWindow getSR()
    {
        return this.guiPrint.getScaledResolution();
    }
    
    public Screen getParentGui()
    {
        return this.guiPrint.getParentGui();
    }
    
    @Override
    public void mouseMoved(double mouseX, double mouseY)
    {
        
    }
    
    @Override
    public boolean mouseClicked(double mouseX, double mouseY, int modifiers)
    {
        return false;
    }
    
    @Override
    public boolean mouseReleased(double mouseX, double mouseY, int modifiers)
    {
        return false;
    }
    
    @Override
    public boolean mouseDragged(double mouseX, double mouseY, int modifiers, double deltaX, double deltaY)
    {
        return false;
    }
    
    @Override
    public boolean mouseScrolled(double mouseX, double mouseY, double amountScrolled)
    {
        return false;
    }
    
    @Override
    public boolean keyPressed(int keyCode, int scanCode, int modifiers)
    {
        return false;
    }
    
    @Override
    public boolean keyReleased(int keyCode, int scanCode, int modifiers)
    {
        return false;
    }
    
    @Override
    public boolean charTyped(char typedChar, int keyCode)
    {
        return false;
    }
}
