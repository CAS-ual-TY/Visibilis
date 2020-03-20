package de.cas_ual_ty.visibilis.print.entity;

import java.util.function.Supplier;

import de.cas_ual_ty.visibilis.Visibilis;
import de.cas_ual_ty.visibilis.util.VUtility;
import net.minecraft.entity.Entity;
import net.minecraft.world.World;
import net.minecraftforge.fml.network.NetworkEvent;
import net.minecraftforge.fml.network.NetworkEvent.Context;

public class MessagePrintEditEntity // Server to Client ONLY
{
    public static void handle(MessagePrintSynchEntity msg, Supplier<NetworkEvent.Context> ctx)
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
