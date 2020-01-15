package de.cas_ual_ty.visibilis.util;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;

import de.cas_ual_ty.visibilis.Visibilis;
import de.cas_ual_ty.visibilis.event.CommandBuilderEvent;
import de.cas_ual_ty.visibilis.event.ExecCommandEvent;
import de.cas_ual_ty.visibilis.print.Print;
import de.cas_ual_ty.visibilis.print.item.ItemPrint;
import net.minecraft.command.CommandSource;
import net.minecraft.command.Commands;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.ItemStack;
import net.minecraftforge.common.MinecraftForge;

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
            return arg1.getServer().isSinglePlayer() || arg1.hasPermissionLevel(2);
        }).executes((arg2) ->
        {
            CommandSource source = arg2.getSource();
            VCommand.execute(source);
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
    
    public static boolean executeFor(CommandSource sender, PlayerEntity player, int slot)
    {
        ItemStack itemStack = player.inventory.getStackInSlot(slot);
        
        if(!itemStack.isEmpty() && itemStack.getItem() instanceof ItemPrint)
        {
            ItemPrint item = (ItemPrint)itemStack.getItem();
            Print p = item.getPrint(itemStack);
            
            return VCommand.executeFor(sender, p);
        }
        
        return false;
    }
    
    public static boolean executeFor(CommandSource sender, Print p)
    {
        if(p != null)
        {
            p.executeEvent(Visibilis.MOD_ID, "command", sender);
            return true;
        }
        
        return false;
    }
    
    public static void execCommand(ExecCommandEvent event)
    {
        if(event.source.getEntity() instanceof PlayerEntity)
        {
            PlayerEntity player = (PlayerEntity)event.source.getEntity();
            VCommand.executeFor(event.source, player, EquipmentSlotType.MAINHAND.getSlotIndex());
            VCommand.executeFor(event.source, player, EquipmentSlotType.OFFHAND.getSlotIndex());
        }
    }
}
