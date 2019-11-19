package de.cas_ual_ty.visibilis.print.ui.component;

import java.util.ArrayList;

import org.lwjgl.glfw.GLFW;

import com.mojang.blaze3d.platform.GlStateManager;

import de.cas_ual_ty.visibilis.node.Node;
import de.cas_ual_ty.visibilis.node.NodeGroupsHelper;
import de.cas_ual_ty.visibilis.print.IPrintProvider;
import de.cas_ual_ty.visibilis.print.ui.UiBase;
import de.cas_ual_ty.visibilis.print.ui.util.MouseInteractionObject.MouseInteractionType;
import de.cas_ual_ty.visibilis.util.RenderUtility;
import de.cas_ual_ty.visibilis.util.RenderUtility.Rectangle;
import net.minecraft.client.gui.widget.TextFieldWidget;
import net.minecraft.util.StringUtils;

public class ComponentNodeList extends Component
{
    public Rectangle listRect;
    public Rectangle inputRect;
    
    public float zoom;
    public int listOffset;
    public TextFieldWidget searchInput;
    
    public boolean isSearchFieldFocused;
    
    public int keyPressed = -1;
    public int keyPressedDelay = 0;
    
    public ComponentNodeList(UiBase guiPrint, RenderUtility util, IPrintProvider helper)
    {
        super(guiPrint, util, helper);
        
        this.zoom = 0.5F;
        this.listOffset = 0;
        this.searchInput = new TextFieldWidget(this.util.fontRenderer, 0, 0, 0, 0, "");
        this.searchInput.setVisible(true);
        this.searchInput.setEnabled(true);
        this.listRect = Rectangle.fromXYWH(0, 0, 0, 0);
        this.inputRect = Rectangle.fromXYWH(0, 0, 0, 0);
        this.isSearchFieldFocused = false;
    }
    
    @Override
    public void guiInitGui()
    {
        this.updateInputRect();
        this.updateListRect();
        this.updateTextFieldSize();
    }
    
    @Override
    public void guiRender(int mouseX, int mouseY, float partialTicks)
    {
        float zoom = 0.5F;
        
        // Background
        this.dimensions.render(0.375F, 0.375F, 0.375F);
        
        // Reset hoverObj, then check if the mouse is over smth
        this.hoverObj.nothing();
        boolean mouseOnTextField = this.inputRect.isCoordInside(mouseX, mouseY);
        boolean mouseOnList = !mouseOnTextField && this.listRect.isCoordInside(mouseX, mouseY);
        
        GlStateManager.pushMatrix();
        GlStateManager.translatef(this.dimensions.x, this.dimensions.y, 0);
        //        RenderUtility.scissorStart(this.guiPrint.getScaledResolution(), this.listRect.x, this.listRect.y, this.listRect.w, this.listRect.h);
        RenderUtility.applyZoom(zoom); // TODO low: maybe make this dynamic
        
        int margin = 2;
        
        int x = 0;
        int y = (int) (this.listOffset + this.listRect.y / zoom + margin); // We start at the offset
        int w = this.util.nodeWidth;
        int h;
        
        // Draw all the nodes in the list
        for (Node node : this.getAvailableNodesList())
        {
            this.util.drawNode(node, x, y);
            
            h = this.util.getNodeTotalHeight(node); //height of the entire node
            
            // If the mouse is on a node:
            // - update hoverObj
            // - Immediately render hover rect
            if (mouseOnList && RenderUtility.isCoordInsideRect(mouseX, mouseY, this.dimensions.x, this.dimensions.y + y * zoom, w * zoom, h * zoom))
            {
                this.hoverObj.node(node);
                this.util.drawHoverRect(x, y, w, h);
            }
            
            // Now add the height of the node to the next node's position. +2 so there is a small margin between nodes
            y += h + margin;
        }
        
        // Remove the margin off of the last node again
        y -= margin + this.listOffset;
        
        int offset = this.inputRect.b;
        
        int topRect = this.listRect.t + offset;
        int botRect = (this.listRect.b - y) - offset; //TODO properly fit botRect. Right now you can scroll down until the bottom of the nodes list hits roughly the middle of the screen instead of the bottom - nodeHeight.
        
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
        
        //        RenderUtility.scissorEnd();
        GlStateManager.popMatrix();
        
        if (mouseOnTextField)
        {
            // This overrides any nodes the mouse might be on
            this.hoverObj.textField(this.searchInput);
        }
        
        this.searchInput.render(mouseX, mouseY, partialTicks);
    }
    
    @Override
    public void guiPostRender(int mouseX, int mouseY, float partialTicks)
    {
        if (this.hoverObj.type == MouseInteractionType.NODE)
        {
            this.util.drawNodeHoveringText(this.hoverObj.node, mouseX, mouseY);
        }
    }
    
    @Override
    public void guiTick()
    {
        if (!this.updateKeyPressedDelay())
        {
            return;
        }
        
        if (this.keyPressed == GLFW.GLFW_KEY_PAGE_UP)
        {
            this.scrollUp();
        }
        else if (this.keyPressed == GLFW.GLFW_KEY_PAGE_DOWN)
        {
            this.scrollDown();
        }
        
        super.guiTick();
    }
    
    @Override
    public boolean mouseClicked(double mouseX, double mouseY, int modifiers)
    {
        if (modifiers == 0)
        {
            if (this.hoverObj.type == MouseInteractionType.NODE)
            {
                this.getPrint().addNode(this.hoverObj.node.setPosition(-this.getPrint().posX, -this.getPrint().posY));
            }
            
            // If you had the text field selected and click on it again, dont deselect it
            if (this.hoverObj.type == MouseInteractionType.TEXT_FIELD)
            {
                if (this.searchInput.isFocused())
                {
                    return this.searchInput.mouseClicked(mouseX, mouseY, modifiers);
                }
                else
                {
                    this.setFocusTextField();
                    return true;
                }
            }
            else
            {
                this.setUnfocusTextField();
            }
        }
        
        return super.mouseClicked(mouseX, mouseY, modifiers);
    }
    
    @Override
    public boolean charTyped(char typedChar, int keyCode)
    {
        if (this.searchInput.isFocused())
        {
            if (keyCode == GLFW.GLFW_KEY_ENTER)
            {
                this.setUnfocusTextField();
                return true;
            }
            else
            {
                //If the text field is visible transfer all pressed keys to it.
                this.searchInput.charTyped(typedChar, keyCode);
                return true;
            }
        }
        
        return super.charTyped(typedChar, keyCode);
    }
    
    @Override
    public boolean keyPressed(int keyCode, int scanCode, int modifiers)
    {
        if (this.searchInput.isFocused())
        {
            if (keyCode == GLFW.GLFW_KEY_ENTER)
            {
                this.setUnfocusTextField();
                return true;
            }
            else
            {
                //If the text field is visible transfer all pressed keys to it.
                this.searchInput.keyPressed(keyCode, scanCode, modifiers);
                return true;
            }
        }
        
        this.updateKeyPressed(keyCode, 0);
        
        return super.keyPressed(keyCode, scanCode, modifiers);
    }
    
    @Override
    public boolean keyReleased(int keyCode, int scanCode, int modifiers)
    {
        this.updateKeyPressed(-1, 0);
        
        if (this.searchInput.isFocused())
        {
            if (keyCode == GLFW.GLFW_KEY_ENTER)
            {
                this.setUnfocusTextField();
                return true;
            }
            else
            {
                //If the text field is visible transfer all pressed keys to it.
                this.searchInput.keyReleased(keyCode, scanCode, modifiers);
                return true;
            }
        }
        
        return super.keyReleased(keyCode, scanCode, modifiers);
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
    
    public void updateKeyPressed(int keyCode, int delay)
    {
        this.keyPressed = keyCode;
        this.keyPressedDelay = delay;
    }
    
    public boolean updateKeyPressedDelay()
    {
        boolean ret = --this.keyPressedDelay <= 0;
        if (ret)
        {
            this.keyPressedDelay = 3;
        }
        return ret;
    }
    
    public ArrayList<Node> getAvailableNodesList()
    {
        return this.cutNodeListToSearch(this.helper.getAvailableNodes(this.guiPrint.getParentGui()));
    }
    
    /**
     * Remove all nodes that have no tags matching the current search text box in any way
     * @param list The nodes list
     * @return The nodes list containing only nodes matching the current search criteria
     */
    public ArrayList<Node> cutNodeListToSearch(ArrayList<Node> list)
    {
        if (list == null)
        {
            return new ArrayList<>();
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
        this.searchInput.setFocused2(false);
    }
    
    public void setFocusTextField()
    {
        this.searchInput.setFocused2(true);
    }
    
    public void updateInputRect()
    {
        this.inputRect.x = this.dimensions.x;
        this.inputRect.w = this.dimensions.w;
        this.inputRect.y = 0;
        this.inputRect.h = this.util.nodeHeight;
        this.inputRect.updateLRTB();
    }
    
    public void updateListRect()
    {
        this.listRect.x = this.dimensions.x;
        this.listRect.w = this.dimensions.w;
        this.listRect.y = this.inputRect.h;
        this.listRect.h = this.dimensions.h - this.listRect.y;
        this.listRect.updateLRTB();
    }
    
    public void updateTextFieldSize()
    {
        this.searchInput.x = this.inputRect.x + 1;
        this.searchInput.y = this.inputRect.y + 1;
        this.searchInput.setWidth(this.inputRect.w - 2);
        this.searchInput.setHeight(this.inputRect.h - 2);
    }
}
