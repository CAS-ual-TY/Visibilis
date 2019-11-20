package de.cas_ual_ty.visibilis.print.ui.util;

import java.util.ArrayList;

import de.cas_ual_ty.visibilis.node.NodeAction;
import de.cas_ual_ty.visibilis.print.ui.RenderUtility;
import de.cas_ual_ty.visibilis.print.ui.RenderUtility.Rectangle;
import de.cas_ual_ty.visibilis.print.ui.component.Component;
import net.minecraft.client.gui.IGuiEventListener;
import net.minecraft.client.resources.I18n;

public class NodeActionWidget implements IGuiEventListener
{
    public final Component component;
    public final ArrayList<NodeAction> actions;
    
    public Rectangle dimensions;
    
    public NodeAction hoverObj;
    
    public NodeActionWidget(Component component, int mouseX, int mouseY, Rectangle dimensionsIn, ArrayList<NodeAction> actions)
    {
        this.component = component;
        this.actions = actions;
        
        this.createDimensions(mouseX, mouseY, dimensionsIn);
        this.hoverObj = null;
    }
    
    public void createDimensions(int mouseX, int mouseY, Rectangle dimensionsIn)
    {
        int w = this.component.uiBase.util.nodeWidth;
        int h = this.component.uiBase.util.nodeHeight * this.actions.size();
        
        Rectangle dim = Rectangle.fromXYWH(mouseX - w, mouseY - h, w, h);
        
        if (dim.r > dimensionsIn.r)
        {
            dim.l -= dim.r - dimensionsIn.r;
            dim.r -= dim.r - dimensionsIn.r;
        }
        
        if (dim.b > dimensionsIn.b)
        {
            dim.t -= dim.b - dimensionsIn.b;
            dim.b -= dim.b - dimensionsIn.b;
        }
        
        if (dim.l < dimensionsIn.l)
        {
            dim.l += dimensionsIn.l - dim.l;
            dim.r += dimensionsIn.l - dim.l;
        }
        
        if (dim.t < dimensionsIn.t)
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
        this.dimensions.render(this.component.getUtil().nodeBackground[0], this.component.getUtil().nodeBackground[1], this.component.getUtil().nodeBackground[2]);
        
        NodeAction action;
        int x = this.dimensions.x;
        int y = this.dimensions.y;
        int w = this.dimensions.w;
        int h = this.component.getUtil().nodeHeight;
        
        int marginText = 2;
        
        for (int i = 0; i < this.actions.size(); ++i)
        {
            action = this.actions.get(i);
            RenderUtility.drawRect(x, y, w, h, 1, this.component.getUtil().actionColor);
            RenderUtility.drawTextCentered(this.component.getUtil().fontRenderer, x, y + marginText, this.dimensions.w + marginText, I18n.format(action.text), this.component.getUtil().actionColorText);
            
            if (RenderUtility.isCoordInsideRect(mouseX, mouseY, x, y, w, h))
            {
                this.hoverObj = action;
                this.component.getUtil().drawHoverRect(x, y, w, h);
            }
            
            y += h;
        }
    }
    
    @Override
    public boolean mouseClicked(double mouseX, double mouseY, int modifiers)
    {
        if (modifiers == 0 && this.hoverObj != null)
        {
            this.hoverObj.clicked();
            return true;
        }
        
        return false;
    }
}