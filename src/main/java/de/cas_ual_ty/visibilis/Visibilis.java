package de.cas_ual_ty.visibilis;

import de.cas_ual_ty.visibilis.config.VConfigHelper;
import de.cas_ual_ty.visibilis.config.VConfiguration;
import de.cas_ual_ty.visibilis.node.base.dtboolean.NodeBooleanV;
import de.cas_ual_ty.visibilis.node.base.dtfloat.NodeFloatV;
import de.cas_ual_ty.visibilis.node.calculate.NodeAddition;
import de.cas_ual_ty.visibilis.node.calculate.NodeConcatenation;
import de.cas_ual_ty.visibilis.node.calculate.NodeDivision;
import de.cas_ual_ty.visibilis.node.calculate.NodeExponentiation;
import de.cas_ual_ty.visibilis.node.calculate.NodeLogarithm10;
import de.cas_ual_ty.visibilis.node.calculate.NodeLogarithm1p;
import de.cas_ual_ty.visibilis.node.calculate.NodeLogarithmE;
import de.cas_ual_ty.visibilis.node.calculate.NodeModulo;
import de.cas_ual_ty.visibilis.node.calculate.NodeMultiplication;
import de.cas_ual_ty.visibilis.node.calculate.NodeRoot;
import de.cas_ual_ty.visibilis.node.calculate.NodeSubtraction;
import de.cas_ual_ty.visibilis.node.cast.NodeFloatToInteger;
import de.cas_ual_ty.visibilis.node.compare.NodeFloatCompare;
import de.cas_ual_ty.visibilis.node.constant.NodeE;
import de.cas_ual_ty.visibilis.node.constant.NodePi;
import de.cas_ual_ty.visibilis.node.constant.NodeSQRT2;
import de.cas_ual_ty.visibilis.node.event.NodeEvent;
import de.cas_ual_ty.visibilis.node.exec.NodeBranch;
import de.cas_ual_ty.visibilis.node.exec.NodeFor;
import de.cas_ual_ty.visibilis.node.exec.NodeMerge;
import de.cas_ual_ty.visibilis.node.exec.NodeWhile;
import de.cas_ual_ty.visibilis.node.function.NodeCosines;
import de.cas_ual_ty.visibilis.node.function.NodeRound;
import de.cas_ual_ty.visibilis.node.function.NodeRoundDown;
import de.cas_ual_ty.visibilis.node.function.NodeRoundUp;
import de.cas_ual_ty.visibilis.node.function.NodeSines;
import de.cas_ual_ty.visibilis.node.function.NodeTangent;
import de.cas_ual_ty.visibilis.node.general.NodePrint;
import de.cas_ual_ty.visibilis.node.logic.NodeAND;
import de.cas_ual_ty.visibilis.node.logic.NodeNAND;
import de.cas_ual_ty.visibilis.node.logic.NodeNOR;
import de.cas_ual_ty.visibilis.node.logic.NodeNOT;
import de.cas_ual_ty.visibilis.node.logic.NodeOR;
import de.cas_ual_ty.visibilis.node.logic.NodeXNOR;
import de.cas_ual_ty.visibilis.node.logic.NodeXOR;
import de.cas_ual_ty.visibilis.print.item.MessageItem;
import de.cas_ual_ty.visibilis.proxy.IVSidedProxy;
import de.cas_ual_ty.visibilis.test.VCommandExec;
import de.cas_ual_ty.visibilis.test.VItemTest;
import de.cas_ual_ty.visibilis.test.VNodePrintDebug;
import de.cas_ual_ty.visibilis.util.VCommandShutdown;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber.Bus;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.event.server.FMLServerStartingEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.fml.network.NetworkRegistry;
import net.minecraftforge.fml.network.simple.SimpleChannel;

@Mod(Visibilis.MOD_ID)
public class Visibilis
{
    public static final String MOD_ID = "visibilis";
    public static final String PROTOCOL_VERSION = "1";
    
    public static VItemTest itemTest = (VItemTest) new VItemTest(new Item.Properties().group(ItemGroup.COMBAT).maxStackSize(1)).setRegistryName(Visibilis.MOD_ID + ":" + "test");
    
    public static Visibilis instance;
    
    public static IVSidedProxy proxy = (IVSidedProxy) DistExecutor.runForDist(() -> de.cas_ual_ty.visibilis.proxy.VProxyClient::new, () -> de.cas_ual_ty.visibilis.proxy.VProxyServer::new);
    
    public static SimpleChannel channel;
    
    public Visibilis()
    {
        Visibilis.instance = this;
        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::init);
        MinecraftForge.EVENT_BUS.addListener(this::serverStarting);
        MinecraftForge.EVENT_BUS.addListener(VCommandExec::execCommand);
        
        ModLoadingContext mld = ModLoadingContext.get();
        mld.registerConfig(ModConfig.Type.CLIENT, VConfiguration.CLIENT_SPEC);
        mld.registerConfig(ModConfig.Type.COMMON, VConfiguration.COMMON_SPEC);
    }
    
    public void init(FMLCommonSetupEvent event)
    {
        Visibilis.channel = NetworkRegistry.newSimpleChannel(new ResourceLocation(Visibilis.MOD_ID, "main"),
                        () -> Visibilis.PROTOCOL_VERSION,
                        Visibilis.PROTOCOL_VERSION::equals,
                        Visibilis.PROTOCOL_VERSION::equals);
        Visibilis.channel.registerMessage(0, MessageItem.class, MessageItem::encode, MessageItem::decode, MessageItem::handle);
        
        this.registerNodes();
    }
    
    public void serverStarting(FMLServerStartingEvent event)
    {
        VCommandExec.register(event.getCommandDispatcher());
        VCommandShutdown.register(event.getCommandDispatcher());
    }
    
    public void registerNodes()
    {
        NodesRegistry.INSTANCE.registerNode(NodeEvent.class, Visibilis.MOD_ID, "event");
        NodesRegistry.INSTANCE.registerNode(VNodePrintDebug.class, Visibilis.MOD_ID, "test");
        
        NodesRegistry.INSTANCE.registerNode(NodeAddition.class, Visibilis.MOD_ID, "addition");
        NodesRegistry.INSTANCE.registerNode(NodeDivision.class, Visibilis.MOD_ID, "division");
        NodesRegistry.INSTANCE.registerNode(NodeExponentiation.class, Visibilis.MOD_ID, "exponentiation");
        NodesRegistry.INSTANCE.registerNode(NodeLogarithm10.class, Visibilis.MOD_ID, "logarithm_10");
        NodesRegistry.INSTANCE.registerNode(NodeLogarithm1p.class, Visibilis.MOD_ID, "logarithm_1p");
        NodesRegistry.INSTANCE.registerNode(NodeLogarithmE.class, Visibilis.MOD_ID, "logarithm_e");
        NodesRegistry.INSTANCE.registerNode(NodeModulo.class, Visibilis.MOD_ID, "modulo");
        NodesRegistry.INSTANCE.registerNode(NodeMultiplication.class, Visibilis.MOD_ID, "multiplication");
        NodesRegistry.INSTANCE.registerNode(NodeRoot.class, Visibilis.MOD_ID, "root");
        NodesRegistry.INSTANCE.registerNode(NodeSubtraction.class, Visibilis.MOD_ID, "subtraction");
        NodesRegistry.INSTANCE.registerNode(NodeConcatenation.class, Visibilis.MOD_ID, "concatenation");
        
        NodesRegistry.INSTANCE.registerNode(NodeE.class, Visibilis.MOD_ID, "e");
        NodesRegistry.INSTANCE.registerNode(NodePi.class, Visibilis.MOD_ID, "pi");
        NodesRegistry.INSTANCE.registerNode(NodeSQRT2.class, Visibilis.MOD_ID, "sqrt2");
        NodesRegistry.INSTANCE.registerNode(NodeFloatV.class, Visibilis.MOD_ID, "const_float");
        NodesRegistry.INSTANCE.registerNode(NodeBooleanV.class, Visibilis.MOD_ID, "const_boolean");
        
        NodesRegistry.INSTANCE.registerNode(NodeCosines.class, Visibilis.MOD_ID, "cosines");
        NodesRegistry.INSTANCE.registerNode(NodeRound.class, Visibilis.MOD_ID, "round");
        NodesRegistry.INSTANCE.registerNode(NodeRoundDown.class, Visibilis.MOD_ID, "round_down");
        NodesRegistry.INSTANCE.registerNode(NodeRoundUp.class, Visibilis.MOD_ID, "round_up");
        NodesRegistry.INSTANCE.registerNode(NodeSines.class, Visibilis.MOD_ID, "sines");
        NodesRegistry.INSTANCE.registerNode(NodeTangent.class, Visibilis.MOD_ID, "tangent");
        
        NodesRegistry.INSTANCE.registerNode(NodeAND.class, Visibilis.MOD_ID, "and");
        NodesRegistry.INSTANCE.registerNode(NodeNAND.class, Visibilis.MOD_ID, "nand");
        NodesRegistry.INSTANCE.registerNode(NodeNOR.class, Visibilis.MOD_ID, "nor");
        NodesRegistry.INSTANCE.registerNode(NodeNOT.class, Visibilis.MOD_ID, "not");
        NodesRegistry.INSTANCE.registerNode(NodeOR.class, Visibilis.MOD_ID, "or");
        NodesRegistry.INSTANCE.registerNode(NodeXNOR.class, Visibilis.MOD_ID, "xnor");
        NodesRegistry.INSTANCE.registerNode(NodeXOR.class, Visibilis.MOD_ID, "xor");
        
        NodesRegistry.INSTANCE.registerNode(NodeBranch.class, Visibilis.MOD_ID, "branch");
        NodesRegistry.INSTANCE.registerNode(NodeMerge.class, Visibilis.MOD_ID, "merge");
        NodesRegistry.INSTANCE.registerNode(NodeFor.class, Visibilis.MOD_ID, "for");
        NodesRegistry.INSTANCE.registerNode(NodeWhile.class, Visibilis.MOD_ID, "while");
        
        NodesRegistry.INSTANCE.registerNode(NodeFloatCompare.class, Visibilis.MOD_ID, "equals");
        NodesRegistry.INSTANCE.registerNode(NodeFloatToInteger.class, Visibilis.MOD_ID, "cast_float_to_integer");
        
        NodesRegistry.INSTANCE.registerNode(NodePrint.class, Visibilis.MOD_ID, "print");
    }
    
    // TODO low: Some nice logging here please
    public static void error(String s)
    {
        System.err.println("[" + Visibilis.MOD_ID + "] " + s);
    }
    
    /**
     * For modding environment only
     */
    public static void debug(Object s)
    {
        System.out.println("------------------------ " + s);
    }
    
    @EventBusSubscriber(modid = Visibilis.MOD_ID, bus = Bus.MOD)
    public static class Registries
    {
        @SubscribeEvent
        public static void registerItems(RegistryEvent.Register<Item> event)
        {
            event.getRegistry().register(Visibilis.itemTest);
        }
        
        @SubscribeEvent
        public static void onModConfigEvent(ModConfig.ModConfigEvent event)
        {
            VConfigHelper.bake(event.getConfig());
        }
    }
}
