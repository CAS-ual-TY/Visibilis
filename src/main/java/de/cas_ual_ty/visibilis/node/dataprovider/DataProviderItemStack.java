package de.cas_ual_ty.visibilis.node.dataprovider;

import net.minecraft.command.CommandSource;
import net.minecraft.item.ItemStack;

public class DataProviderItemStack extends DataProvider
{
    protected ItemStack itemStack;
    
    public DataProviderItemStack(CommandSource commandSender, ItemStack itemStack)
    {
        super(commandSender);
        this.itemStack = itemStack;
    }
    
    public ItemStack getItemStack()
    {
        return this.itemStack;
    }
}
