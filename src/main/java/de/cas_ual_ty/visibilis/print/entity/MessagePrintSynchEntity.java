package de.cas_ual_ty.visibilis.print.entity;

import java.util.function.Supplier;

import de.cas_ual_ty.visibilis.event.EntityPrintValidationEvent;
import de.cas_ual_ty.visibilis.print.Print;
import de.cas_ual_ty.visibilis.print.capability.CapabilityProviderPrint;
import de.cas_ual_ty.visibilis.util.VNBTUtility;
import de.cas_ual_ty.visibilis.util.VUtility;
import net.minecraft.entity.Entity;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.PacketBuffer;
import net.minecraft.world.World;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.network.NetworkEvent;
import net.minecraftforge.fml.network.NetworkEvent.Context;

public class MessagePrintSynchEntity
{
    public final int entityId;
    public final CompoundNBT nbt;
    
    public MessagePrintSynchEntity(Entity entity)
    {
        this(entity.getEntityId(), VNBTUtility.savePrintToNBT(entity.getCapability(CapabilityProviderPrint.CAPABILITY_PRINT).orElseThrow(() -> new IllegalArgumentException("LazyOptional must not be empty (8)!"))));
    }
    
    public MessagePrintSynchEntity(int entityId, CompoundNBT nbt)
    {
        this.nbt = nbt;
        this.entityId = entityId;
    }
    
    public static void encode(MessagePrintSynchEntity msg, PacketBuffer buf)
    {
        buf.writeInt(msg.entityId);
        buf.writeCompoundTag(msg.nbt);
    }
    
    public static MessagePrintSynchEntity decode(PacketBuffer buf)
    {
        return new MessagePrintSynchEntity(buf.readInt(), buf.readCompoundTag());
    }
    
    public static void handle(MessagePrintSynchEntity msg, Supplier<NetworkEvent.Context> ctx)
    {
        Context context = ctx.get();
        
        context.enqueueWork(() ->
        {
            World world = VUtility.getWorld(context);
            Entity entity = world.getEntityByID(msg.entityId);
            
            if(entity != null)
            {
                Print print = entity.getCapability(CapabilityProviderPrint.CAPABILITY_PRINT).orElse(new Print());
                
                if(entity instanceof EntityPrint && !((EntityPrint)entity).validate(print))
                {
                    return;
                }
                if(!MinecraftForge.EVENT_BUS.post(new EntityPrintValidationEvent(entity)))
                {
                    print.overrideFromNBT(msg.nbt);
                }
            }
        });
        
        ctx.get().setPacketHandled(true);
    }
}
