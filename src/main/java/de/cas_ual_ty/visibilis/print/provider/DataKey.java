package de.cas_ual_ty.visibilis.print.provider;

import de.cas_ual_ty.visibilis.Visibilis;
import de.cas_ual_ty.visibilis.util.VUtility;
import net.minecraft.command.CommandSource;
import net.minecraft.entity.Entity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;

public class DataKey<I>
{
    public static final DataKey<CommandSource> KEY_COMMAND_SOURCE = new DataKey<>(Visibilis.MOD_ID, "command_source");
    public static final DataKey<World> KEY_WORLD = new DataKey<>(Visibilis.MOD_ID, "world");
    public static final DataKey<Entity> KEY_ENTITY = new DataKey<>(Visibilis.MOD_ID, "entity");
    
    public static final DataKey<ItemStack> KEY_ITEM_STACK = new DataKey<>(Visibilis.MOD_ID, "item_stack");
    
    private final ResourceLocation rl;
    private final int hash;
    
    public DataKey(String modId, String type)
    {
        this.rl = new ResourceLocation(modId, type);
        this.hash = this.rl.hashCode();
    }
    
    public ResourceLocation getRL()
    {
        return this.rl;
    }
    
    @Override
    public int hashCode()
    {
        return this.hash;
    }
    
    public I cast(Object o)
    {
        return VUtility.cast(o);
    }
}
