package de.cas_ual_ty.visibilis.print.item;

import de.cas_ual_ty.visibilis.Visibilis;
import de.cas_ual_ty.visibilis.print.IPrintProvider;
import de.cas_ual_ty.visibilis.print.Print;
import de.cas_ual_ty.visibilis.util.NBTUtility;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumHand;

public class ItemPrint extends Item
{
    /**
     * Opens the {@link GuiPrintOld} for the given player
     * 
     * @param player
     *            The player to open the Gui for
     * @param itemStack
     *            The itemStack to edit the {@link Print} of (item must be in hand)
     * @param hand
     *            The hand the given itemStack is in
     * @return <b>true</b> if the Gui was opened
     */
    public boolean openGui(EntityPlayer player, ItemStack itemStack, EnumHand hand)
    {
        if (player.world.isRemote)
        {
            Visibilis.proxy.openGuiPrint(this.getHelper(itemStack, hand));
            return true;
        }
        
        return false;
    }
    
    public boolean openGui(EntityPlayer player, EnumHand hand)
    {
        return this.openGui(player, player.getHeldItem(hand), hand);
    }
    
    /**
     * Returns an instance of {@link IPrintProvider} which is used to open the {@link GuiPrintOld} in {@link #openGui(EntityPlayer, ItemStack, EnumHand)}
     */
    public IPrintProvider getHelper(ItemStack itemStack, EnumHand hand)
    {
        return new PrintHelperItem(itemStack, hand);
    }
    
    public Print getPrint(ItemStack itemStack)
    {
        if (itemStack.hasTagCompound())
        {
            NBTTagCompound nbt0 = itemStack.getTagCompound();
            
            if (nbt0.hasKey(Visibilis.MOD_ID))
            {
                NBTTagCompound nbt = nbt0.getCompoundTag(Visibilis.MOD_ID);
                return NBTUtility.loadPrintFromNBT(nbt);
            }
        }
        
        return null;
    }
}
