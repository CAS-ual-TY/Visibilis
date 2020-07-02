package de.cas_ual_ty.visibilis.util;

import java.util.List;

import de.cas_ual_ty.visibilis.Visibilis;
import de.cas_ual_ty.visibilis.config.VConfigHelper;
import de.cas_ual_ty.visibilis.config.VConfiguration;
import de.cas_ual_ty.visibilis.node.Node;
import de.cas_ual_ty.visibilis.node.NodeType;
import de.cas_ual_ty.visibilis.node.field.Input;
import de.cas_ual_ty.visibilis.node.field.NodeField;
import de.cas_ual_ty.visibilis.node.field.Output;
import de.cas_ual_ty.visibilis.print.Print;
import de.cas_ual_ty.visibilis.print.capability.CapabilityProviderPrintHolder;
import de.cas_ual_ty.visibilis.print.provider.PrintProvider;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.fml.network.NetworkEvent;

public class VUtility
{
    public static final float[] COLOR_DEFAULT_WHITE = new float[] { 1F, 1F, 1F };
    public static final float[] COLOR_DEFAULT_BLACK = new float[] { 0F, 0F, 0F };
    public static final float[] COLOR_DEFAULT_GREY = new float[] { 0.5F, 0.5F, 0.5F };
    
    @SuppressWarnings("unchecked")
    public static <A, B> A cast(B b)
    {
        return (A)b;
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
    
    public static float[] toColor(int r, int g, int b)
    {
        return new float[] { r / 255F, g / 255F, b / 255F };
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
    
    public static CapabilityProviderPrintHolder attachCapability(AttachCapabilitiesEvent<?> event)
    {
        return VUtility.attachCapability(Visibilis.MOD_ID, event);
    }
    
    public static CapabilityProviderPrintHolder attachCapability(String modId, AttachCapabilitiesEvent<?> event)
    {
        return VUtility.attachCapability(modId, "print", event);
    }
    
    public static CapabilityProviderPrintHolder attachCapability(String modId, String name, AttachCapabilitiesEvent<?> event)
    {
        CapabilityProviderPrintHolder provider = new CapabilityProviderPrintHolder();
        event.addCapability(new ResourceLocation(modId, name), provider);
        event.addListener(provider.getListener());
        return provider;
    }
    
    public static boolean validate(Print p, List<NodeType<?>> typesList)
    {
        for(Node node : p.getNodes())
        {
            if(!typesList.contains(node.type))
            {
                return false;
            }
        }
        
        return VUtility.validateConnections(p);
    }
    
    public static boolean validateConnections(Print p)
    {
        for(Node node : p.getNodes())
        {
            for(Output<?> out : node.getOutputsIterable())
            {
                if(out.hasConnections())
                {
                    for(Input<?> in : out.getConnections())
                    {
                        if(!NodeField.canConnect(out, in, true))
                        {
                            return false;
                        }
                    }
                }
            }
        }
        
        return true;
    }
}
