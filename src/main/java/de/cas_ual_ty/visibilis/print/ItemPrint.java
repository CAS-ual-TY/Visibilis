package de.cas_ual_ty.visibilis.print;

import de.cas_ual_ty.visibilis.Visibilis;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumHand;

public class ItemPrint extends Item
{
    public void openGui(ItemStack itemStack, EnumHand hand)
    {
        Visibilis.proxy.openGuiPrint(this.getHelper(itemStack, hand));
    }
    
    public void openGui(EntityPlayer player, EnumHand hand)
    {
        this.openGui(player.getHeldItem(hand), hand);
    }
    
    public IPrintHelper getHelper(ItemStack itemStack, EnumHand hand)
    {
        return new PrintHelperItem(itemStack, hand);
    }
}
