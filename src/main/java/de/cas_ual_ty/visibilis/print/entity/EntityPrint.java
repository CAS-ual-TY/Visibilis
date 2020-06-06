package de.cas_ual_ty.visibilis.print.entity;

import java.util.function.Function;

import de.cas_ual_ty.visibilis.Visibilis;
import de.cas_ual_ty.visibilis.print.Print;
import de.cas_ual_ty.visibilis.print.capability.CapabilityProviderPrint;
import de.cas_ual_ty.visibilis.print.provider.DataProvider;
import de.cas_ual_ty.visibilis.print.provider.NodeListProvider;
import de.cas_ual_ty.visibilis.print.provider.NodeListProviderBase;
import de.cas_ual_ty.visibilis.print.provider.PrintProvider;
import de.cas_ual_ty.visibilis.util.VUtility;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.Direction;
import net.minecraft.util.Hand;
import net.minecraft.world.World;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.fml.network.PacketDistributor;

public abstract class EntityPrint extends Entity
{
    protected Print print;
    protected LazyOptional<Print> printOptional;
    
    public EntityPrint(EntityType<?> entityTypeIn, World worldIn)
    {
        super(entityTypeIn, worldIn);
        this.print = new Print();
        this.printOptional = LazyOptional.of(() -> this.getPrint());
    }
    
    /**
     * On client side: calls {@link #doOpenGui()}.
     * On server side: Sends a packet to the client the given player which calls this method again (on client side).
     * 
     * @param player
     *            The player to open the Gui for
     * @return <b>true</b> if the Gui was opened
     */
    public boolean openGui(PlayerEntity player)
    {
        // Make sure they are in the same dimension, synching fetches entity from same world player is in
        // (because it takes the player's world and searches for the entity using its entity id).
        // Need custom PrintProvider otherwise which takes different dimensions into account
        if(player.world == this.world)
        {
            if(this.world.isRemote)
            {
                this.doOpenGui();
                return true;
            }
            else if(player instanceof ServerPlayerEntity)
            {
                Visibilis.channel.send(PacketDistributor.PLAYER.with(() -> (ServerPlayerEntity)player), new MessageSynchEntityToClient(this, true));
                return true;
            }
        }
        
        return false;
    }
    
    public void doOpenGui()
    {
        VUtility.openGuiForClient(this.getPrintProvider());
    }
    
    public void synchToTrackers()
    {
        Visibilis.channel.send(PacketDistributor.TRACKING_ENTITY.with(() -> this), new MessageSynchEntityToClient(this, false));
    }
    
    /**
     * Returns an instance of {@link PrintProvider} which is used to open the {@link de.cas_ual_ty.visibilis.print.GuiPrint} in {@link #openGui(PlayerEntity, ItemStack, Hand)}
     */
    public PrintProvider getPrintProvider()
    {
        return new PrintProviderEntity(this.getNodeList(), this);
    }
    
    public NodeListProvider getNodeList()
    {
        return new NodeListProviderBase();
    }
    
    public Print getPrint()
    {
        return this.print;
    }
    
    @Override
    public <T> LazyOptional<T> getCapability(Capability<T> cap, Direction side)
    {
        return cap == CapabilityProviderPrint.CAPABILITY_PRINT ? CapabilityProviderPrint.CAPABILITY_PRINT.orEmpty(cap, this.printOptional) : super.getCapability(cap, side);
    }
    
    public void setPrintTag(CompoundNBT nbt)
    {
        Print print = this.getPrint();
        print.overrideFromNBT(nbt);
    }
    
    public Function<Print, DataProvider> createDataProvider()
    {
        return EntityPrint.getDataFactory(this);
    }
    
    public boolean executeEvent(String modId, String event)
    {
        return this.getPrint().executeEvent(modId, event, this.createDataProvider());
    }
    
    public boolean validate(Print print)
    {
        return true;
    }
    
    @Override
    protected void readAdditional(CompoundNBT compound)
    {
        this.getPrint().readFromNBT(compound);
    }
    
    @Override
    protected void writeAdditional(CompoundNBT compound)
    {
        this.getPrint().writeToNBT(compound);
    }
    
    public static Function<Print, DataProvider> getDataFactory(Entity entity)
    {
        return (print) -> new DataProvider(print, entity);
    }
}
