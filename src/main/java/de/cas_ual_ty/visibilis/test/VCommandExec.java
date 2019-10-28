package de.cas_ual_ty.visibilis.test;

import java.util.ArrayList;
import java.util.List;

import de.cas_ual_ty.visibilis.Visibilis;
import de.cas_ual_ty.visibilis.print.Print;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommand;
import net.minecraft.command.ICommandSender;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;

public class VCommandExec implements ICommand
{
    public final ArrayList<String> aliases;
    
    public VCommandExec()
    {
        this.aliases = new ArrayList<String>();
        this.aliases.add("exec");
        this.aliases.add("execute");
        this.aliases.add("visibilis");
    }
    
    @Override
    public int compareTo(ICommand com)
    {
        return 0;
    }
    
    @Override
    public String getName()
    {
        return "exec";
    }
    
    @Override
    public String getUsage(ICommandSender sender)
    {
        return "exec";
    }
    
    @Override
    public List<String> getAliases()
    {
        return this.aliases;
    }
    
    @Override
    public void execute(MinecraftServer server, ICommandSender sender, String[] args) throws CommandException
    {
        if (!sender.getEntityWorld().isRemote && sender.getCommandSenderEntity() instanceof EntityPlayer)
        {
            EntityPlayer player = (EntityPlayer) sender.getCommandSenderEntity();
            
            if (!this.executeFor(player, EnumHand.MAIN_HAND))
            {
                this.executeFor(player, EnumHand.OFF_HAND);
            }
        }
    }
    
    public boolean executeFor(EntityPlayer player, EnumHand hand)
    {
        ItemStack itemStack = player.getHeldItem(hand);
        
        if (itemStack.getItem() == Visibilis.itemTest)
        {
            Print p = Visibilis.itemTest.getPrint(itemStack);
            
            if (p != null)
            {
                p.executeEvent(Visibilis.MOD_ID, "command", player);
                return true;
            }
        }
        
        return false;
    }
    
    @Override
    public boolean checkPermission(MinecraftServer server, ICommandSender sender)
    {
        return true;
    }
    
    @Override
    public List<String> getTabCompletions(MinecraftServer server, ICommandSender sender, String[] args, BlockPos targetPos)
    {
        return null;
    }
    
    @Override
    public boolean isUsernameIndex(String[] args, int index)
    {
        return false;
    }
}
