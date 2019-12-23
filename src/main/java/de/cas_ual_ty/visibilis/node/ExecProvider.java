package de.cas_ual_ty.visibilis.node;

import de.cas_ual_ty.visibilis.print.Print;
import net.minecraft.command.CommandSource;

public class ExecProvider
{
    /*
     * Provides necessary access to functionality for all nodes.
     */
    
    protected Print print;
    
    protected CommandSource commandSender;
    
    public ExecProvider(CommandSource commandSender)
    {
        this.commandSender = commandSender;
    }
    
    public CommandSource getCommandSender()
    {
        return this.commandSender;
    }
    
    public Print getPrint()
    {
        return this.print;
    }
    
    public ExecProvider setPrint(Print print)
    {
        this.print = print;
        return this;
    }
}
