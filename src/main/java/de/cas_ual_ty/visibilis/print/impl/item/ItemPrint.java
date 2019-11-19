package de.cas_ual_ty.visibilis.print.impl.item;

import de.cas_ual_ty.visibilis.Visibilis;
import de.cas_ual_ty.visibilis.print.Print;
import de.cas_ual_ty.visibilis.print.impl.GuiPrint;
import de.cas_ual_ty.visibilis.print.impl.IPrintProvider;
import de.cas_ual_ty.visibilis.util.NBTUtility;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.Hand;

public class ItemPrint extends Item
{
    public ItemPrint(Properties properties)
    {
        super(properties);
    }
    
    @Override
    public boolean updateItemStackNBT(CompoundNBT nbt)
    {
        return true;
    }
    
    /**
     * Opens the {@link GuiPrint} for the given player
     * 
     * @param player
     *            The player to open the Gui for
     * @param itemStack
     *            The itemStack to edit the {@link Print} of (item must be in hand)
     * @param hand
     *            The hand the given itemStack is in
     * @return <b>true</b> if the Gui was opened
     */
    public boolean openGui(PlayerEntity player, ItemStack itemStack, Hand hand)
    {
        if (player.world.isRemote)
        {
            Visibilis.proxy.openGuiPrint(this.getHelper(itemStack, hand));
            return true;
        }
        
        return false;
    }
    
    public boolean openGui(PlayerEntity player, Hand hand)
    {
        return this.openGui(player, player.getHeldItem(hand), hand);
    }
    
    /**
     * Returns an instance of {@link IPrintProvider} which is used to open the {@link de.cas_ual_ty.visibilis.print.impl.GuiPrint} in {@link #openGui(PlayerEntity, ItemStack, Hand)}
     */
    public IPrintProvider getHelper(ItemStack itemStack, Hand hand)
    {
        return new PrintProviderItem(itemStack, hand);
    }
    
    public Print getPrint(ItemStack itemStack)
    {
        if (itemStack.hasTag())
        {
            CompoundNBT nbt0 = itemStack.getTag();
            
            if (nbt0.hasUniqueId(Visibilis.MOD_ID))
            {
                CompoundNBT nbt = nbt0.getCompound(Visibilis.MOD_ID);
                return NBTUtility.loadPrintFromNBT(nbt);
            }
        }
        
        return null;
    }
}
