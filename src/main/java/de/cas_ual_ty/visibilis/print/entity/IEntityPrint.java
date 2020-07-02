package de.cas_ual_ty.visibilis.print.entity;

import java.util.List;
import java.util.function.Function;

import de.cas_ual_ty.visibilis.Visibilis;
import de.cas_ual_ty.visibilis.node.NodeType;
import de.cas_ual_ty.visibilis.print.Print;
import de.cas_ual_ty.visibilis.print.capability.IPrintHolder;
import de.cas_ual_ty.visibilis.print.provider.DataProvider;
import de.cas_ual_ty.visibilis.print.provider.NodeListProvider;
import de.cas_ual_ty.visibilis.print.provider.NodeListProviderBase;
import de.cas_ual_ty.visibilis.print.provider.PrintProvider;
import de.cas_ual_ty.visibilis.util.VUtility;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Hand;
import net.minecraft.world.World;
import net.minecraftforge.fml.network.PacketDistributor;

public interface IEntityPrint extends IPrintHolder
{
    /**
     * On client side: calls {@link #doOpenGui()}.
     * On server side: Sends a packet to the client the given player which calls this method again (on client side).
     * 
     * @param player
     *            The player to open the Gui for
     * @return <b>true</b> if the Gui was opened
     */
    public default boolean openGui(PlayerEntity player)
    {
        World world = ((Entity)this).world;
        
        // Make sure they are in the same dimension, synching fetches entity from same world player is in
        // (because it takes the player's world and searches for the entity using its entity id).
        // Need custom PrintProvider otherwise which takes different dimensions into account
        if(player.world == world)
        {
            if(world.isRemote)
            {
                this.doOpenGui();
                return true;
            }
            else if(player instanceof ServerPlayerEntity)
            {
                Visibilis.channel.send(PacketDistributor.PLAYER.with(() -> (ServerPlayerEntity)player), new MessageSynchEntityToClient(this, this.synchVariables(), true));
                return true;
            }
        }
        
        return false;
    }
    
    public default void doOpenGui()
    {
        VUtility.openGuiForClient(this.getPrintProvider());
    }
    
    /**
     * Returns an instance of {@link PrintProvider} which is used to open the {@link de.cas_ual_ty.visibilis.print.GuiPrint} in {@link #openGui(PlayerEntity, ItemStack, Hand)}
     */
    public default PrintProvider getPrintProvider()
    {
        return new PrintProviderEntity(this.getNodeList(), (Entity)this);
    }
    
    public default List<NodeType<?>> getNodeTypeList()
    {
        return NodeListProviderBase.ALL_NODES;
    }
    
    public default NodeListProvider getNodeList()
    {
        return new NodeListProviderBase(this.getNodeTypeList());
    }
    
    public default Function<Print, DataProvider> createDataProvider()
    {
        return IEntityPrint.getDataFactory((Entity)this);
    }
    
    public default boolean executeEvent(String modId, String event)
    {
        return this.getPrint().executeEvent(modId, event, this.createDataProvider());
    }
    
    public default boolean validate(Print print)
    {
        return VUtility.validate(print, this.getNodeTypeList());
    }
    
    public default void synchToTrackers()
    {
        Visibilis.channel.send(PacketDistributor.TRACKING_ENTITY.with(() -> (Entity)this), new MessageSynchEntityToClient(this, this.synchVariables(), false));
    }
    
    public default boolean synchVariables()
    {
        return true;
    }
    
    public static Function<Print, DataProvider> getDataFactory(Entity entity)
    {
        return (print) -> new DataProvider(print, entity);
    }
}
