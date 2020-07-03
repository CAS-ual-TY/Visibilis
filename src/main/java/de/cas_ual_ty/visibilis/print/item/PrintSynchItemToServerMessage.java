package de.cas_ual_ty.visibilis.print.item;

import java.util.function.Supplier;

import de.cas_ual_ty.visibilis.event.ItemPrintValidationEvent;
import de.cas_ual_ty.visibilis.print.Print;
import de.cas_ual_ty.visibilis.print.capability.IPrintHolder;
import de.cas_ual_ty.visibilis.print.capability.PrintHolderCapabilityProvider;
import de.cas_ual_ty.visibilis.util.VNBTUtility;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.PacketBuffer;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.network.NetworkEvent;
import net.minecraftforge.fml.network.NetworkEvent.Context;

public class PrintSynchItemToServerMessage
{
    public final int slot;
    public final CompoundNBT nbt;
    
    public PrintSynchItemToServerMessage(int slot, Print print)
    {
        this(slot, VNBTUtility.savePrintToNBT(print, false));
    }
    
    public PrintSynchItemToServerMessage(int slot, CompoundNBT nbt)
    {
        this.nbt = nbt;
        this.slot = slot;
    }
    
    public static void encode(PrintSynchItemToServerMessage msg, PacketBuffer buf)
    {
        buf.writeInt(msg.slot);
        buf.writeCompoundTag(msg.nbt);
    }
    
    public static PrintSynchItemToServerMessage decode(PacketBuffer buf)
    {
        return new PrintSynchItemToServerMessage(buf.readInt(), buf.readCompoundTag());
    }
    
    public static void handle(PrintSynchItemToServerMessage msg, Supplier<NetworkEvent.Context> ctx)
    {
        Context context = ctx.get();
        
        context.enqueueWork(() ->
        {
            ItemStack itemStack = ctx.get().getSender().inventory.getStackInSlot(msg.slot);
            Print print = VNBTUtility.loadPrintFromNBT(msg.nbt, false);
            
            if(itemStack.getItem() instanceof IPrintItem && !((PrintItem)itemStack.getItem()).validate(itemStack, print))
            {
                return;
            }
            else if(!MinecraftForge.EVENT_BUS.post(new ItemPrintValidationEvent(itemStack, print)))
            {
                IPrintHolder holder = itemStack.getCapability(PrintHolderCapabilityProvider.CAPABILITY_PRINT_HOLDER).orElseThrow(() -> new IllegalArgumentException("LazyOptional must not be empty!"));
                holder.setPrint(print);
            }
        });
        
        ctx.get().setPacketHandled(true);
    }
}
