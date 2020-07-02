package de.cas_ual_ty.visibilis.print.item;

import java.util.function.Function;

import de.cas_ual_ty.visibilis.print.GuiPrint;
import de.cas_ual_ty.visibilis.print.Print;
import de.cas_ual_ty.visibilis.print.capability.CapabilityProviderPrintHolder;
import de.cas_ual_ty.visibilis.print.capability.IPrintHolder;
import de.cas_ual_ty.visibilis.print.provider.DataKey;
import de.cas_ual_ty.visibilis.print.provider.DataProvider;
import de.cas_ual_ty.visibilis.print.provider.NodeListProvider;
import de.cas_ual_ty.visibilis.print.provider.NodeListProviderBase;
import de.cas_ual_ty.visibilis.print.provider.PrintProvider;
import de.cas_ual_ty.visibilis.util.VUtility;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Hand;

public interface IItemPrint
{
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
    public default boolean openGui(PlayerEntity player, ItemStack itemStack, Hand hand)
    {
        if(player.world.isRemote)
        {
            this.doOpenGui(itemStack, hand == Hand.MAIN_HAND ? player.inventory.currentItem : EquipmentSlotType.OFFHAND.getSlotIndex());
            return true;
        }
        
        return false;
    }
    
    public default boolean openGui(PlayerEntity player, Hand hand)
    {
        return this.openGui(player, player.getHeldItem(hand), hand);
    }
    
    public default void doOpenGui(ItemStack itemStack, int slot)
    {
        VUtility.openGuiForClient(this.getPrintProvider(itemStack, slot));
    }
    
    /**
     * Returns an instance of {@link PrintProvider} which is used to open the {@link de.cas_ual_ty.visibilis.print.GuiPrint} in {@link #openGui(PlayerEntity, ItemStack, Hand)}
     */
    public default PrintProvider getPrintProvider(ItemStack itemStack, int slot)
    {
        return new PrintProviderItem(this.getNodeList(itemStack), itemStack, slot);
    }
    
    public default NodeListProvider getNodeList(ItemStack itemStack)
    {
        return new NodeListProviderBase();
    }
    
    public default Function<Print, DataProvider> createDataProvider(Entity entity, ItemStack itemStack)
    {
        return IItemPrint.getDataFactory(entity, itemStack);
    }
    
    public default boolean executeEvent(String modId, String event, Entity entity, ItemStack itemStack)
    {
        return this.getPrint(itemStack).executeEvent(modId, event, this.createDataProvider(entity, itemStack));
    }
    
    public default boolean validate(ItemStack itemStack, Print print)
    {
        return this.getNodeList(itemStack).validate(print);
    }
    
    public default Print getPrint(ItemStack itemStack)
    {
        return this.getPrintHolder(itemStack).getPrint();
    }
    
    public default void setPrint(ItemStack itemStack, Print print)
    {
        this.getPrintHolder(itemStack).setPrint(print);
    }
    
    public default IPrintHolder getPrintHolder(ItemStack itemStack)
    {
        return itemStack.getCapability(CapabilityProviderPrintHolder.CAPABILITY_PRINT_HOLDER).orElseThrow(() -> new IllegalArgumentException("LazyOptional must not be empty!"));
    }
    
    public static Function<Print, DataProvider> getDataFactory(Entity entity, ItemStack itemStack)
    {
        return (print) -> new DataProvider(print, entity).addData(DataKey.KEY_ITEM_STACK, itemStack);
    }
}
