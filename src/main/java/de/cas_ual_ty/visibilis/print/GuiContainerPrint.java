package de.cas_ual_ty.visibilis.print;

import de.cas_ual_ty.visibilis.print.provider.PrintProvider;
import de.cas_ual_ty.visibilis.print.ui.UiBase;
import net.minecraft.client.gui.screen.inventory.ContainerScreen;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.container.Container;
import net.minecraft.util.text.ITextComponent;

public abstract class GuiContainerPrint<A extends Container> extends ContainerScreen<A>
{
    public final PrintProvider provider;
    public UiBase uiLogic;
    
    public GuiContainerPrint(A container, PlayerInventory inventory, ITextComponent title, PrintProvider provider)
    {
        super(container, inventory, title);
        this.provider = provider;
        this.uiLogic = new UiBase(this, this.provider);
        
        this.provider.onGuiOpen();
    }
    
    @Override
    public void init()
    {
        this.uiLogic.guiInit();
        super.init();
    }
    
    @Override
    public void onClose()
    {
        this.uiLogic.guiOnClose();
        this.provider.onGuiClose();
        super.onClose();
    }
    
    @Override
    public void render(int mouseX, int mouseY, float partialTicks)
    {
        this.uiLogic.guiDrawScreen(mouseX, mouseY, partialTicks);
        super.render(mouseX, mouseY, partialTicks);
    }
    
    @Override
    public void tick()
    {
        this.uiLogic.guiTick();
        super.tick();
    }
    
    // --- IGuiEventListener START ---
    
    @Override
    public void mouseMoved(double mouseX, double mouseY)
    {
        this.uiLogic.mouseMoved(mouseX, mouseY);
        super.mouseMoved(mouseX, mouseY);
    }
    
    @Override
    public boolean mouseClicked(double mouseX, double mouseY, int modifiers)
    {
        this.uiLogic.mouseClicked(mouseX, mouseY, modifiers);
        return super.mouseClicked(mouseX, mouseY, modifiers);
    }
    
    @Override
    public boolean mouseReleased(double mouseX, double mouseY, int modifiers)
    {
        this.uiLogic.mouseReleased(mouseX, mouseY, modifiers);
        return super.mouseReleased(mouseX, mouseY, modifiers);
    }
    
    @Override
    public boolean mouseDragged(double mouseX, double mouseY, int modifiers, double deltaX, double deltaY)
    {
        this.uiLogic.mouseDragged(mouseX, mouseY, modifiers, deltaX, deltaY);
        return super.mouseDragged(mouseX, mouseY, modifiers, deltaX, deltaY);
    }
    
    @Override
    public boolean mouseScrolled(double mouseX, double mouseY, double amountScrolled)
    {
        this.uiLogic.mouseScrolled(mouseX, mouseY, amountScrolled);
        return super.mouseScrolled(mouseX, mouseY, amountScrolled);
    }
    
    @Override
    public boolean keyPressed(int keyCode, int scanCode, int modifiers)
    {
        this.uiLogic.keyPressed(keyCode, scanCode, modifiers);
        return super.keyPressed(keyCode, scanCode, modifiers);
    }
    
    @Override
    public boolean keyReleased(int keyCode, int scanCode, int modifiers)
    {
        this.uiLogic.keyReleased(keyCode, scanCode, modifiers);
        return super.keyReleased(keyCode, scanCode, modifiers);
    }
    
    @Override
    public boolean charTyped(char typedChar, int keyCode)
    {
        this.uiLogic.charTyped(typedChar, keyCode);
        return super.charTyped(typedChar, keyCode);
    }
    
    // --- END ---
}
