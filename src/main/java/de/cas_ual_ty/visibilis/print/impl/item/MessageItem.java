package de.cas_ual_ty.visibilis.print.impl.item;

import java.util.function.Supplier;

import de.cas_ual_ty.visibilis.util.NBTUtility;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.Hand;
import net.minecraftforge.fml.network.NetworkEvent;

public class MessageItem
{
    public CompoundNBT nbt;
    public Hand hand;
    
    public MessageItem(ItemStack itemStack, Hand hand)
    {
        this(itemStack.getTag(), hand);
    }
    
    public MessageItem(CompoundNBT nbt, Hand hand)
    {
        this.nbt = nbt;
        this.hand = hand;
    }
    
    public static void encode(MessageItem msg, PacketBuffer buf)
    {
        buf.writeBoolean(msg.hand == Hand.MAIN_HAND ? true : false);
        buf.writeCompoundTag(msg.nbt);
    }
    
    public static MessageItem decode(PacketBuffer buf)
    {
        Hand hand = buf.readBoolean() ? Hand.MAIN_HAND : Hand.OFF_HAND;
        CompoundNBT nbt = buf.readCompoundTag();
        return new MessageItem(nbt, hand);
    }
    
    public static void handle(MessageItem msg, Supplier<NetworkEvent.Context> ctx)
    {
        ItemStack itemStack = ctx.get().getSender().getHeldItem(msg.hand);
        itemStack.setTag(msg.nbt);
        
        // TODO validate! fire event?
        
        NBTUtility.printTree(msg.nbt);
    }
}
