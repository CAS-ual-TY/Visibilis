package de.cas_ual_ty.visibilis.node;

import net.minecraft.command.CommandSource;

public class ExecProvider
{
    /*
     * Provides necessary access to functionality for all nodes.
     */
    
    protected CommandSource commandSender;
    
    public ExecProvider(CommandSource commandSender)
    {
        this.commandSender = commandSender;
    }
    
    public CommandSource getCommandSender()
    {
        return this.commandSender;
    }
}
