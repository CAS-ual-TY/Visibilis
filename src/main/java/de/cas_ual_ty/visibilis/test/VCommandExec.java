package de.cas_ual_ty.visibilis.test;

import com.mojang.brigadier.CommandDispatcher;

import de.cas_ual_ty.visibilis.Visibilis;
import de.cas_ual_ty.visibilis.event.ExecCommandEvent;
import de.cas_ual_ty.visibilis.print.Print;
import de.cas_ual_ty.visibilis.print.impl.item.ItemPrint;
import net.minecraft.command.CommandSource;
import net.minecraft.command.Commands;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.ItemStack;
import net.minecraftforge.common.MinecraftForge;

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
        if (!sender.getWorld().isRemote)
        {
            MinecraftForge.EVENT_BUS.post(new ExecCommandEvent(sender));
        }
    }
    
    public static boolean executeFor(CommandSource sender, PlayerEntity player, int slot)
    {
        ItemStack itemStack = player.inventory.getStackInSlot(slot);
        
        if (!itemStack.isEmpty() && itemStack.getItem() instanceof ItemPrint)
        {
            ItemPrint item = (ItemPrint) itemStack.getItem();
            Print p = item.getPrint(itemStack);
            
            return VCommandExec.executeFor(sender, p);
        }
        
        return false;
    }
    
    public static boolean executeFor(CommandSource sender, Print p)
    {
        if (p != null)
        {
            p.executeEvent(Visibilis.MOD_ID, "command", sender);
            return true;
        }
        
        return false;
    }
    
    public static void execCommand(ExecCommandEvent event)
    {
        if (event.source.getEntity() instanceof PlayerEntity)
        {
            PlayerEntity player = (PlayerEntity) event.source.getEntity();
            VCommandExec.executeFor(event.source, player, EquipmentSlotType.MAINHAND.getSlotIndex());
            VCommandExec.executeFor(event.source, player, EquipmentSlotType.OFFHAND.getSlotIndex());
        }
    }
}
