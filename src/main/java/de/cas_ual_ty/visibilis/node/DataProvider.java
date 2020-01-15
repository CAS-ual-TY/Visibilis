package de.cas_ual_ty.visibilis.node;

import net.minecraft.command.CommandSource;
import net.minecraft.world.World;

public class DataProvider
{
    /*
     * Provides necessary access to functionality for all nodes.
     */
    
    protected CommandSource commandSender;
    
    public DataProvider(CommandSource commandSender)
    {
        this.commandSender = commandSender;
    }
    
    public CommandSource getCommandSender()
    {
        return this.commandSender;
    }
    
    public World getWorld()
    {
        return this.getCommandSender().getWorld();
    }
}
