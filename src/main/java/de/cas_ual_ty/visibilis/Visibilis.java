package de.cas_ual_ty.visibilis;

import de.cas_ual_ty.visibilis.handler.VEventHandler;
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
import de.cas_ual_ty.visibilis.node.constant.NodeConstBoolean;
import de.cas_ual_ty.visibilis.node.constant.NodeConstNumber;
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
import de.cas_ual_ty.visibilis.node.logic.NodeAND;
import de.cas_ual_ty.visibilis.node.logic.NodeNAND;
import de.cas_ual_ty.visibilis.node.logic.NodeNOR;
import de.cas_ual_ty.visibilis.node.logic.NodeNOT;
import de.cas_ual_ty.visibilis.node.logic.NodeOR;
import de.cas_ual_ty.visibilis.node.logic.NodeXNOR;
import de.cas_ual_ty.visibilis.node.logic.NodeXOR;
import de.cas_ual_ty.visibilis.print.impl.item.MessageItem;
import de.cas_ual_ty.visibilis.proxy.IVSidedProxy;
import de.cas_ual_ty.visibilis.test.VCommandExec;
import de.cas_ual_ty.visibilis.test.VItemTest;
import de.cas_ual_ty.visibilis.test.VNodeTest;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber.Bus;
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
    
    public static VEventHandler eventHandler;
    
    public static SimpleChannel channel;
    
    public Visibilis()
    {
        Visibilis.instance = this;
        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::init);
    }
    
    public void init(FMLCommonSetupEvent event)
    {
        Visibilis.proxy.preInit();
        
        MinecraftForge.EVENT_BUS.register((Visibilis.eventHandler = new VEventHandler()));
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
    }
    
    public void registerNodes()
    {
        VRegistry.INSTANCE.registerNode(NodeEvent.class, Visibilis.MOD_ID, "event");
        VRegistry.INSTANCE.registerNode(VNodeTest.class, Visibilis.MOD_ID, "test");
        
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
        VRegistry.INSTANCE.registerNode(NodePi.class, Visibilis.MOD_ID, "pi");
        VRegistry.INSTANCE.registerNode(NodeSQRT2.class, Visibilis.MOD_ID, "sqrt2");
        VRegistry.INSTANCE.registerNode(NodeConstNumber.class, Visibilis.MOD_ID, "const_number");
        VRegistry.INSTANCE.registerNode(NodeConstBoolean.class, Visibilis.MOD_ID, "const_boolean");
        
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
        
        VRegistry.INSTANCE.registerNode(NodeBranch.class, Visibilis.MOD_ID, "branch");
        VRegistry.INSTANCE.registerNode(NodeMerge.class, Visibilis.MOD_ID, "merge");
        VRegistry.INSTANCE.registerNode(NodeFor.class, Visibilis.MOD_ID, "for");
        VRegistry.INSTANCE.registerNode(NodeWhile.class, Visibilis.MOD_ID, "while");
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
    }
}
