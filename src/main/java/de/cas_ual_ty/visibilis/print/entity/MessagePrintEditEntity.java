package de.cas_ual_ty.visibilis.print.entity;

import java.util.function.Supplier;

import de.cas_ual_ty.visibilis.Visibilis;
import de.cas_ual_ty.visibilis.print.capability.CapabilityProviderPrint;
import de.cas_ual_ty.visibilis.util.VNBTUtility;
import de.cas_ual_ty.visibilis.util.VUtility;
import net.minecraft.entity.Entity;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.PacketBuffer;
import net.minecraft.world.World;
import net.minecraftforge.fml.network.NetworkEvent;
import net.minecraftforge.fml.network.NetworkEvent.Context;

public class MessagePrintEditEntity // Server to Client ONLY
{
    public final int entityId;
    public final CompoundNBT nbt;
    
    public MessagePrintEditEntity(Entity entity)
    {
        this(entity.getEntityId(), VNBTUtility.savePrintToNBT(entity.getCapability(CapabilityProviderPrint.CAPABILITY_PRINT).orElseThrow(() -> new IllegalArgumentException("LazyOptional must not be empty (8)!"))));
    }
    
    public MessagePrintEditEntity(int entityId, CompoundNBT nbt)
    {
        this.nbt = nbt;
        this.entityId = entityId;
    }
    
    public static void encode(MessagePrintEditEntity msg, PacketBuffer buf)
    {
        buf.writeInt(msg.entityId);
        buf.writeCompoundTag(msg.nbt);
    }
    
    public static MessagePrintEditEntity decode(PacketBuffer buf)
    {
        return new MessagePrintEditEntity(buf.readInt(), buf.readCompoundTag());
    }
    
    public static void handle(MessagePrintEditEntity msg, Supplier<NetworkEvent.Context> ctx)
    {
        Context context = ctx.get();
        
        context.enqueueWork(() ->
        {
            World world = Visibilis.proxy.getClientWorld();
            Entity entity = world.getEntityByID(msg.entityId);
            
            if(entity instanceof EntityPrint)
            {
                VUtility.openGuiForClient(((EntityPrint)entity).getPrintProvider());
            }
        });
        
        ctx.get().setPacketHandled(true);
    }
}
