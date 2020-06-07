package de.cas_ual_ty.visibilis.print.entity;

import java.util.function.Supplier;

import de.cas_ual_ty.visibilis.print.capability.CapabilityProviderPrintHolder;
import de.cas_ual_ty.visibilis.util.VNBTUtility;
import de.cas_ual_ty.visibilis.util.VUtility;
import net.minecraft.entity.Entity;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.PacketBuffer;
import net.minecraft.world.World;
import net.minecraftforge.fml.network.NetworkEvent;
import net.minecraftforge.fml.network.NetworkEvent.Context;

public class MessageSynchEntityToClient
{
    public final int entityId;
    public final CompoundNBT nbt;
    public final boolean openGuiForClient;
    
    public MessageSynchEntityToClient(Entity entity)
    {
        this(entity.getEntityId(), VNBTUtility.savePrintToNBT(entity.getCapability(CapabilityProviderPrintHolder.CAPABILITY_PRINT_HOLDER).orElseThrow(() -> new IllegalArgumentException("LazyOptional must not be empty!")).getPrint()), false);
    }
    
    public MessageSynchEntityToClient(IEntityPrint entity, boolean openGuiForClient)
    {
        this(((Entity)entity).getEntityId(), VNBTUtility.savePrintToNBT(entity.getPrint()), openGuiForClient);
    }
    
    public MessageSynchEntityToClient(int entityId, CompoundNBT nbt, boolean openForClient)
    {
        this.nbt = nbt;
        this.entityId = entityId;
        this.openGuiForClient = openForClient;
    }
    
    public static void encode(MessageSynchEntityToClient msg, PacketBuffer buf)
    {
        buf.writeInt(msg.entityId);
        buf.writeCompoundTag(msg.nbt);
        buf.writeBoolean(msg.openGuiForClient);
    }
    
    public static MessageSynchEntityToClient decode(PacketBuffer buf)
    {
        return new MessageSynchEntityToClient(buf.readInt(), buf.readCompoundTag(), buf.readBoolean());
    }
    
    public static void handle(MessageSynchEntityToClient msg, Supplier<NetworkEvent.Context> ctx)
    {
        Context context = ctx.get();
        
        context.enqueueWork(() ->
        {
            World world = VUtility.getWorld(context);
            Entity entity = world.getEntityByID(msg.entityId);
            entity.getCapability(CapabilityProviderPrintHolder.CAPABILITY_PRINT_HOLDER).orElseThrow(() -> new IllegalArgumentException("LazyOptional must not be empty!")).setPrint(msg.nbt);
            
            if(msg.openGuiForClient && entity instanceof IEntityPrint)
            {
                ((IEntityPrint)entity).openGui(VUtility.getPlayer(context));
            }
        });
        
        ctx.get().setPacketHandled(true);
    }
}
