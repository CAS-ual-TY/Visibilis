package de.cas_ual_ty.visibilis.command;

import java.util.function.Supplier;

import de.cas_ual_ty.visibilis.Visibilis;
import de.cas_ual_ty.visibilis.print.item.IPrintItem;
import de.cas_ual_ty.visibilis.util.VUtility;
import net.minecraft.item.ItemStack;
import net.minecraft.network.PacketBuffer;
import net.minecraftforge.fml.network.NetworkEvent;
import net.minecraftforge.fml.network.NetworkEvent.Context;

public class PrintEquipmentSlotMessage
{
    public final int equipmentSlot;
    
    public PrintEquipmentSlotMessage(int equipmentSlot)
    {
        this.equipmentSlot = equipmentSlot;
    }
    
    public static void encode(PrintEquipmentSlotMessage msg, PacketBuffer buf)
    {
        buf.writeInt(msg.equipmentSlot);
    }
    
    public static PrintEquipmentSlotMessage decode(PacketBuffer buf)
    {
        return new PrintEquipmentSlotMessage(buf.readInt());
    }
    
    public static void handle(PrintEquipmentSlotMessage msg, Supplier<NetworkEvent.Context> ctx)
    {
        Context context = ctx.get();
        
        context.enqueueWork(() ->
        {
            Visibilis.proxy.doForClientPlayer((player) ->
            {
                ItemStack stack = player.inventory.getStackInSlot(msg.equipmentSlot);
                
                if(stack.getItem() instanceof IPrintItem)
                {
                    VUtility.openGuiForClient(((IPrintItem)stack.getItem()).getPrintProvider(stack, msg.equipmentSlot));
                }
            });
        });
        
        ctx.get().setPacketHandled(true);
    }
}
