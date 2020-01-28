package de.cas_ual_ty.visibilis.node.player;

import java.util.function.Supplier;

import de.cas_ual_ty.visibilis.Visibilis;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.math.Vec3d;
import net.minecraftforge.fml.network.NetworkEvent;
import net.minecraftforge.fml.network.NetworkEvent.Context;

public class MessagePlayerMotion
{
    public final Vec3d motion;
    
    public MessagePlayerMotion(Vec3d motion)
    {
        this.motion = motion;
    }
    
    public MessagePlayerMotion(double x, double y, double z)
    {
        this(new Vec3d(x, y, z));
    }
    
    public static void encode(MessagePlayerMotion msg, PacketBuffer buf)
    {
        buf.writeDouble(msg.motion.x);
        buf.writeDouble(msg.motion.y);
        buf.writeDouble(msg.motion.z);
    }
    
    public static MessagePlayerMotion decode(PacketBuffer buf)
    {
        return new MessagePlayerMotion(buf.readDouble(), buf.readDouble(), buf.readDouble());
    }
    
    public static void handle(MessagePlayerMotion msg, Supplier<NetworkEvent.Context> ctx)
    {
        Context context = ctx.get();
        
        context.enqueueWork(() ->
        {
            Visibilis.proxy.doForClientPlayer((player) -> player.setMotion(msg.motion));
        });
        
        ctx.get().setPacketHandled(true);
    }
}
