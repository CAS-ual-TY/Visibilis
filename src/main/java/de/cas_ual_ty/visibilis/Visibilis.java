package de.cas_ual_ty.visibilis;

import de.cas_ual_ty.visibilis.config.VConfigHelper;
import de.cas_ual_ty.visibilis.config.VConfiguration;
import de.cas_ual_ty.visibilis.node.NodeType;
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
import net.minecraftforge.event.RegistryEvent.NewRegistry;
import net.minecraftforge.eventbus.api.IEventBus;
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
import net.minecraftforge.registries.IForgeRegistry;
import net.minecraftforge.registries.RegistryBuilder;

@Mod(Visibilis.MOD_ID)
public class Visibilis
{
    public static final String MOD_ID = "visibilis";
    public static final String PROTOCOL_VERSION = "1";
    
    public static VItemTest itemTest = (VItemTest)new VItemTest(new Item.Properties().group(ItemGroup.COMBAT).maxStackSize(1)).setRegistryName(Visibilis.MOD_ID + ":" + "test");
    
    public static Visibilis instance;
    
    public static IVSidedProxy proxy = (IVSidedProxy)DistExecutor.runForDist(() -> de.cas_ual_ty.visibilis.proxy.VProxyClient::new, () -> de.cas_ual_ty.visibilis.proxy.VProxyServer::new);
    
    public static SimpleChannel channel;
    
    public static IForgeRegistry<NodeType<?>> nodesRegistry;
    
    public Visibilis()
    {
        Visibilis.instance = this;
        
        IEventBus bus = FMLJavaModLoadingContext.get().getModEventBus();
        bus.addListener(this::init);
        bus.addListener(this::newRegistry);
        
        bus = MinecraftForge.EVENT_BUS;
        bus.addListener(this::serverStarting);
        bus.addListener(VCommandExec::execCommand);
        
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
    }
    
    @SuppressWarnings({ "unchecked", "rawtypes" })
    public void newRegistry(NewRegistry event)
    {
        Visibilis.nodesRegistry = new RegistryBuilder().setName(new ResourceLocation(Visibilis.MOD_ID, "nodes")).setType(NodeType.class).setMaxID(4096).setDefaultKey(new ResourceLocation(Visibilis.MOD_ID, "default")).create();
    }
    
    public void serverStarting(FMLServerStartingEvent event)
    {
        VCommandExec.register(event.getCommandDispatcher());
        VCommandShutdown.register(event.getCommandDispatcher());
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
        public static void registerNodeTypes(RegistryEvent.Register<NodeType<?>> event)
        {
            IForgeRegistry<NodeType<?>> registry = event.getRegistry();
            
            registry.register(new NodeType<>(NodeEvent::new).setRegistryName(Visibilis.MOD_ID, "event"));
            registry.register(new NodeType<>(VNodePrintDebug::new).setRegistryName(Visibilis.MOD_ID, "test"));
            
            registry.register(new NodeType<>(NodeAddition::new).setRegistryName(Visibilis.MOD_ID, "addition"));
            registry.register(new NodeType<>(NodeDivision::new).setRegistryName(Visibilis.MOD_ID, "division"));
            registry.register(new NodeType<>(NodeExponentiation::new).setRegistryName(Visibilis.MOD_ID, "exponentiation"));
            registry.register(new NodeType<>(NodeLogarithm10::new).setRegistryName(Visibilis.MOD_ID, "logarithm_10"));
            registry.register(new NodeType<>(NodeLogarithm1p::new).setRegistryName(Visibilis.MOD_ID, "logarithm_1p"));
            registry.register(new NodeType<>(NodeLogarithmE::new).setRegistryName(Visibilis.MOD_ID, "logarithm_e"));
            registry.register(new NodeType<>(NodeModulo::new).setRegistryName(Visibilis.MOD_ID, "modulo"));
            registry.register(new NodeType<>(NodeMultiplication::new).setRegistryName(Visibilis.MOD_ID, "multiplication"));
            registry.register(new NodeType<>(NodeRoot::new).setRegistryName(Visibilis.MOD_ID, "root"));
            registry.register(new NodeType<>(NodeSubtraction::new).setRegistryName(Visibilis.MOD_ID, "subtraction"));
            registry.register(new NodeType<>(NodeConcatenation::new).setRegistryName(Visibilis.MOD_ID, "concatenation"));
            
            registry.register(new NodeType<>(NodeE::new).setRegistryName(Visibilis.MOD_ID, "e"));
            registry.register(new NodeType<>(NodePi::new).setRegistryName(Visibilis.MOD_ID, "pi"));
            registry.register(new NodeType<>(NodeSQRT2::new).setRegistryName(Visibilis.MOD_ID, "sqrt2"));
            registry.register(new NodeType<>(NodeFloatV::new).setRegistryName(Visibilis.MOD_ID, "const_float"));
            registry.register(new NodeType<>(NodeBooleanV::new).setRegistryName(Visibilis.MOD_ID, "const_boolean"));
            
            registry.register(new NodeType<>(NodeCosines::new).setRegistryName(Visibilis.MOD_ID, "cosines"));
            registry.register(new NodeType<>(NodeRound::new).setRegistryName(Visibilis.MOD_ID, "round"));
            registry.register(new NodeType<>(NodeRoundDown::new).setRegistryName(Visibilis.MOD_ID, "round_down"));
            registry.register(new NodeType<>(NodeRoundUp::new).setRegistryName(Visibilis.MOD_ID, "round_up"));
            registry.register(new NodeType<>(NodeSines::new).setRegistryName(Visibilis.MOD_ID, "sines"));
            registry.register(new NodeType<>(NodeTangent::new).setRegistryName(Visibilis.MOD_ID, "tangent"));
            
            registry.register(new NodeType<>(NodeAND::new).setRegistryName(Visibilis.MOD_ID, "and"));
            registry.register(new NodeType<>(NodeNAND::new).setRegistryName(Visibilis.MOD_ID, "nand"));
            registry.register(new NodeType<>(NodeNOR::new).setRegistryName(Visibilis.MOD_ID, "nor"));
            registry.register(new NodeType<>(NodeNOT::new).setRegistryName(Visibilis.MOD_ID, "not"));
            registry.register(new NodeType<>(NodeOR::new).setRegistryName(Visibilis.MOD_ID, "or"));
            registry.register(new NodeType<>(NodeXNOR::new).setRegistryName(Visibilis.MOD_ID, "xnor"));
            registry.register(new NodeType<>(NodeXOR::new).setRegistryName(Visibilis.MOD_ID, "xor"));
            
            registry.register(new NodeType<>(NodeBranch::new).setRegistryName(Visibilis.MOD_ID, "branch"));
            registry.register(new NodeType<>(NodeMerge::new).setRegistryName(Visibilis.MOD_ID, "merge"));
            registry.register(new NodeType<>(NodeFor::new).setRegistryName(Visibilis.MOD_ID, "for"));
            registry.register(new NodeType<>(NodeWhile::new).setRegistryName(Visibilis.MOD_ID, "while"));
            
            registry.register(new NodeType<>(NodeFloatCompare::new).setRegistryName(Visibilis.MOD_ID, "equals"));
            registry.register(new NodeType<>(NodeFloatToInteger::new).setRegistryName(Visibilis.MOD_ID, "cast_float_to_integer"));
            
            registry.register(new NodeType<>(NodePrint::new).setRegistryName(Visibilis.MOD_ID, "print"));
        }
        
        @SubscribeEvent
        public static void onModConfigEvent(ModConfig.ModConfigEvent event)
        {
            VConfigHelper.bake(event.getConfig());
        }
    }
}
