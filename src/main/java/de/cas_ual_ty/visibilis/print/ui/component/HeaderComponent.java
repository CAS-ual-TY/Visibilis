package de.cas_ual_ty.visibilis.print.ui.component;

import de.cas_ual_ty.visibilis.print.ui.UiBase;

public class HeaderComponent extends Component
{
    public static final String LANG_UNDO = "visibilis.undo";
    public static final String LANG_REDO = "visibilis.redo";
    
    public HeaderComponent(UiBase guiPrint)
    {
        super(guiPrint);
    }
    
    @Override
    public void guiRender(int mouseX, int mouseY, float partialTicks)
    {
        this.dimensions.render(0, 0, 0);
    }
    
    @Override
    public boolean mouseClicked(double mouseX, double mouseY, int modifiers)
    {
        return super.mouseClicked(mouseX, mouseY, modifiers);
    }
}
