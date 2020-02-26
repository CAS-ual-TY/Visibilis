package de.cas_ual_ty.visibilis.print.item;

import de.cas_ual_ty.visibilis.Visibilis;
import de.cas_ual_ty.visibilis.print.NodeListProvider;
import de.cas_ual_ty.visibilis.print.Print;
import de.cas_ual_ty.visibilis.print.PrintProvider;
import de.cas_ual_ty.visibilis.print.capability.CapabilityProviderPrint;
import de.cas_ual_ty.visibilis.util.VNBTUtility;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;

public class PrintProviderItem extends PrintProvider
{
    public ItemStack itemStack;
    public int slot;
    
    public PrintProviderItem(NodeListProvider nodeListProvider, ItemStack itemStack, int slot)
    {
        super(nodeListProvider);
        this.itemStack = itemStack;
        this.slot = slot;
    }
    
    @Override
    public void init()
    {
        this.undoList.setFirst(this.itemStack.getCapability(CapabilityProviderPrint.CAPABILITY_PRINT).orElse(new Print()));
        super.init();
    }
    
    @Override
    public void onGuiClose()
    {
        this.synchToServer(VNBTUtility.savePrintToNBT(this.getPrint()));
        super.onGuiClose();
    }
    
    public void synchToServer(CompoundNBT nbt)
    {
        Visibilis.channel.sendToServer(new MessageItem(this.slot, this.itemStack));
    }
}
