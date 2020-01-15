package de.cas_ual_ty.visibilis;

import de.cas_ual_ty.visibilis.config.VConfigHelper;
import de.cas_ual_ty.visibilis.config.VConfiguration;
import de.cas_ual_ty.visibilis.datatype.DataType;
import de.cas_ual_ty.visibilis.node.NodeType;
import de.cas_ual_ty.visibilis.print.item.MessageItem;
import de.cas_ual_ty.visibilis.proxy.IVSidedProxy;
import de.cas_ual_ty.visibilis.registries.VDataTypes;
import de.cas_ual_ty.visibilis.test.VCommandExec;
import de.cas_ual_ty.visibilis.util.VCommandShutdown;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.MinecraftForge;
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
    
    public static Visibilis instance;
    
    public static IVSidedProxy proxy = (IVSidedProxy)DistExecutor.runForDist(() -> de.cas_ual_ty.visibilis.proxy.VProxyClient::new, () -> de.cas_ual_ty.visibilis.proxy.VProxyServer::new);
    
    public static SimpleChannel channel;
    
    public static IForgeRegistry<NodeType<?>> nodeTypesRegistry;
    public static IForgeRegistry<DataType<?>> dataTypesRegistry;
    
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
        
        VDataTypes.addConverters();
    }
    
    @SuppressWarnings({ "rawtypes", "unchecked" })
    public void newRegistry(NewRegistry event)
    {
        Visibilis.nodeTypesRegistry = new RegistryBuilder().setName(new ResourceLocation(Visibilis.MOD_ID, "nodes")).setType(NodeType.class).setMaxID(4096).create();
        Visibilis.dataTypesRegistry = new RegistryBuilder().setName(new ResourceLocation(Visibilis.MOD_ID, "datatypes")).setType(DataType.class).setMaxID(4096).create();
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
        public static void onModConfigEvent(ModConfig.ModConfigEvent event)
        {
            VConfigHelper.bake(event.getConfig());
        }
    }
}
