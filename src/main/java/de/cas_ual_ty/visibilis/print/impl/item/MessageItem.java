package de.cas_ual_ty.visibilis.print.impl.item;

import java.util.function.Supplier;

import de.cas_ual_ty.visibilis.event.ItemPrintValidationEvent;
import de.cas_ual_ty.visibilis.print.Print;
import de.cas_ual_ty.visibilis.util.NBTUtility;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.PacketBuffer;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.network.NetworkEvent;

public class MessageItem
{
    public final int slot;
    public final CompoundNBT nbt;
    
    public MessageItem(int slot, ItemStack itemStack)
    {
        this(slot, NBTUtility.savePrintToNBT(((ItemPrint) itemStack.getItem()).getPrint(itemStack)));
    }
    
    public MessageItem(int slot, CompoundNBT nbt)
    {
        this.nbt = nbt;
        this.slot = slot;
    }
    
    public static void encode(MessageItem msg, PacketBuffer buf)
    {
        buf.writeInt(msg.slot);
        buf.writeCompoundTag(msg.nbt);
    }
    
    public static MessageItem decode(PacketBuffer buf)
    {
        return new MessageItem(buf.readInt(), buf.readCompoundTag());
    }
    
    public static void handle(MessageItem msg, Supplier<NetworkEvent.Context> ctx)
    {
        ItemStack itemStack = ctx.get().getSender().inventory.getStackInSlot(msg.slot);
        ItemPrint item = (ItemPrint) itemStack.getItem(); //TODO change to print capability
        Print print = NBTUtility.loadPrintFromNBT(msg.nbt);
        
        if (!MinecraftForge.EVENT_BUS.post(new ItemPrintValidationEvent(itemStack, print)))
        {
            item.setPrintTag(itemStack, msg.nbt);
        }
    }
}
