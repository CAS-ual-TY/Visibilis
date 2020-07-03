package de.cas_ual_ty.visibilis.registries;

import de.cas_ual_ty.visibilis.Visibilis;
import de.cas_ual_ty.visibilis.test.VCodePrintItem;
import net.minecraft.item.Item;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber.Bus;
import net.minecraftforge.registries.IForgeRegistry;
import net.minecraftforge.registries.ObjectHolder;

@EventBusSubscriber(modid = Visibilis.MOD_ID, bus = Bus.MOD)
@ObjectHolder(Visibilis.MOD_ID)
public class VItems
{
    public static final VCodePrintItem CODE_PRINT = null;
    
    @SubscribeEvent
    public static void register(RegistryEvent.Register<Item> event)
    {
        IForgeRegistry<Item> registry = event.getRegistry();
        
        registry.register(new VCodePrintItem(new Item.Properties().maxStackSize(1)).setRegistryName(Visibilis.MOD_ID + ":" + "code_print"));
    }
    
}
