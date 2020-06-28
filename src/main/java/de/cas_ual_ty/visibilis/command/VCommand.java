package de.cas_ual_ty.visibilis.command;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;

import de.cas_ual_ty.visibilis.Visibilis;
import de.cas_ual_ty.visibilis.event.CommandBuilderEvent;
import de.cas_ual_ty.visibilis.event.EditCommandEvent;
import de.cas_ual_ty.visibilis.event.ExecCommandEvent;
import de.cas_ual_ty.visibilis.print.Print;
import de.cas_ual_ty.visibilis.print.item.IItemPrint;
import de.cas_ual_ty.visibilis.print.item.ItemPrint;
import de.cas_ual_ty.visibilis.util.VUtility;
import net.minecraft.command.CommandSource;
import net.minecraft.command.Commands;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Hand;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.network.PacketDistributor;

public class VCommand
{
    public static void register(CommandDispatcher<CommandSource> dispatcher)
    {
        LiteralArgumentBuilder<CommandSource> base = Commands.literal("v").requires((arg1) ->
        {
            return arg1.getServer().isSinglePlayer() || arg1.hasPermissionLevel(2);
        });
        
        // To show you how to use it
        CommandBuilderEvent event = new CommandBuilderEvent(base);
        
        event.getArgumentBase().then(Commands.literal("sd").executes((cmd) ->
        {
            VCommand.shutdown(cmd.getSource());
            return 0;
        }));
        
        event.getArgumentBase().then(Commands.literal("shutdown").executes((cmd) ->
        {
            VCommand.shutdown(cmd.getSource());
            return 0;
        }));
        
        event.getArgumentBase().then(Commands.literal("usd").executes((cmd) ->
        {
            VCommand.unshutdown(cmd.getSource());
            return 0;
        }));
        
        event.getArgumentBase().then(Commands.literal("unshowdown").executes((cmd) ->
        {
            VCommand.unshutdown(cmd.getSource());
            return 0;
        }));
        
        event.getArgumentBase().then(Commands.literal("exec").requires((arg1) ->
        {
            return arg1.getEntity() instanceof PlayerEntity && (arg1.getServer().isSinglePlayer() || arg1.hasPermissionLevel(2));
        }).executes((arg2) ->
        {
            VCommand.execute(arg2.getSource());
            return 0;
        }));
        
        event.getArgumentBase().then(Commands.literal("edit").requires((arg1) ->
        {
            return arg1.getEntity() instanceof PlayerEntity && (arg1.getServer().isSinglePlayer() || !MinecraftForge.EVENT_BUS.post(new EditCommandEvent(arg1)));
        }).executes((arg2) ->
        {
            VCommand.edit(arg2.getSource());
            return 0;
        }));
        
        MinecraftForge.EVENT_BUS.post(event);
        
        dispatcher.register(base);
    }
    
    public static void shutdown(CommandSource sender)
    {
        if(!sender.getWorld().isRemote)
        {
            VUtility.shutdown();
        }
    }
    
    public static void unshutdown(CommandSource sender)
    {
        if(!sender.getWorld().isRemote)
        {
            VUtility.unShutdown();
        }
    }
    
    public static void execute(CommandSource sender)
    {
        if(!sender.getWorld().isRemote)
        {
            MinecraftForge.EVENT_BUS.post(new ExecCommandEvent(sender));
        }
    }
    
    public static void edit(CommandSource sender)
    {
        ServerPlayerEntity player = (ServerPlayerEntity)sender.getEntity();
        ItemStack stack;
        
        for(Hand hand : Hand.values())
        {
            stack = player.getHeldItem(hand);
            if(stack.getItem() instanceof IItemPrint && ((ItemPrint)stack.getItem()).isEditable(stack, sender))
            {
                Visibilis.channel.send(PacketDistributor.PLAYER.with(() -> player), new MessagePrintEquipmentSlot(hand == Hand.MAIN_HAND ? player.inventory.currentItem : EquipmentSlotType.OFFHAND.getSlotIndex()));
                return;
            }
        }
    }
    
    public static boolean executeFor(CommandSource sender, PlayerEntity player, int slot, String event)
    {
        ItemStack itemStack = player.inventory.getStackInSlot(slot);
        
        if(!itemStack.isEmpty() && itemStack.getItem() instanceof IItemPrint)
        {
            IItemPrint item = (IItemPrint)itemStack.getItem();
            Print p = item.getPrint(itemStack);
            
            if(p != null)
            {
                return p.executeEvent(Visibilis.MOD_ID, event, item.createDataProvider(player, itemStack));
            }
        }
        
        return false;
    }
    
    public static void execCommand(ExecCommandEvent event)
    {
        if(event.source.getEntity() instanceof PlayerEntity)
        {
            PlayerEntity player = (PlayerEntity)event.source.getEntity();
            VCommand.executeFor(event.source, player, EquipmentSlotType.MAINHAND.getSlotIndex(), "command");
            VCommand.executeFor(event.source, player, EquipmentSlotType.OFFHAND.getSlotIndex(), "command");
        }
    }
}
