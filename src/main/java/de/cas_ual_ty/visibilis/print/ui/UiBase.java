package de.cas_ual_ty.visibilis.print.ui;

import java.util.ArrayList;
import java.util.function.Consumer;

import de.cas_ual_ty.visibilis.node.Node;
import de.cas_ual_ty.visibilis.print.provider.PrintProvider;
import de.cas_ual_ty.visibilis.print.ui.RenderUtility.Rectangle;
import de.cas_ual_ty.visibilis.print.ui.component.Component;
import de.cas_ual_ty.visibilis.print.ui.component.ComponentHeader;
import de.cas_ual_ty.visibilis.print.ui.component.ComponentNodeList;
import de.cas_ual_ty.visibilis.print.ui.component.ComponentPrint;
import net.minecraft.client.MainWindow;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.IGuiEventListener;
import net.minecraft.client.gui.screen.Screen;

public class UiBase implements IGuiEventListener
{
    public ArrayList<Component> children;
    
    protected Screen gui;
    
    protected Consumer<Node> addToPrint;
    
    private final RenderUtility util;
    private final PrintProvider provider;
    
    // Helper fields
    public double lastMousePosX;
    public double lastMousePosY;
    
    public ComponentPrint windowPrint;
    public ComponentNodeList windowNodeList;
    public ComponentHeader windowHeader;
    
    public UiBase(Screen gui, PrintProvider provider)
    {
        this.addToPrint = provider.getPrint()::addNode;
        
        this.children = new ArrayList<>();
        
        this.gui = gui;
        
        this.util = new RenderUtility(this.gui);
        this.provider = provider;
        
        this.windowPrint = this.createCPrint();
        this.windowNodeList = this.createCNodeList();
        this.windowHeader = this.createCHeader();
    }
    
    public RenderUtility getUtil()
    {
        return this.util;
    }
    
    public PrintProvider getProvider()
    {
        return this.provider;
    }
    
    public ComponentPrint createCPrint()
    {
        return new ComponentPrint(this);
    }
    
    public ComponentNodeList createCNodeList()
    {
        return new ComponentNodeList(this);
    }
    
    public ComponentHeader createCHeader()
    {
        return new ComponentHeader(this);
    }
    
    // All the following functions MUST be invoked by the GuiScreen in order to make this work
    // --- START ---
    
    /**
     * Called from {@link GuiScreen#initGui()}
     */
    public void guiInit()
    {
        this.initComponentDimensions();
        
        for(Component c : this.children)
        {
            c.guiInitGui();
        }
    }
    
    /**
     * Called from {@link GuiScreen#onGuiClosed()}
     */
    public void guiOnClose()
    {
        for(Component c : this.children)
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
        
        for(Component c : this.children)
        {
            c.updateMouseOverDimensions(mouseX, mouseY);
        }
        
        for(Component c : this.children)
        {
            c.guiRender(mouseX, mouseY, partialTicks);
        }
        
        for(Component c : this.children)
        {
            c.guiPostRender(mouseX, mouseY, partialTicks);
        }
    }
    
    public void guiTick()
    {
        for(Component c : this.children)
        {
            c.guiTick();
        }
    }
    
    public void initComponentDimensions()
    {
        int w = (int)(this.getUtil().nodeWidth * this.windowNodeList.zoom);
        int h = 20;
        
        this.windowPrint.setDimensions(Rectangle.fromXYWH(0, h, this.getScaledResolution().getScaledWidth() - w, this.getScaledResolution().getScaledHeight() - h));
        this.windowNodeList.setDimensions(Rectangle.fromXYWH(this.getScaledResolution().getScaledWidth() - w, 0, w, this.getScaledResolution().getScaledHeight()));
        this.windowHeader.setDimensions(Rectangle.fromXYWH(0, 0, this.windowPrint.dimensions.w, h));
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
        return Minecraft.getInstance().getMainWindow();
    }
    
    @Override
    public void mouseMoved(double mouseX, double mouseY)
    {
        this.setLastMousePos(mouseX, mouseY);
        
        for(Component c : this.children)
        {
            c.mouseMoved(mouseX, mouseY);
        }
    }
    
    @Override
    public boolean mouseClicked(double mouseX, double mouseY, int modifiers)
    {
        this.setLastMousePos(mouseX, mouseY);
        
        for(Component c : this.children)
        {
            if(c.mouseClicked(mouseX, mouseY, modifiers))
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
        
        for(Component c : this.children)
        {
            if(c.mouseReleased(mouseX, mouseY, modifiers))
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
        
        for(Component c : this.children)
        {
            if(c.mouseDragged(mouseX, mouseY, modifiers, deltaX, deltaY))
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
        
        for(Component c : this.children)
        {
            if(c.mouseScrolled(mouseX, mouseY, amountScrolled))
            {
                return true;
            }
        }
        
        return false;
    }
    
    @Override
    public boolean keyPressed(int keyCode, int scanCode, int modifiers)
    {
        for(Component c : this.children)
        {
            if(c.keyPressed(keyCode, scanCode, modifiers))
            {
                return true;
            }
        }
        
        return false;
    }
    
    @Override
    public boolean keyReleased(int keyCode, int scanCode, int modifiers)
    {
        for(Component c : this.children)
        {
            if(c.keyReleased(keyCode, scanCode, modifiers))
            {
                return true;
            }
        }
        
        return false;
    }
    
    @Override
    public boolean charTyped(char typedChar, int keyCode)
    {
        if(this.windowPrint.isolateInput())
        {
            this.windowPrint.charTyped(typedChar, keyCode);
            return true;
        }
        else if(this.windowNodeList.isolateInput())
        {
            this.windowNodeList.charTyped(typedChar, keyCode);
            return true;
        }
        
        this.windowPrint.charTyped(typedChar, keyCode);
        this.windowNodeList.charTyped(typedChar, keyCode);
        
        return false;
    }
    
    public UiBase setAddToPrint(Consumer<Node> addToPrint)
    {
        this.addToPrint = addToPrint;
        return this;
    }
    
    public void addNodeToPrint(Node n)
    {
        this.addToPrint.accept(n);
        this.getProvider().onNodeAdded(n);
    }
}
