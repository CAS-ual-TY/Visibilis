package de.cas_ual_ty.visibilis.test;

import com.mojang.brigadier.CommandDispatcher;

import de.cas_ual_ty.visibilis.Visibilis;
import de.cas_ual_ty.visibilis.print.Print;
import net.minecraft.command.CommandSource;
import net.minecraft.command.Commands;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Hand;
import net.minecraft.util.text.StringTextComponent;

public class VCommandExec
{
    public static void register(CommandDispatcher<CommandSource> dispatcher)
    {
        dispatcher.register(Commands.literal("vexec").requires((arg1) -> {
            return arg1.getServer().isSinglePlayer() || arg1.hasPermissionLevel(2);
        }).executes((arg2) -> {
            CommandSource source = arg2.getSource();
            VCommandExec.execute(source);
            return 0;
        }));
    }
    
    public static void execute(CommandSource sender)
    {
        if (!sender.getWorld().isRemote && sender.getEntity() instanceof PlayerEntity)
        {
            PlayerEntity player = (PlayerEntity) sender.getEntity();
            
            try
            {
                if (!VCommandExec.executeFor(sender, player, Hand.MAIN_HAND))
                {
                    VCommandExec.executeFor(sender, player, Hand.OFF_HAND);
                }
            }
            catch (Exception e)
            {
                e.printStackTrace();
                throw e;
            }
        }
    }
    
    public static boolean executeFor(CommandSource sender, PlayerEntity player, Hand hand)
    {
        ItemStack itemStack = player.getHeldItem(hand);
        
        if (!itemStack.isEmpty() && itemStack.getItem() == Visibilis.itemTest)
        {
            Print p = Visibilis.itemTest.getPrint(itemStack, hand);
            
            if (p != null)
            {
                sender.sendFeedback(new StringTextComponent("Debug START"), true);
                p.executeEvent(Visibilis.MOD_ID, "command", sender);
                sender.sendFeedback(new StringTextComponent("Debug END"), true);
                return true;
            }
        }
        
        return false;
    }
}
