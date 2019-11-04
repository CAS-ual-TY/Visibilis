package de.cas_ual_ty.visibilis.print.gui.component;

import java.util.ArrayList;

import org.lwjgl.input.Keyboard;

import de.cas_ual_ty.visibilis.node.Node;
import de.cas_ual_ty.visibilis.node.NodeGroupsHelper;
import de.cas_ual_ty.visibilis.print.IPrintProvider;
import de.cas_ual_ty.visibilis.print.gui.UiBase;
import de.cas_ual_ty.visibilis.print.gui.component.MouseInteractionObject.MouseInteractionType;
import de.cas_ual_ty.visibilis.util.RenderUtility;
import de.cas_ual_ty.visibilis.util.RenderUtility.Rectangle;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiTextField;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.util.StringUtils;

public class ComponentNodeList extends Component
{
    public Rectangle listRect;
    
    public float zoom;
    public int listOffset;
    public GuiTextField searchInput;
    
    public ComponentNodeList(UiBase guiPrint, RenderUtility util, IPrintProvider helper)
    {
        super(guiPrint, util, helper);
        
        this.zoom = 0.5F;
        this.listOffset = 0;
        this.searchInput = new GuiTextField(1, Minecraft.getMinecraft().fontRenderer, 0, 0, 0, 0);
        this.searchInput.setVisible(true);
        this.searchInput.setEnabled(true);
        this.listRect = Rectangle.fromXYWH(0, 0, 0, 0);
    }
    
    @Override
    public void guiInitGui()
    {
        this.updateTextFieldSize();
        this.updateListRect();
    }
    
    @Override
    public void guiDrawScreen(int mouseX, int mouseY, float partialTicks)
    {
        this.dimensions.render(0.375F, 0.375F, 0.375F);
        
        this.hoverObj.nothing();
        
        GlStateManager.pushMatrix();
        RenderUtility.scissorStart(this.guiPrint.getScaledResolution(), this.listRect.x, this.listRect.y, this.listRect.w, this.listRect.h);
        GlStateManager.translate(this.dimensions.x, this.dimensions.y, 0); // Move everything in the print by the print's position
        RenderUtility.applyZoom(0.5F);
        
        int x = 0;
        int y = this.listOffset;
        int w = this.util.nodeWidth;
        int h;
        
        for (Node node : this.getAvailableNodesList())
        {
            this.util.drawNode(node, x, y);
            
            h = this.util.getNodeTotalHeight(node);
            
            if (RenderUtility.isCoordInsideRect(mouseX, mouseY, this.dimensions.x, this.dimensions.y + y / 2, w / 2, h / 2))
            {
                this.hoverObj.node(node);
                this.util.drawHoverRect(x, y, w, h);
            }
            
            y += h + 2;
        }
        
        y -= 2 + this.listOffset;
        
        int offset = 0;//this.util.nodeHeight;
        
        int topRect = this.dimensions.t + offset;
        int botRect = (this.dimensions.b - y) - offset; //TODO properly fit botRect. Right now you can scroll down until the bottom of the nodes list hits roughly the middle of the screen instead of the bottom - nodeHeight.
        
        // listOffset + y / 2 < b
        // <=>
        // listOffset < b - y / 2
        
        if (this.listOffset < botRect) //Bottom of list is below window
        {
            this.listOffset = botRect;
        }
        if (this.listOffset > topRect) //Top of list is above window
        {
            this.listOffset = topRect;
        }
        
        RenderUtility.scissorEnd();
        GlStateManager.popMatrix();
        
        if (RenderUtility.isCoordInsideRect(mouseX, mouseY, this.searchInput.x, this.searchInput.y, this.searchInput.width, this.searchInput.height))
        {
            this.hoverObj.textField(this.searchInput);
        }
        
        this.searchInput.drawTextBox();
    }
    
    @Override
    public void guiPostDrawScreen(int mouseX, int mouseY, float partialTicks)
    {
        if (this.hoverObj.type == MouseInteractionType.NODE)
        {
            this.util.drawNodeHoveringText(this.hoverObj.node, mouseX, mouseY);
        }
    }
    
    @Override
    public void guiKeyTyped(char typedChar, int keyCode)
    {
        if (this.searchInput.isFocused())
        {
            if (keyCode == Keyboard.KEY_RETURN)
            {
                this.searchInput.setFocused(false);
                return;
            }
            else
            {
                //If the text field is visible transfer all pressed keys to it.
                this.searchInput.textboxKeyTyped(typedChar, keyCode);
                return;
            }
        }
        
        if (keyCode == Keyboard.KEY_PRIOR)
        {
            this.scrollUp();
        }
        else if (keyCode == Keyboard.KEY_NEXT)
        {
            this.scrollDown();
        }
    }
    
    @Override
    public void guiMouseClicked(int mouseX, int mouseY, int mouseButton)
    {
        if (mouseButton == 0)
        {
            if (this.hoverObj.type == MouseInteractionType.NODE)
            {
                this.getPrint().addNode(this.hoverObj.node.setPosition(-this.getPrint().posX, -this.getPrint().posY));
            }
            
            if (this.hoverObj.type == MouseInteractionType.TEXT_FIELD)
            {
                if (this.searchInput.isFocused())
                {
                    this.searchInput.mouseClicked(mouseX, mouseY, mouseButton);
                }
                else
                {
                    this.setFocusTextField();
                }
            }
            else
            {
                this.setUnfocusTextField();
            }
        }
    }
    
    @Override
    public boolean isolateInput()
    {
        return this.searchInput.isFocused();
    }
    
    public void scrollUp()
    {
        this.listOffset += this.util.nodeHeight * 2;
    }
    
    public void scrollDown()
    {
        this.listOffset -= this.util.nodeHeight * 2;
    }
    
    public ArrayList<Node> getAvailableNodesList()
    {
        return this.cutNodeListToSearch(this.helper.getAvailableNodes(this.guiPrint.getParentGui()));
    }
    
    public ArrayList<Node> cutNodeListToSearch(ArrayList<Node> list)
    {
        if (list == null)
        {
            return new ArrayList<Node>();
        }
        
        String text = this.searchInput.getText().trim();
        
        if (!StringUtils.isNullOrEmpty(text))
        {
            ArrayList<Node> list2 = (ArrayList<Node>) list.clone();
            
            for (Node node : list2)
            {
                if (!NodeGroupsHelper.INSTANCE.isTextMatchingNode(node, text))
                {
                    list.remove(node);
                }
            }
        }
        
        return list;
    }
    
    public void setUnfocusTextField()
    {
        this.searchInput.setFocused(false);
    }
    
    public void setFocusTextField()
    {
        // Set it to be focused, otherwise it does not accept input
        this.searchInput.setFocused(true);
    }
    
    public void updateTextFieldSize()
    {
        this.searchInput.x = this.dimensions.x + 1;
        this.searchInput.y = this.dimensions.y + 1;
        this.searchInput.width = this.dimensions.w - 2;
        this.searchInput.height = this.util.nodeHeight - 2;
    }
    
    public void updateListRect()
    {
        this.listRect.x = this.dimensions.x;
        this.listRect.w = this.dimensions.w;
        this.listRect.y = this.searchInput.height;
        this.listRect.h = this.dimensions.h - this.listRect.y;
    }
}
