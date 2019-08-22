package de.cas_ual_ty.visibilis;

import de.cas_ual_ty.visibilis.handler.VEventHandler;
import de.cas_ual_ty.visibilis.handler.VGuiHandler;
import de.cas_ual_ty.visibilis.node.logic.NodeAND;
import de.cas_ual_ty.visibilis.node.logic.NodeNAND;
import de.cas_ual_ty.visibilis.node.logic.NodeNOR;
import de.cas_ual_ty.visibilis.node.logic.NodeNOT;
import de.cas_ual_ty.visibilis.node.logic.NodeOR;
import de.cas_ual_ty.visibilis.node.logic.NodeXNOR;
import de.cas_ual_ty.visibilis.node.logic.NodeXOR;
import de.cas_ual_ty.visibilis.print.MessageHandlerItem;
import de.cas_ual_ty.visibilis.print.MessageItem;
import de.cas_ual_ty.visibilis.proxy.IVSidedProxy;
import de.cas_ual_ty.visibilis.test.VItemTest;
import net.minecraft.item.Item;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.Mod.Instance;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.network.NetworkRegistry;
import net.minecraftforge.fml.common.network.simpleimpl.SimpleNetworkWrapper;
import net.minecraftforge.fml.relauncher.Side;

@Mod(modid = Visibilis.MOD_ID, name = Visibilis.MOD_NAME, version = Visibilis.MOD_VERSION)
public class Visibilis
{
    public static final String MOD_ID = "visibilis";
    public static final String MOD_NAME = "Visibilis";
    public static final String MOD_VERSION = "1.0.0.0";
    
    // @GameRegistry.ObjectHolder(MOD_ID + ":" + "test")
    public static VItemTest itemTest = (VItemTest) new VItemTest().setUnlocalizedName(Visibilis.MOD_ID + ":" + "test").setRegistryName(Visibilis.MOD_ID + ":" + "test");
    
    @Instance
    public static Visibilis instance;
    
    @SidedProxy(modId = Visibilis.MOD_ID, clientSide = "de.cas_ual_ty.visibilis.proxy.VProxyClient", serverSide = "de.cas_ual_ty.visibilis.proxy.VProxyServer")
    public static IVSidedProxy proxy;
    
    public static VEventHandler eventHandler;
    public static VGuiHandler guiHandler;
    public static SimpleNetworkWrapper channel;
    
    @EventHandler
    public void preInit(FMLPreInitializationEvent event)
    {
        Visibilis.proxy.preInit();
    }
    
    @EventHandler
    public void init(FMLInitializationEvent event)
    {
        MinecraftForge.EVENT_BUS.register((Visibilis.eventHandler = new VEventHandler()));
        NetworkRegistry.INSTANCE.registerGuiHandler(Visibilis.instance, (Visibilis.guiHandler = new VGuiHandler()));
        Visibilis.channel = NetworkRegistry.INSTANCE.newSimpleChannel(Visibilis.MOD_ID);
        Visibilis.channel.registerMessage(MessageHandlerItem.class, MessageItem.class, 0, Side.SERVER);
        
        VRegistry.INSTANCE.registerNode(NodeAND.class, Visibilis.MOD_ID, "and");
        VRegistry.INSTANCE.registerNode(NodeNAND.class, Visibilis.MOD_ID, "nand");
        VRegistry.INSTANCE.registerNode(NodeNOR.class, Visibilis.MOD_ID, "nor");
        VRegistry.INSTANCE.registerNode(NodeNOT.class, Visibilis.MOD_ID, "not");
        VRegistry.INSTANCE.registerNode(NodeOR.class, Visibilis.MOD_ID, "or");
        VRegistry.INSTANCE.registerNode(NodeXNOR.class, Visibilis.MOD_ID, "xnor");
        VRegistry.INSTANCE.registerNode(NodeXOR.class, Visibilis.MOD_ID, "xor");
    }
    
    @EventHandler
    public void postInit(FMLPostInitializationEvent event)
    {
        
    }
    
    // TODO low: Some nice logging here please
    public static void error(String s)
    {
        System.err.println("[" + Visibilis.MOD_ID + "] " + s);
    }
    
    /**
     * For modding environment only
     */
    public static void debug(String s)
    {
        System.out.println("------------------------ " + s);
    }
    
    @EventBusSubscriber(modid = Visibilis.MOD_ID)
    public static class Registries
    {
        @SubscribeEvent
        public static void registerItems(RegistryEvent.Register<Item> event)
        {
            event.getRegistry().register(Visibilis.itemTest);
        }
    }
}
