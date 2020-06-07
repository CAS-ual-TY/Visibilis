package de.cas_ual_ty.visibilis.print.item;

import de.cas_ual_ty.visibilis.Visibilis;
import de.cas_ual_ty.visibilis.print.provider.NodeListProvider;
import de.cas_ual_ty.visibilis.print.provider.PrintProviderCapability;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;

public class PrintProviderItem extends PrintProviderCapability
{
    public ItemStack itemStack;
    public int slot;
    
    public PrintProviderItem(NodeListProvider nodeListProvider, ItemStack itemStack, int slot)
    {
        super(nodeListProvider, itemStack);
        this.itemStack = itemStack;
        this.slot = slot;
    }
    
    @Override
    public void synchToServer(CompoundNBT nbt)
    {
        Visibilis.channel.sendToServer(new MessagePrintSynchItemToServer(this.slot, this.getPrint()));
    }
}
