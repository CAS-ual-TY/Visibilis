package de.cas_ual_ty.visibilis.print.ui.component;

import de.cas_ual_ty.visibilis.print.ui.UiBase;
import net.minecraft.client.gui.widget.button.AbstractButton;
import net.minecraft.client.resources.I18n;

public class ComponentHeader extends Component
{
    public static final String LANG_UNDO = "visibilis.undo";
    public static final String LANG_REDO = "visibilis.redo";
    
    public AbstractButton undo;
    public AbstractButton redo;
    
    public ComponentHeader(UiBase guiPrint)
    {
        super(guiPrint);
        
        int w = 19 * 3;
        int h = 19;
        
        this.undo = new AbstractButton(0, 0, w, h, I18n.format(ComponentHeader.LANG_UNDO))
        {
            @Override
            public void onPress()
            {
                ComponentHeader.this.undo();
            }
        };
        
        this.redo = new AbstractButton(0 + w, 0, w, h, I18n.format(ComponentHeader.LANG_REDO))
        {
            @Override
            public void onPress()
            {
                ComponentHeader.this.redo();
            }
        };
    }
    
    @Override
    public void guiRender(int mouseX, int mouseY, float partialTicks)
    {
        this.dimensions.render(0, 0, 0);
        
        this.undo.render(mouseX, mouseY, partialTicks);
        this.redo.render(mouseX, mouseY, partialTicks);
    }
    
    @Override
    public void guiPostRender(int mouseX, int mouseY, float partialTicks)
    {
        this.undo.active = this.getProvider().canUndo();
        this.redo.active = this.getProvider().canRedo();
        
        if (this.undo.isMouseOver(mouseX, mouseY))
        {
            this.getParentGui().renderTooltip(I18n.format(ComponentHeader.LANG_UNDO), mouseX, mouseY);
        }
        else if (this.redo.isMouseOver(mouseX, mouseY))
        {
            this.getParentGui().renderTooltip(I18n.format(ComponentHeader.LANG_REDO), mouseX, mouseY);
        }
    }
    
    @Override
    public boolean mouseClicked(double mouseX, double mouseY, int modifiers)
    {
        this.undo.mouseClicked(mouseX, mouseY, modifiers);
        this.redo.mouseClicked(mouseX, mouseY, modifiers);
        return super.mouseClicked(mouseX, mouseY, modifiers);
    }
    
    public void undo()
    {
        this.getProvider().undo();
    }
    
    public void redo()
    {
        this.getProvider().redo();
    }
}
