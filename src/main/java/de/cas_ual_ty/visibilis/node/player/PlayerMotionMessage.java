package de.cas_ual_ty.visibilis.node.player;

import java.util.function.Supplier;

import de.cas_ual_ty.visibilis.Visibilis;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.math.Vec3d;
import net.minecraftforge.fml.network.NetworkEvent;
import net.minecraftforge.fml.network.NetworkEvent.Context;

public class PlayerMotionMessage
{
    public final Vec3d motion;
    
    public PlayerMotionMessage(Vec3d motion)
    {
        this.motion = motion;
    }
    
    public PlayerMotionMessage(double x, double y, double z)
    {
        this(new Vec3d(x, y, z));
    }
    
    public static void encode(PlayerMotionMessage msg, PacketBuffer buf)
    {
        buf.writeDouble(msg.motion.x);
        buf.writeDouble(msg.motion.y);
        buf.writeDouble(msg.motion.z);
    }
    
    public static PlayerMotionMessage decode(PacketBuffer buf)
    {
        return new PlayerMotionMessage(buf.readDouble(), buf.readDouble(), buf.readDouble());
    }
    
    public static void handle(PlayerMotionMessage msg, Supplier<NetworkEvent.Context> ctx)
    {
        Context context = ctx.get();
        
        context.enqueueWork(() ->
        {
            Visibilis.proxy.doForClientPlayer((player) -> player.setMotion(msg.motion));
        });
        
        ctx.get().setPacketHandled(true);
    }
}
