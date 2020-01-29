package de.cas_ual_ty.visibilis.command;

import java.util.function.Supplier;

import de.cas_ual_ty.visibilis.Visibilis;
import de.cas_ual_ty.visibilis.print.item.ItemPrint;
import de.cas_ual_ty.visibilis.util.VUtility;
import net.minecraft.item.ItemStack;
import net.minecraft.network.PacketBuffer;
import net.minecraftforge.fml.network.NetworkEvent;
import net.minecraftforge.fml.network.NetworkEvent.Context;

public class MessagePrintEquipmentSlot
{
    public final int equipmentSlot;
    
    public MessagePrintEquipmentSlot(int equipmentSlot)
    {
        this.equipmentSlot = equipmentSlot;
    }
    
    public static void encode(MessagePrintEquipmentSlot msg, PacketBuffer buf)
    {
        buf.writeInt(msg.equipmentSlot);
    }
    
    public static MessagePrintEquipmentSlot decode(PacketBuffer buf)
    {
        return new MessagePrintEquipmentSlot(buf.readInt());
    }
    
    public static void handle(MessagePrintEquipmentSlot msg, Supplier<NetworkEvent.Context> ctx)
    {
        Context context = ctx.get();
        
        context.enqueueWork(() ->
        {
            Visibilis.proxy.doForClientPlayer((player) ->
            {
                ItemStack stack = player.inventory.getStackInSlot(msg.equipmentSlot);
                
                if(stack.getItem() instanceof ItemPrint)
                {
                    VUtility.openGuiForClient(((ItemPrint)stack.getItem()).getPrintProvider(stack, msg.equipmentSlot));
                }
            });
        });
        
        ctx.get().setPacketHandled(true);
    }
}
