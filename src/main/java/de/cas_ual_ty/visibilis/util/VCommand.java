package de.cas_ual_ty.visibilis.util;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;

import net.minecraft.command.CommandSource;
import net.minecraft.command.Commands;

public class VCommand
{
    public static void register(CommandDispatcher<CommandSource> dispatcher)
    {
        LiteralArgumentBuilder<CommandSource> base = Commands.literal("v").requires((arg1) ->
        {
            return arg1.getServer().isSinglePlayer() || arg1.hasPermissionLevel(2);
        });
        
        base.then(Commands.literal("sd").executes((cmd) ->
        {
            VCommand.shutdown(cmd.getSource());
            return 0;
        }));
        
        base.then(Commands.literal("shutdown").executes((cmd) ->
        {
            VCommand.shutdown(cmd.getSource());
            return 0;
        }));
        
        base.then(Commands.literal("usd").executes((cmd) ->
        {
            VCommand.unshutdown(cmd.getSource());
            return 0;
        }));
        
        base.then(Commands.literal("unshowdown").executes((cmd) ->
        {
            VCommand.unshutdown(cmd.getSource());
            return 0;
        }));
        
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
}
