package de.cas_ual_ty.visibilis.print.gui;

import java.util.ArrayList;

import de.cas_ual_ty.visibilis.print.IPrintProvider;
import de.cas_ual_ty.visibilis.print.gui.component.Component;
import de.cas_ual_ty.visibilis.print.gui.component.ComponentNodeList;
import de.cas_ual_ty.visibilis.print.gui.component.ComponentPrint;
import de.cas_ual_ty.visibilis.util.RenderUtility;
import de.cas_ual_ty.visibilis.util.RenderUtility.Rectangle;
import net.minecraft.client.MainWindow;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.IGuiEventListener;
import net.minecraft.client.gui.screen.Screen;

public class UiBase implements IGuiEventListener
{
    public ArrayList<Component> children;
    
    protected Screen gui;
    
    public final RenderUtility util;
    public final IPrintProvider helper;
    
    // Helper fields
    public double lastMousePosX;
    public double lastMousePosY;
    
    public ComponentPrint windowPrint;
    public ComponentNodeList windowNodeList;
    
    public UiBase(Screen gui, IPrintProvider helper)
    {
        this.children = new ArrayList<>();
        
        this.gui = gui;
        
        this.util = new RenderUtility(this.gui);
        this.helper = helper;
        
        this.windowPrint = new ComponentPrint(this, this.util, this.helper);
        this.windowNodeList = new ComponentNodeList(this, this.util, this.helper);
    }
    
    // All the following functions MUST be invoked by the GuiScreen in order to make this work
    // --- START ---
    
    /**
     * Called from {@link GuiScreen#initGui()}
     */
    public void guiInit()
    {
        int w = (int) (this.util.nodeWidth * this.windowNodeList.zoom);
        this.windowPrint.setDimensions(Rectangle.fromXYWH(0, 0, this.getScaledResolution().getScaledWidth() - w, this.getScaledResolution().getScaledHeight()));
        this.windowNodeList.setDimensions(Rectangle.fromXYWH(this.getScaledResolution().getScaledWidth() - w, 0, w, this.getScaledResolution().getScaledHeight()));
        
        for (Component c : this.children)
        {
            c.guiInitGui();
        }
    }
    
    /**
     * Called from {@link GuiScreen#onGuiClosed()}
     */
    public void guiOnClose()
    {
        for (Component c : this.children)
        {
            c.guiOnGuiClosed();
        }
    }
    
    /**
     * Called from {@link GuiScreen#drawScreen(int, int, float)}
     */
    public void guiDrawScreen(int mouseX, int mouseY, float partialTicks)
    {
        this.setLastMousePos(mouseX, mouseY);
        
        for (Component c : this.children)
        {
            c.updateMouseOverDimensions(mouseX, mouseY);
        }
        
        for (Component c : this.children)
        {
            c.guiRender(mouseX, mouseY, partialTicks);
        }
        
        for (Component c : this.children)
        {
            c.guiPostRender(mouseX, mouseY, partialTicks);
        }
    }
    
    public void guiTick()
    {
        for (Component c : this.children)
        {
            c.guiTick();
        }
    }
    
    /**
     * Helper method.
     */
    protected void setLastMousePos(double mouseX, double mouseY)
    {
        this.lastMousePosX = mouseX;
        this.lastMousePosY = mouseY;
    }
    
    /**
     * @return The parent GuiScreen instance outsourcing this instance
     */
    public Screen getParentGui()
    {
        return this.gui;
    }
    
    /**
     * @return The current {@link ScaledResolution}
     */
    public MainWindow getScaledResolution()
    {
        return Minecraft.getInstance().mainWindow;
    }
    
    @Override
    public void mouseMoved(double mouseX, double mouseY)
    {
        this.setLastMousePos(mouseX, mouseY);
        
        for (Component c : this.children)
        {
            c.mouseMoved(mouseX, mouseY);
        }
    }
    
    @Override
    public boolean mouseClicked(double mouseX, double mouseY, int modifiers)
    {
        this.setLastMousePos(mouseX, mouseY);
        
        for (Component c : this.children)
        {
            if (c.mouseClicked(mouseX, mouseY, modifiers))
            {
                return true;
            }
        }
        
        return false;
    }
    
    @Override
    public boolean mouseReleased(double mouseX, double mouseY, int modifiers)
    {
        this.setLastMousePos(mouseX, mouseY);
        
        for (Component c : this.children)
        {
            if (c.mouseReleased(mouseX, mouseY, modifiers))
            {
                return true;
            }
        }
        
        return false;
    }
    
    @Override
    public boolean mouseDragged(double mouseX, double mouseY, int modifiers, double deltaX, double deltaY)
    {
        this.setLastMousePos(mouseX, mouseY);
        
        for (Component c : this.children)
        {
            if (c.mouseDragged(mouseX, mouseY, modifiers, deltaX, deltaY))
            {
                return true;
            }
        }
        
        return false;
    }
    
    @Override
    public boolean mouseScrolled(double mouseX, double mouseY, double amountScrolled)
    {
        this.setLastMousePos(mouseX, mouseY);
        
        for (Component c : this.children)
        {
            if (c.mouseScrolled(mouseX, mouseY, amountScrolled))
            {
                return true;
            }
        }
        
        return false;
    }
    
    @Override
    public boolean keyPressed(int keyCode, int scanCode, int modifiers)
    {
        for (Component c : this.children)
        {
            if (c.keyPressed(keyCode, scanCode, modifiers))
            {
                return true;
            }
        }
        
        return false;
    }
    
    @Override
    public boolean keyReleased(int keyCode, int scanCode, int modifiers)
    {
        for (Component c : this.children)
        {
            if (c.keyReleased(keyCode, scanCode, modifiers))
            {
                return true;
            }
        }
        
        return false;
    }
    
    @Override
    public boolean charTyped(char typedChar, int keyCode)
    {
        if (this.windowPrint.isolateInput())
        {
            this.windowPrint.charTyped(typedChar, keyCode);
            return true;
        }
        else if (this.windowNodeList.isolateInput())
        {
            this.windowNodeList.charTyped(typedChar, keyCode);
            return true;
        }
        
        this.windowPrint.charTyped(typedChar, keyCode);
        this.windowNodeList.charTyped(typedChar, keyCode);
        
        return false;
    }
}