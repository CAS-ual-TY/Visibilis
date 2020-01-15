package de.cas_ual_ty.visibilis.util;

import com.mojang.brigadier.CommandDispatcher;

import net.minecraft.command.CommandSource;
import net.minecraft.command.Commands;

public class VCommandShutdown
{
    public static void register(CommandDispatcher<CommandSource> dispatcher)
    {
        dispatcher.register(Commands.literal("vshutdown").requires((arg1) ->
        {
            return arg1.getServer().isSinglePlayer() || arg1.hasPermissionLevel(2);
        }).executes((arg2) ->
        {
            CommandSource source = arg2.getSource();
            VCommandShutdown.execute(source);
            return 0;
        }));
    }
    
    public static void execute(CommandSource sender)
    {
        if(!sender.getWorld().isRemote)
        {
            VUtility.shutdown();
        }
    }
}
