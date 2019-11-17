package de.cas_ual_ty.visibilis.print.gui.util;

import de.cas_ual_ty.visibilis.node.NodeAction;
import de.cas_ual_ty.visibilis.print.gui.component.Component;
import de.cas_ual_ty.visibilis.util.RenderUtility;
import de.cas_ual_ty.visibilis.util.RenderUtility.Rectangle;
import net.minecraft.client.gui.IGuiEventListener;
import net.minecraft.client.resources.I18n;

public class NodeActionWidget implements IGuiEventListener
{
    public final Component component;
    public final NodeAction[] actions;
    
    public Rectangle dimensions;
    
    public NodeAction hoverObj;
    
    public NodeActionWidget(Component component, int mouseX, int mouseY, Rectangle dimensionsIn, NodeAction... actions)
    {
        this.component = component;
        this.actions = actions;
        
        this.createDimensions(mouseX, mouseY, dimensionsIn);
        this.hoverObj = null;
    }
    
    public void createDimensions(int mouseX, int mouseY, Rectangle dimensionsIn)
    {
        int w = this.component.guiPrint.util.nodeWidth;
        int h = this.component.guiPrint.util.nodeHeight * this.actions.length;
        
        Rectangle dim = Rectangle.fromXYWH(mouseX - w, mouseY - h, w, h);
        
        if(dim.r > dimensionsIn.r)
        {
            dim.l -= dim.r - dimensionsIn.r;
            dim.r -= dim.r - dimensionsIn.r;
        }
        
        if(dim.b > dimensionsIn.b)
        {
            dim.t -= dim.b - dimensionsIn.b;
            dim.b -= dim.b - dimensionsIn.b;
        }
        
        if(dim.l < dimensionsIn.l)
        {
            dim.l += dimensionsIn.l - dim.l;
            dim.r += dimensionsIn.l - dim.l;
        }
        
        if(dim.t < dimensionsIn.t)
        {
            dim.t += dimensionsIn.t - dim.t;
            dim.b += dimensionsIn.t - dim.t;
        }
        
        dim.updateXYWH();
        
        this.dimensions = dim;
    }
    
    public void guiRender(int mouseX, int mouseY, float partialTicks)
    {
        this.hoverObj = null;
        this.dimensions.render(this.component.util.nodeBackground[0], this.component.util.nodeBackground[1], this.component.util.nodeBackground[2]);
        
        NodeAction action;
        int x = this.dimensions.x;
        int y = this.dimensions.y;
        int w = this.dimensions.w;
        int h = this.component.util.nodeHeight;
        
        int marginText = 2;
        
        for(int i = 0; i < this.actions.length; ++i)
        {
            action = this.actions[i];
            RenderUtility.drawRect(x, y, w, h, 1, this.component.util.actionColor);
            RenderUtility.drawTextCentered(this.component.util.fontRenderer, x, y + marginText, this.dimensions.w + marginText, I18n.format(action.text), this.component.util.actionColorText);
            
            if(RenderUtility.isCoordInsideRect(mouseX, mouseY, x, y, w, h))
            {
                this.hoverObj = action;
                this.component.util.drawHoverRect(x, y, w, h);
            }
            
            y += h;
        }
    }
    
    @Override
    public boolean mouseClicked(double mouseX, double mouseY, int modifiers)
    {
        if(modifiers == 0 && this.hoverObj != null)
        {
            this.hoverObj.clicked();
            return true;
        }
        
        return false;
    }
}
