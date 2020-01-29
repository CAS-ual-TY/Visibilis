package de.cas_ual_ty.visibilis.node.dataprovider;

import de.cas_ual_ty.visibilis.Visibilis;
import net.minecraft.command.CommandSource;
import net.minecraft.world.World;

public class DataProvider
{
    /*
     * Provides necessary access to functionality for all nodes.
     */
    
    protected CommandSource commandSender;
    public World world;
    
    public DataProvider(CommandSource commandSender)
    {
        this.commandSender = commandSender;
        
        this.world = this.getCommandSender().getWorld();
        
        if(this.world == null)
        {
            if(this.getCommandSender().getEntity() != null)
            {
                this.world = this.getCommandSender().getEntity().world;
            }
            else
            {
                this.world = Visibilis.proxy.getClientWorld();
            }
        }
    }
    
    public CommandSource getCommandSender()
    {
        return this.commandSender;
    }
    
    public World getWorld()
    {
        return this.world;
    }
}
