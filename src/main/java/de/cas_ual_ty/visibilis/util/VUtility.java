package de.cas_ual_ty.visibilis.util;

import java.util.ArrayList;

import javax.annotation.Nullable;

import de.cas_ual_ty.visibilis.Visibilis;
import de.cas_ual_ty.visibilis.config.VConfigClient;
import de.cas_ual_ty.visibilis.config.VConfigHelper;
import de.cas_ual_ty.visibilis.config.VConfiguration;
import de.cas_ual_ty.visibilis.node.Node;
import de.cas_ual_ty.visibilis.print.provider.PrintProvider;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import net.minecraftforge.common.ForgeConfigSpec.Builder;
import net.minecraftforge.common.ForgeConfigSpec.DoubleValue;
import net.minecraftforge.fml.network.NetworkEvent;

public class VUtility
{
    @SuppressWarnings("unchecked")
    public static <A, B> A cast(B b)
    {
        return (A)b;
    }
    
    public static <A> ArrayList<A> cloneArrayList(ArrayList<A> list)
    {
        return VUtility.cast(list.clone());
    }
    
    public static void shutdown()
    {
        VUtility.setShutdown(true);
    }
    
    public static void unShutdown()
    {
        VUtility.setShutdown(false);
    }
    
    public static void setShutdown(boolean value)
    {
        VConfigHelper.setValueAndSave(VConfigHelper.commonConfig, "shutdown", value);
        VConfiguration.shutdown = value;
    }
    
    public static boolean isShutdown()
    {
        return VConfiguration.shutdown;
    }
    
    /**
     * Open the standard GuiPrint using the Print provided by the PrintProvider. This does nothing on server side.
     */
    public static void openGuiForClient(PrintProvider helper)
    {
        Visibilis.proxy.openGuiForClient(helper);
    }
    
    public static DoubleValue[] buildColorConfigValue(Builder builder, String translationPrefix, String key, float[] define)
    {
        DoubleValue[] values = new DoubleValue[3];
        
        builder.push(key);
        
        for(int i = 0; i < VConfigClient.SUFFIX_COLOR.length; ++i)
        {
            values[i] = builder
                .comment(VConfigClient.COLORS[i] + " Color")
                .translation(translationPrefix + key + VConfigClient.SUFFIX_COLOR[i])
                .defineInRange(VConfigClient.COLORS[i], define[i], 0F, 1F);
        }
        
        builder.pop();
        
        return values;
    }
    
    public static float[] toColor(DoubleValue[] values)
    {
        return new float[] { values[0].get().floatValue(), values[1].get().floatValue(), values[2].get().floatValue() };
    }
    
    public static World getWorld(NetworkEvent.Context context)
    {
        if(context.getSender() != null)
        {
            return context.getSender().world;
        }
        else
        {
            return Visibilis.proxy.getClientWorld();
        }
    }
    
    public static PlayerEntity getPlayer(NetworkEvent.Context context)
    {
        if(context.getSender() != null)
        {
            return context.getSender();
        }
        else
        {
            return Visibilis.proxy.getClientPlayer();
        }
    }
    
    public static Node instantiateNode(String modIdName)
    {
        return VUtility.instantiateNode(new ResourceLocation(modIdName));
    }
    
    public static Node instantiateNode(ResourceLocation rl)
    {
        if(Visibilis.NODE_TYPES_REGISTRY.containsKey(rl))
        {
            return Visibilis.NODE_TYPES_REGISTRY.getValue(rl).instantiate();
        }
        
        Visibilis.error("Node \"" + rl.toString() + "\" does not exist!");
        
        return null;
    }
}
