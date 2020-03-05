package de.cas_ual_ty.visibilis.print.item;

import de.cas_ual_ty.visibilis.print.GuiPrint;
import de.cas_ual_ty.visibilis.print.Print;
import de.cas_ual_ty.visibilis.print.capability.CapabilityProviderPrint;
import de.cas_ual_ty.visibilis.print.provider.DataProvider;
import de.cas_ual_ty.visibilis.print.provider.NodeListProvider;
import de.cas_ual_ty.visibilis.print.provider.NodeListProviderBase;
import de.cas_ual_ty.visibilis.print.provider.PrintProvider;
import de.cas_ual_ty.visibilis.util.VUtility;
import net.minecraft.command.CommandSource;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.EquipmentSlotType;
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
        if(player.world.isRemote)
        {
            VUtility.openGuiForClient(this.getPrintProvider(itemStack, hand == Hand.MAIN_HAND ? EquipmentSlotType.MAINHAND.getSlotIndex() : EquipmentSlotType.OFFHAND.getSlotIndex()));
            return true;
        }
        
        return false;
    }
    
    public boolean openGui(PlayerEntity player, Hand hand)
    {
        return this.openGui(player, player.getHeldItem(hand), hand);
    }
    
    /**
     * Returns an instance of {@link PrintProvider} which is used to open the {@link de.cas_ual_ty.visibilis.print.GuiPrint} in {@link #openGui(PlayerEntity, ItemStack, Hand)}
     */
    public PrintProvider getPrintProvider(ItemStack itemStack, int slot)
    {
        return new PrintProviderItem(this.getNodeList(itemStack, slot), itemStack, slot);
    }
    
    public NodeListProvider getNodeList(ItemStack itemStack, int slot)
    {
        return new NodeListProviderBase();
    }
    
    public Print getPrint(ItemStack itemStack)
    {
        return itemStack.getCapability(CapabilityProviderPrint.CAPABILITY_PRINT).orElseThrow(() -> new IllegalArgumentException("LazyOptional must not be empty (3)!"));
    }
    
    public void setPrintTag(ItemStack itemStack, CompoundNBT nbt)
    {
        Print print = this.getPrint(itemStack);
        print.overrideFromNBT(nbt);
    }
    
    // is this item editable via the "v edit" command?
    public boolean isEditable(ItemStack itemStack, CommandSource source)
    {
        return false;
    }
    
    public DataProvider createDataProvider(Entity entity, ItemStack itemStack)
    {
        return new DataProviderItem(entity, itemStack);
    }
    
    public boolean executeEvent(String modId, String event, Entity entity, ItemStack itemStack)
    {
        return this.getPrint(itemStack).executeEvent(event, this.createDataProvider(entity, itemStack));
    }
    
    public boolean validate(ItemStack itemStack, Print print)
    {
        return true;
    }
}
