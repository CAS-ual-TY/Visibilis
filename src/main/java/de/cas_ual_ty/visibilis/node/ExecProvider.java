package de.cas_ual_ty.visibilis.node;

import net.minecraft.command.ICommandSender;

public class ExecProvider
{
    protected ICommandSender commandSender;
    
    public ExecProvider(ICommandSender commandSender)
    {
        this.commandSender = commandSender;
    }
    
    public ICommandSender getCommandSender()
    {
        return commandSender;
    }
}
