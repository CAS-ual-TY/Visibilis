package de.cas_ual_ty.visibilis;

import java.util.ArrayList;
import java.util.Collection;

import de.cas_ual_ty.visibilis.command.PrintEquipmentSlotMessage;
import de.cas_ual_ty.visibilis.command.VCommand;
import de.cas_ual_ty.visibilis.config.VConfigHelper;
import de.cas_ual_ty.visibilis.config.VConfiguration;
import de.cas_ual_ty.visibilis.datatype.DataType;
import de.cas_ual_ty.visibilis.node.NodeType;
import de.cas_ual_ty.visibilis.node.player.PlayerMotionMessage;
import de.cas_ual_ty.visibilis.print.capability.IPrintHolder;
import de.cas_ual_ty.visibilis.print.capability.PrintHolder;
import de.cas_ual_ty.visibilis.print.capability.PrintHolderStorage;
import de.cas_ual_ty.visibilis.print.entity.SynchEntityToClientMessage;
import de.cas_ual_ty.visibilis.print.entity.SynchEntityToServerMessage;
import de.cas_ual_ty.visibilis.print.item.IPrintItem;
import de.cas_ual_ty.visibilis.print.item.PrintSynchItemToServerMessage;
import de.cas_ual_ty.visibilis.print.provider.NodeListProviderBase;
import de.cas_ual_ty.visibilis.proxy.IVSidedProxy;
import de.cas_ual_ty.visibilis.registries.VDataTypes;
import de.cas_ual_ty.visibilis.util.VUtility;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.capabilities.CapabilityManager;
import net.minecraftforge.event.AttachCapabilitiesEvent;
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
    public static final String PROTOCOL_VERSION = "2";
    
    public static Visibilis instance;
    public static IVSidedProxy proxy;
    public static SimpleChannel channel;
    
    public static IForgeRegistry<NodeType<?>> NODE_TYPES_REGISTRY;
    public static IForgeRegistry<DataType<?>> DATA_TYPES_REGISTRY;
    
    public Visibilis()
    {
        Visibilis.instance = this;
        Visibilis.proxy = DistExecutor.runForDist(() -> de.cas_ual_ty.visibilis.proxy.VClientProxy::new, () -> de.cas_ual_ty.visibilis.proxy.VServerProxy::new);
        
        IEventBus bus = FMLJavaModLoadingContext.get().getModEventBus();
        bus.addListener(this::init);
        bus.addListener(this::newRegistry);
        bus.<ModConfigEvent>addListener((event) -> VConfigHelper.bake(event.getConfig()));
        
        bus = MinecraftForge.EVENT_BUS;
        bus.addListener(VCommand::execCommand);
        bus.<FMLServerStartingEvent>addListener((event) -> VCommand.register(event.getCommandDispatcher()));
        bus.addListener(this::attachCapabilitiesItemStack);
        
        ModLoadingContext mld = ModLoadingContext.get();
        mld.registerConfig(Type.CLIENT, VConfiguration.CLIENT_SPEC);
        mld.registerConfig(Type.COMMON, VConfiguration.COMMON_SPEC);
    }
    
    private void init(FMLCommonSetupEvent event)
    {
        Visibilis.channel = NetworkRegistry.newSimpleChannel(new ResourceLocation(Visibilis.MOD_ID, "main"),
            () -> Visibilis.PROTOCOL_VERSION,
            Visibilis.PROTOCOL_VERSION::equals,
            Visibilis.PROTOCOL_VERSION::equals);
        Visibilis.channel.registerMessage(0, PrintSynchItemToServerMessage.class, PrintSynchItemToServerMessage::encode, PrintSynchItemToServerMessage::decode, PrintSynchItemToServerMessage::handle);
        Visibilis.channel.registerMessage(1, PlayerMotionMessage.class, PlayerMotionMessage::encode, PlayerMotionMessage::decode, PlayerMotionMessage::handle);
        Visibilis.channel.registerMessage(2, PrintEquipmentSlotMessage.class, PrintEquipmentSlotMessage::encode, PrintEquipmentSlotMessage::decode, PrintEquipmentSlotMessage::handle);
        Visibilis.channel.registerMessage(3, SynchEntityToServerMessage.class, SynchEntityToServerMessage::encode, SynchEntityToServerMessage::decode, SynchEntityToServerMessage::handle);
        Visibilis.channel.registerMessage(4, SynchEntityToClientMessage.class, SynchEntityToClientMessage::encode, SynchEntityToClientMessage::decode, SynchEntityToClientMessage::handle);
        
        VDataTypes.addConverters();
        
        Collection<NodeType<?>> allTypes = Visibilis.NODE_TYPES_REGISTRY.getValues();
        NodeListProviderBase.ALL_NODES = new ArrayList<>(allTypes.size());
        NodeListProviderBase.ALL_NODES.addAll(allTypes);
        
        CapabilityManager.INSTANCE.register(IPrintHolder.class, new PrintHolderStorage(), PrintHolder::new);
    }
    
    @SuppressWarnings({ "rawtypes", "unchecked" })
    private void newRegistry(NewRegistry event)
    {
        Visibilis.NODE_TYPES_REGISTRY = new RegistryBuilder().setName(new ResourceLocation(Visibilis.MOD_ID, "nodes")).setType(NodeType.class).setMaxID(512).create();
        Visibilis.DATA_TYPES_REGISTRY = new RegistryBuilder().setName(new ResourceLocation(Visibilis.MOD_ID, "datatypes")).setType(DataType.class).setMaxID(512).create();
    }
    
    private void attachCapabilitiesItemStack(AttachCapabilitiesEvent<ItemStack> event)
    {
        if(event.getObject() instanceof ItemStack && event.getObject().getItem() instanceof IPrintItem)
        {
            VUtility.attachCapability(event);
        }
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
