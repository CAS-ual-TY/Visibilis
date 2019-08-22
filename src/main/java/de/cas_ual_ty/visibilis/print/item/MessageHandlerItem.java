package de.cas_ual_ty.visibilis.print.item;

import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumHand;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

public class MessageHandlerItem implements IMessageHandler<MessageItem, IMessage>
{
    @Override
    public IMessage onMessage(MessageItem message, MessageContext ctx)
    {
        ItemStack itemStack = ctx.getServerHandler().player.getHeldItem(EnumHand.MAIN_HAND);
        itemStack.setTagCompound(message.nbt);
        
        return null;
    }
}
