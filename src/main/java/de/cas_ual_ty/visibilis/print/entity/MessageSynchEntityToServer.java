package de.cas_ual_ty.visibilis.print.entity;

import java.util.function.Supplier;

import de.cas_ual_ty.visibilis.event.EntityPrintValidationEvent;
import de.cas_ual_ty.visibilis.print.Print;
import de.cas_ual_ty.visibilis.print.capability.CapabilityProviderPrintHolder;
import de.cas_ual_ty.visibilis.print.capability.IPrintHolder;
import de.cas_ual_ty.visibilis.util.VNBTUtility;
import de.cas_ual_ty.visibilis.util.VUtility;
import net.minecraft.entity.Entity;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.PacketBuffer;
import net.minecraft.world.World;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.network.NetworkEvent;
import net.minecraftforge.fml.network.NetworkEvent.Context;

public class MessageSynchEntityToServer
{
    public final int entityId;
    public final CompoundNBT nbt;
    
    public MessageSynchEntityToServer(Entity entity, Print print)
    {
        this(entity.getEntityId(), VNBTUtility.savePrintToNBT(print, false));
    }
    
    public MessageSynchEntityToServer(Entity entity, CompoundNBT nbt)
    {
        this(entity.getEntityId(), nbt);
    }
    
    public MessageSynchEntityToServer(int entityId, CompoundNBT nbt)
    {
        this.nbt = nbt;
        this.entityId = entityId;
    }
    
    public static void encode(MessageSynchEntityToServer msg, PacketBuffer buf)
    {
        buf.writeInt(msg.entityId);
        buf.writeCompoundTag(msg.nbt);
    }
    
    public static MessageSynchEntityToServer decode(PacketBuffer buf)
    {
        return new MessageSynchEntityToServer(buf.readInt(), buf.readCompoundTag());
    }
    
    public static void handle(MessageSynchEntityToServer msg, Supplier<NetworkEvent.Context> ctx)
    {
        Context context = ctx.get();
        
        context.enqueueWork(() ->
        {
            World world = VUtility.getWorld(context);
            Entity entity = world.getEntityByID(msg.entityId);
            
            if(entity != null)
            {
                Print print = VNBTUtility.loadPrintFromNBT(msg.nbt, false);
                
                if(entity instanceof IEntityPrint && !((IEntityPrint)entity).validate(print))
                {
                    return;
                }
                if(!MinecraftForge.EVENT_BUS.post(new EntityPrintValidationEvent(entity)))
                {
                    IPrintHolder holder = entity.getCapability(CapabilityProviderPrintHolder.CAPABILITY_PRINT_HOLDER).orElseThrow(() -> new IllegalArgumentException("LazyOptional must not be empty!"));
                    holder.setPrint(print);
                }
            }
        });
        
        ctx.get().setPacketHandled(true);
    }
}
