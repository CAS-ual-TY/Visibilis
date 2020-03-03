package de.cas_ual_ty.visibilis.print.item;

import java.util.function.Supplier;

import de.cas_ual_ty.visibilis.event.ItemPrintValidationEvent;
import de.cas_ual_ty.visibilis.print.Print;
import de.cas_ual_ty.visibilis.print.capability.CapabilityProviderPrint;
import de.cas_ual_ty.visibilis.util.VNBTUtility;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.PacketBuffer;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.network.NetworkEvent;
import net.minecraftforge.fml.network.NetworkEvent.Context;

public class MessagePrintSynchItem
{
    public final int slot;
    public final CompoundNBT nbt;
    
    public MessagePrintSynchItem(int slot, ItemStack itemStack)
    {
        this(slot, VNBTUtility.savePrintToNBT(itemStack.getCapability(CapabilityProviderPrint.CAPABILITY_PRINT).orElseThrow(() -> new IllegalArgumentException("LazyOptional must not be empty (6)!"))));
    }
    
    public MessagePrintSynchItem(int slot, CompoundNBT nbt)
    {
        this.nbt = nbt;
        this.slot = slot;
    }
    
    public static void encode(MessagePrintSynchItem msg, PacketBuffer buf)
    {
        buf.writeInt(msg.slot);
        buf.writeCompoundTag(msg.nbt);
    }
    
    public static MessagePrintSynchItem decode(PacketBuffer buf)
    {
        return new MessagePrintSynchItem(buf.readInt(), buf.readCompoundTag());
    }
    
    public static void handle(MessagePrintSynchItem msg, Supplier<NetworkEvent.Context> ctx)
    {
        Context context = ctx.get();
        
        context.enqueueWork(() ->
        {
            ItemStack itemStack = ctx.get().getSender().inventory.getStackInSlot(msg.slot);
            Print print = itemStack.getCapability(CapabilityProviderPrint.CAPABILITY_PRINT).orElse(new Print());
            
            if(itemStack.getItem() instanceof ItemPrint && !((ItemPrint)itemStack.getItem()).validate(itemStack, print))
            {
                return;
            }
            else if(!MinecraftForge.EVENT_BUS.post(new ItemPrintValidationEvent(itemStack, print)))
            {
                print.overrideFromNBT(msg.nbt);
            }
        });
        
        ctx.get().setPacketHandled(true);
    }
}
