package de.cas_ual_ty.visibilis;

import de.cas_ual_ty.visibilis.command.MessagePrintEquipmentSlot;
import de.cas_ual_ty.visibilis.command.VCommand;
import de.cas_ual_ty.visibilis.config.VConfigHelper;
import de.cas_ual_ty.visibilis.config.VConfiguration;
import de.cas_ual_ty.visibilis.datatype.DataType;
import de.cas_ual_ty.visibilis.node.NodeType;
import de.cas_ual_ty.visibilis.node.player.MessagePlayerMotion;
import de.cas_ual_ty.visibilis.print.item.MessageItem;
import de.cas_ual_ty.visibilis.proxy.IVSidedProxy;
import de.cas_ual_ty.visibilis.registries.VDataTypes;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.RegistryEvent.NewRegistry;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig.ModConfigEvent;
import net.minecraftforge.fml.config.ModConfig.Type;
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
        bus.<ModConfigEvent>addListener((event) -> VConfigHelper.bake(event.getConfig()));
        
        bus = MinecraftForge.EVENT_BUS;
        bus.addListener(VCommand::execCommand);
        bus.<FMLServerStartingEvent>addListener((event) -> VCommand.register(event.getCommandDispatcher()));
        
        ModLoadingContext mld = ModLoadingContext.get();
        mld.registerConfig(Type.CLIENT, VConfiguration.CLIENT_SPEC);
        mld.registerConfig(Type.COMMON, VConfiguration.COMMON_SPEC);
    }
    
    public void init(FMLCommonSetupEvent event)
    {
        Visibilis.channel = NetworkRegistry.newSimpleChannel(new ResourceLocation(Visibilis.MOD_ID, "main"),
            () -> Visibilis.PROTOCOL_VERSION,
            Visibilis.PROTOCOL_VERSION::equals,
            Visibilis.PROTOCOL_VERSION::equals);
        Visibilis.channel.registerMessage(0, MessageItem.class, MessageItem::encode, MessageItem::decode, MessageItem::handle);
        Visibilis.channel.registerMessage(1, MessagePlayerMotion.class, MessagePlayerMotion::encode, MessagePlayerMotion::decode, MessagePlayerMotion::handle);
        Visibilis.channel.registerMessage(2, MessagePrintEquipmentSlot.class, MessagePrintEquipmentSlot::encode, MessagePrintEquipmentSlot::decode, MessagePrintEquipmentSlot::handle);
        
        VDataTypes.addConverters();
    }
    
    @SuppressWarnings({ "rawtypes", "unchecked" })
    public void newRegistry(NewRegistry event)
    {
        Visibilis.nodeTypesRegistry = new RegistryBuilder().setName(new ResourceLocation(Visibilis.MOD_ID, "nodes")).setType(NodeType.class).setMaxID(4096).create();
        Visibilis.dataTypesRegistry = new RegistryBuilder().setName(new ResourceLocation(Visibilis.MOD_ID, "datatypes")).setType(DataType.class).setMaxID(4096).create();
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
}
