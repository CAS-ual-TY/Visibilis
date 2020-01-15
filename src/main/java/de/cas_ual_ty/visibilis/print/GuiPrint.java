package de.cas_ual_ty.visibilis.print;

import de.cas_ual_ty.visibilis.print.ui.UiBase;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.util.text.ITextComponent;

public class GuiPrint extends Screen
{
    private PrintProvider provider;
    private UiBase uiLogic;
    
    public GuiPrint(ITextComponent title, PrintProvider provider)
    {
        super(title);
        this.provider = provider;
        this.getProvider().init();
        this.uiLogic = new UiBase(this, this.getProvider());
        this.getProvider().onGuiOpen();
    }
    
    public PrintProvider getProvider()
    {
        return this.provider;
    }
    
    public UiBase getUiLogic()
    {
        return this.uiLogic;
    }
    
    @Override
    protected void init()
    {
        this.getUiLogic().guiInit();
        super.init();
    }
    
    @Override
    public void onClose()
    {
        this.getUiLogic().guiOnClose();
        this.getProvider().onGuiClose();
        super.onClose();
    }
    
    @Override
    public void render(int mouseX, int mouseY, float partialTicks)
    {
        this.getUiLogic().guiDrawScreen(mouseX, mouseY, partialTicks);
        super.render(mouseX, mouseY, partialTicks);
    }
    
    @Override
    public void tick()
    {
        this.getUiLogic().guiTick();
        super.tick();
    }
    
    // --- IGuiEventListener START ---
    
    @Override
    public void mouseMoved(double mouseX, double mouseY)
    {
        this.getUiLogic().mouseMoved(mouseX, mouseY);
        super.mouseMoved(mouseX, mouseY);
    }
    
    @Override
    public boolean mouseClicked(double mouseX, double mouseY, int modifiers)
    {
        this.getUiLogic().mouseClicked(mouseX, mouseY, modifiers);
        return super.mouseClicked(mouseX, mouseY, modifiers);
    }
    
    @Override
    public boolean mouseReleased(double mouseX, double mouseY, int modifiers)
    {
        this.getUiLogic().mouseReleased(mouseX, mouseY, modifiers);
        return super.mouseReleased(mouseX, mouseY, modifiers);
    }
    
    @Override
    public boolean mouseDragged(double mouseX, double mouseY, int modifiers, double deltaX, double deltaY)
    {
        this.getUiLogic().mouseDragged(mouseX, mouseY, modifiers, deltaX, deltaY);
        return super.mouseDragged(mouseX, mouseY, modifiers, deltaX, deltaY);
    }
    
    @Override
    public boolean mouseScrolled(double mouseX, double mouseY, double amountScrolled)
    {
        this.getUiLogic().mouseScrolled(mouseX, mouseY, amountScrolled);
        return super.mouseScrolled(mouseX, mouseY, amountScrolled);
    }
    
    @Override
    public boolean keyPressed(int keyCode, int scanCode, int modifiers)
    {
        this.getUiLogic().keyPressed(keyCode, scanCode, modifiers);
        return super.keyPressed(keyCode, scanCode, modifiers);
    }
    
    @Override
    public boolean keyReleased(int keyCode, int scanCode, int modifiers)
    {
        this.getUiLogic().keyReleased(keyCode, scanCode, modifiers);
        return super.keyReleased(keyCode, scanCode, modifiers);
    }
    
    @Override
    public boolean charTyped(char typedChar, int keyCode)
    {
        this.getUiLogic().charTyped(typedChar, keyCode);
        return super.charTyped(typedChar, keyCode);
    }
    
    // --- END ---
}
