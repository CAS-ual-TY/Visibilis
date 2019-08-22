package de.cas_ual_ty.visibilis.print;

import io.netty.buffer.ByteBuf;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumHand;
import net.minecraftforge.fml.common.network.ByteBufUtils;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;

public class MessageItem implements IMessage
{
    public NBTTagCompound nbt;
    public EnumHand hand;
    
    public MessageItem(ItemStack itemStack, EnumHand hand)
    {
        this.nbt = itemStack.getTagCompound();
        this.hand = hand;
    }
    
    public MessageItem()
    {
    }
    
    @Override
    public void fromBytes(ByteBuf buf)
    {
        this.hand = buf.readBoolean() ? EnumHand.MAIN_HAND : EnumHand.OFF_HAND;
        this.nbt = ByteBufUtils.readTag(buf);
    }
    
    @Override
    public void toBytes(ByteBuf buf)
    {
        buf.writeBoolean(this.hand == EnumHand.MAIN_HAND ? true : false);
        ByteBufUtils.writeTag(buf, this.nbt);
    }
}
