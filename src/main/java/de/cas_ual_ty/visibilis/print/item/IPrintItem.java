package de.cas_ual_ty.visibilis.print.item;

import java.util.List;
import java.util.function.Function;

import de.cas_ual_ty.visibilis.node.NodeType;
import de.cas_ual_ty.visibilis.print.Print;
import de.cas_ual_ty.visibilis.print.PrintScreen;
import de.cas_ual_ty.visibilis.print.capability.IPrintHolder;
import de.cas_ual_ty.visibilis.print.capability.PrintHolderCapabilityProvider;
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

public interface IPrintItem
{
    /**
     * Opens the {@link PrintScreen} for the given player
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
     * Returns an instance of {@link PrintProvider} which is used to open the {@link de.cas_ual_ty.visibilis.print.PrintScreen} in {@link #openGui(PlayerEntity, ItemStack, Hand)}
     */
    public default PrintProvider getPrintProvider(ItemStack itemStack, int slot)
    {
        return new ItemPrintProvider(this.getNodeList(itemStack), itemStack, slot);
    }
    
    public default List<NodeType<?>> getNodeTypeList(ItemStack itemStack)
    {
        return NodeListProviderBase.ALL_NODES;
    }
    
    public default NodeListProvider getNodeList(ItemStack itemStack)
    {
        return new NodeListProviderBase(this.getNodeTypeList(itemStack));
    }
    
    public default Function<Print, DataProvider> createDataProvider(Entity entity, ItemStack itemStack)
    {
        return IPrintItem.getDataFactory(entity, itemStack);
    }
    
    public default boolean executeEvent(String modId, String event, Entity entity, ItemStack itemStack)
    {
        return this.getPrint(itemStack).executeEvent(modId, event, this.createDataProvider(entity, itemStack));
    }
    
    public default boolean validate(ItemStack itemStack, Print print)
    {
        return VUtility.validate(print, this.getNodeTypeList(itemStack));
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
        return itemStack.getCapability(PrintHolderCapabilityProvider.CAPABILITY_PRINT_HOLDER).orElseThrow(() -> new IllegalArgumentException("LazyOptional must not be empty!"));
    }
    
    public static Function<Print, DataProvider> getDataFactory(Entity entity, ItemStack itemStack)
    {
        return (print) -> new DataProvider(print, entity).addData(DataKey.KEY_ITEM_STACK, itemStack);
    }
}
