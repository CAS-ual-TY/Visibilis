package de.cas_ual_ty.visibilis.print.ui.util;

import java.util.List;

import de.cas_ual_ty.visibilis.node.NodeAction;
import de.cas_ual_ty.visibilis.print.ui.component.Component;
import de.cas_ual_ty.visibilis.util.VRenderUtility;
import net.minecraft.client.gui.IGuiEventListener;
import net.minecraft.client.resources.I18n;

public class NodeActionWidget implements IGuiEventListener
{
    public final Component component;
    public final List<NodeAction> actions;
    
    public VRenderUtility.Rectangle dimensions;
    
    public NodeAction hoverObj;
    
    public NodeActionWidget(Component component, int mouseX, int mouseY, VRenderUtility.Rectangle dimensionsIn, List<NodeAction> actions)
    {
        this.component = component;
        this.actions = actions;
        
        this.createDimensions(mouseX, mouseY, dimensionsIn);
        this.hoverObj = null;
    }
    
    public void createDimensions(int mouseX, int mouseY, VRenderUtility.Rectangle dimensionsIn)
    {
        int w = this.component.getUiBase().getUtil().nodeWidth;
        int h = this.component.getUiBase().getUtil().nodeHeight * this.actions.size();
        
        int midX = dimensionsIn.x + dimensionsIn.w / 2;
        int midY = dimensionsIn.y + dimensionsIn.h / 2;
        
        int x, y;
        
        if(mouseX >= midX)
        {
            x = mouseX - w;
        }
        else
        {
            x = mouseX;
        }
        
        if(mouseY >= midY)
        {
            y = mouseY - h;
        }
        else
        {
            y = mouseY;
        }
        
        this.dimensions = VRenderUtility.Rectangle.fromXYWH(x, y, w, h);
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
        
        for(int i = 0; i < this.actions.size(); ++i)
        {
            action = this.actions.get(i);
            VRenderUtility.drawRect(x, y, w, h, 1, this.component.getUtil().actionColor);
            VRenderUtility.drawTextCentered(this.component.getUtil().fontRenderer, x, y + marginText, this.dimensions.w + marginText, I18n.format(action.text), this.component.getUtil().actionColorText);
            
            if(VRenderUtility.isCoordInsideRect(mouseX, mouseY, x, y, w, h))
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
        if(modifiers == 0 && this.hoverObj != null)
        {
            this.hoverObj.clicked();
            return true;
        }
        
        return false;
    }
}
