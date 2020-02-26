package de.cas_ual_ty.visibilis.print.entity;

import java.util.function.Supplier;

import de.cas_ual_ty.visibilis.event.EntityPrintValidationEvent;
import de.cas_ual_ty.visibilis.print.Print;
import de.cas_ual_ty.visibilis.print.capability.CapabilityProviderPrint;
import de.cas_ual_ty.visibilis.util.VNBTUtility;
import net.minecraft.entity.Entity;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.PacketBuffer;
import net.minecraft.world.World;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.network.NetworkEvent;
import net.minecraftforge.fml.network.NetworkEvent.Context;

public class MessageEntity
{
    public final int entityId;
    public final CompoundNBT nbt;
    
    public MessageEntity(Entity entity)
    {
        this(entity.getEntityId(), VNBTUtility.savePrintToNBT(entity.getCapability(CapabilityProviderPrint.CAPABILITY_PRINT).orElseThrow(() -> new IllegalArgumentException("LazyOptional must not be empty (8)!"))));
    }
    
    public MessageEntity(int entityId, CompoundNBT nbt)
    {
        this.nbt = nbt;
        this.entityId = entityId;
    }
    
    public static void encode(MessageEntity msg, PacketBuffer buf)
    {
        buf.writeInt(msg.entityId);
        buf.writeCompoundTag(msg.nbt);
    }
    
    public static MessageEntity decode(PacketBuffer buf)
    {
        return new MessageEntity(buf.readInt(), buf.readCompoundTag());
    }
    
    public static void handle(MessageEntity msg, Supplier<NetworkEvent.Context> ctx)
    {
        Context context = ctx.get();
        
        context.enqueueWork(() ->
        {
            World world = context.getSender().world;
            Entity entity = world.getEntityByID(msg.entityId);
            
            if(entity != null)
            {
                Print print = entity.getCapability(CapabilityProviderPrint.CAPABILITY_PRINT).orElse(new Print());
                if(!MinecraftForge.EVENT_BUS.post(new EntityPrintValidationEvent(entity)))
                {
                    print.overrideFromNBT(msg.nbt);
                }
            }
        });
        
        ctx.get().setPacketHandled(true);
    }
}
