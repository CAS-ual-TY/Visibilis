package de.cas_ual_ty.visibilis.print.item;

import de.cas_ual_ty.visibilis.print.entity.DataProviderEntity;
import net.minecraft.command.CommandSource;
import net.minecraft.entity.Entity;
import net.minecraft.item.ItemStack;

public class DataProviderItem extends DataProviderEntity
{
    protected ItemStack itemStack;
    
    public DataProviderItem(Entity entity, ItemStack itemStack)
    {
        super(entity);
        this.itemStack = itemStack;
    }
    
    public DataProviderItem(CommandSource source, ItemStack itemStack)
    {
        super(source);
        this.itemStack = itemStack;
    }
    
    public ItemStack getItemStack()
    {
        return this.itemStack;
    }
}
