package de.cas_ual_ty.visibilis.registries;

import de.cas_ual_ty.visibilis.Visibilis;
import de.cas_ual_ty.visibilis.node.NodeType;
import de.cas_ual_ty.visibilis.node.base.dtboolean.NodeBooleanV;
import de.cas_ual_ty.visibilis.node.base.dtfloat.NodeFloatV;
import de.cas_ual_ty.visibilis.node.base.dtvector3d.NodeVector3dCreate;
import de.cas_ual_ty.visibilis.node.base.dtvector3d.NodeVector3dScale;
import de.cas_ual_ty.visibilis.node.base.dtvector3d.NodeVector3dSplit;
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
import de.cas_ual_ty.visibilis.node.compare.NodeFloatEquals;
import de.cas_ual_ty.visibilis.node.compare.NodeFloatGreater;
import de.cas_ual_ty.visibilis.node.compare.NodeFloatGreaterEquals;
import de.cas_ual_ty.visibilis.node.compare.NodeFloatLess;
import de.cas_ual_ty.visibilis.node.compare.NodeFloatLessEquals;
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
import de.cas_ual_ty.visibilis.test.VNodePrintDebug;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber.Bus;
import net.minecraftforge.registries.IForgeRegistry;
import net.minecraftforge.registries.ObjectHolder;

@EventBusSubscriber(modid = Visibilis.MOD_ID, bus = Bus.MOD)
@ObjectHolder(Visibilis.MOD_ID)
public class VNodeTypes
{
    @SubscribeEvent
    public static void register(RegistryEvent.Register<NodeType<?>> event)
    {
        IForgeRegistry<NodeType<?>> registry = event.getRegistry();
        
        registry.register(new NodeType<>((type) -> new NodeEvent(type, Visibilis.MOD_ID, "command")).setRegistryName(Visibilis.MOD_ID, "event_command"));
        registry.register(new NodeType<>(VNodePrintDebug::new).setRegistryName(Visibilis.MOD_ID, "print_debug"));
        
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
        
        registry.register(new NodeType<>(NodeFloatEquals::new).setRegistryName(Visibilis.MOD_ID, "equals"));
        registry.register(new NodeType<>(NodeFloatGreater::new).setRegistryName(Visibilis.MOD_ID, "greater_than"));
        registry.register(new NodeType<>(NodeFloatGreaterEquals::new).setRegistryName(Visibilis.MOD_ID, "greater_than_or_equals"));
        registry.register(new NodeType<>(NodeFloatLess::new).setRegistryName(Visibilis.MOD_ID, "less_than"));
        registry.register(new NodeType<>(NodeFloatLessEquals::new).setRegistryName(Visibilis.MOD_ID, "less_than_or_equals"));
        
        registry.register(new NodeType<>(NodeFloatToInteger::new).setRegistryName(Visibilis.MOD_ID, "cast_float_to_integer"));
        
        registry.register(new NodeType<>(NodeVector3dCreate::new).setRegistryName(Visibilis.MOD_ID, "vector3d_create"));
        registry.register(new NodeType<>(NodeVector3dSplit::new).setRegistryName(Visibilis.MOD_ID, "vector3d_split"));
        registry.register(new NodeType<>(NodeVector3dScale::new).setRegistryName(Visibilis.MOD_ID, "vector3d_scale"));
        
        registry.register(new NodeType<>(NodePrint::new).setRegistryName(Visibilis.MOD_ID, "print"));
    }
}
