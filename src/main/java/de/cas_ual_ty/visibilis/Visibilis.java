package de.cas_ual_ty.visibilis;

import de.cas_ual_ty.visibilis.handler.VEventHandler;
import de.cas_ual_ty.visibilis.handler.VGuiHandler;
import de.cas_ual_ty.visibilis.node.calculate.NodeAddition;
import de.cas_ual_ty.visibilis.node.calculate.NodeDivision;
import de.cas_ual_ty.visibilis.node.calculate.NodeExponentiation;
import de.cas_ual_ty.visibilis.node.calculate.NodeLogarithm10;
import de.cas_ual_ty.visibilis.node.calculate.NodeLogarithm1p;
import de.cas_ual_ty.visibilis.node.calculate.NodeLogarithmE;
import de.cas_ual_ty.visibilis.node.calculate.NodeModulo;
import de.cas_ual_ty.visibilis.node.calculate.NodeMultiplication;
import de.cas_ual_ty.visibilis.node.calculate.NodeRoot;
import de.cas_ual_ty.visibilis.node.calculate.NodeSubtraction;
import de.cas_ual_ty.visibilis.node.constant.NodeE;
import de.cas_ual_ty.visibilis.node.constant.NodeFalse;
import de.cas_ual_ty.visibilis.node.constant.NodePi;
import de.cas_ual_ty.visibilis.node.constant.NodeSQRT2;
import de.cas_ual_ty.visibilis.node.constant.NodeTrue;
import de.cas_ual_ty.visibilis.node.function.NodeCosines;
import de.cas_ual_ty.visibilis.node.function.NodeRound;
import de.cas_ual_ty.visibilis.node.function.NodeRoundDown;
import de.cas_ual_ty.visibilis.node.function.NodeRoundUp;
import de.cas_ual_ty.visibilis.node.function.NodeSines;
import de.cas_ual_ty.visibilis.node.function.NodeTangent;
import de.cas_ual_ty.visibilis.node.logic.NodeAND;
import de.cas_ual_ty.visibilis.node.logic.NodeNAND;
import de.cas_ual_ty.visibilis.node.logic.NodeNOR;
import de.cas_ual_ty.visibilis.node.logic.NodeNOT;
import de.cas_ual_ty.visibilis.node.logic.NodeOR;
import de.cas_ual_ty.visibilis.node.logic.NodeXNOR;
import de.cas_ual_ty.visibilis.node.logic.NodeXOR;
import de.cas_ual_ty.visibilis.print.item.MessageHandlerItem;
import de.cas_ual_ty.visibilis.print.item.MessageItem;
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
        
        this.registerNodes();
    }
    
    @EventHandler
    public void postInit(FMLPostInitializationEvent event)
    {
        
    }
    
    public void registerNodes()
    {
        VRegistry.INSTANCE.registerNode(NodeAddition.class, Visibilis.MOD_ID, "addition");
        VRegistry.INSTANCE.registerNode(NodeDivision.class, Visibilis.MOD_ID, "division");
        VRegistry.INSTANCE.registerNode(NodeExponentiation.class, Visibilis.MOD_ID, "exponentiation");
        VRegistry.INSTANCE.registerNode(NodeLogarithm10.class, Visibilis.MOD_ID, "logarithm_10");
        VRegistry.INSTANCE.registerNode(NodeLogarithm1p.class, Visibilis.MOD_ID, "logarithm_1p");
        VRegistry.INSTANCE.registerNode(NodeLogarithmE.class, Visibilis.MOD_ID, "logarithm_e");
        VRegistry.INSTANCE.registerNode(NodeModulo.class, Visibilis.MOD_ID, "modulo");
        VRegistry.INSTANCE.registerNode(NodeMultiplication.class, Visibilis.MOD_ID, "multiplication");
        VRegistry.INSTANCE.registerNode(NodeRoot.class, Visibilis.MOD_ID, "root");
        VRegistry.INSTANCE.registerNode(NodeSubtraction.class, Visibilis.MOD_ID, "subtraction");
        
        VRegistry.INSTANCE.registerNode(NodeE.class, Visibilis.MOD_ID, "e");
        VRegistry.INSTANCE.registerNode(NodeFalse.class, Visibilis.MOD_ID, "false");
        VRegistry.INSTANCE.registerNode(NodePi.class, Visibilis.MOD_ID, "pi");
        VRegistry.INSTANCE.registerNode(NodeSQRT2.class, Visibilis.MOD_ID, "sqrt2");
        VRegistry.INSTANCE.registerNode(NodeTrue.class, Visibilis.MOD_ID, "true");
        
        VRegistry.INSTANCE.registerNode(NodeCosines.class, Visibilis.MOD_ID, "cosines");
        VRegistry.INSTANCE.registerNode(NodeRound.class, Visibilis.MOD_ID, "round");
        VRegistry.INSTANCE.registerNode(NodeRoundDown.class, Visibilis.MOD_ID, "round_down");
        VRegistry.INSTANCE.registerNode(NodeRoundUp.class, Visibilis.MOD_ID, "round_up");
        VRegistry.INSTANCE.registerNode(NodeSines.class, Visibilis.MOD_ID, "sines");
        VRegistry.INSTANCE.registerNode(NodeTangent.class, Visibilis.MOD_ID, "tangent");
        
        VRegistry.INSTANCE.registerNode(NodeAND.class, Visibilis.MOD_ID, "and");
        VRegistry.INSTANCE.registerNode(NodeNAND.class, Visibilis.MOD_ID, "nand");
        VRegistry.INSTANCE.registerNode(NodeNOR.class, Visibilis.MOD_ID, "nor");
        VRegistry.INSTANCE.registerNode(NodeNOT.class, Visibilis.MOD_ID, "not");
        VRegistry.INSTANCE.registerNode(NodeOR.class, Visibilis.MOD_ID, "or");
        VRegistry.INSTANCE.registerNode(NodeXNOR.class, Visibilis.MOD_ID, "xnor");
        VRegistry.INSTANCE.registerNode(NodeXOR.class, Visibilis.MOD_ID, "xor");
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
