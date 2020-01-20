package de.cas_ual_ty.visibilis.registries;

import de.cas_ual_ty.visibilis.Visibilis;
import de.cas_ual_ty.visibilis.node.NodeType;
import de.cas_ual_ty.visibilis.node.base.dtboolean.NodeBooleanV;
import de.cas_ual_ty.visibilis.node.base.dtfloat.NodeFloatV;
import de.cas_ual_ty.visibilis.node.base.dtvector3d.NodeVector3dCreate;
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
        
        registry.register(new NodeType<>(() -> new NodeEvent(Visibilis.MOD_ID, "command"), NodeEvent.class).setRegistryName(Visibilis.MOD_ID, "event_command"));
        registry.register(new NodeType<>(VNodePrintDebug::new, VNodePrintDebug.class).setRegistryName(Visibilis.MOD_ID, "test"));
        
        registry.register(new NodeType<>(NodeAddition::new, NodeAddition.class).setRegistryName(Visibilis.MOD_ID, "addition"));
        registry.register(new NodeType<>(NodeDivision::new, NodeDivision.class).setRegistryName(Visibilis.MOD_ID, "division"));
        registry.register(new NodeType<>(NodeExponentiation::new, NodeExponentiation.class).setRegistryName(Visibilis.MOD_ID, "exponentiation"));
        registry.register(new NodeType<>(NodeLogarithm10::new, NodeLogarithm10.class).setRegistryName(Visibilis.MOD_ID, "logarithm_10"));
        registry.register(new NodeType<>(NodeLogarithm1p::new, NodeLogarithm1p.class).setRegistryName(Visibilis.MOD_ID, "logarithm_1p"));
        registry.register(new NodeType<>(NodeLogarithmE::new, NodeLogarithmE.class).setRegistryName(Visibilis.MOD_ID, "logarithm_e"));
        registry.register(new NodeType<>(NodeModulo::new, NodeModulo.class).setRegistryName(Visibilis.MOD_ID, "modulo"));
        registry.register(new NodeType<>(NodeMultiplication::new, NodeMultiplication.class).setRegistryName(Visibilis.MOD_ID, "multiplication"));
        registry.register(new NodeType<>(NodeRoot::new, NodeRoot.class).setRegistryName(Visibilis.MOD_ID, "root"));
        registry.register(new NodeType<>(NodeSubtraction::new, NodeSubtraction.class).setRegistryName(Visibilis.MOD_ID, "subtraction"));
        registry.register(new NodeType<>(NodeConcatenation::new, NodeConcatenation.class).setRegistryName(Visibilis.MOD_ID, "concatenation"));
        
        registry.register(new NodeType<>(NodeE::new, NodeE.class).setRegistryName(Visibilis.MOD_ID, "e"));
        registry.register(new NodeType<>(NodePi::new, NodePi.class).setRegistryName(Visibilis.MOD_ID, "pi"));
        registry.register(new NodeType<>(NodeSQRT2::new, NodeSQRT2.class).setRegistryName(Visibilis.MOD_ID, "sqrt2"));
        registry.register(new NodeType<>(NodeFloatV::new, NodeFloatV.class).setRegistryName(Visibilis.MOD_ID, "const_float"));
        registry.register(new NodeType<>(NodeBooleanV::new, NodeBooleanV.class).setRegistryName(Visibilis.MOD_ID, "const_boolean"));
        
        registry.register(new NodeType<>(NodeCosines::new, NodeCosines.class).setRegistryName(Visibilis.MOD_ID, "cosines"));
        registry.register(new NodeType<>(NodeRound::new, NodeRound.class).setRegistryName(Visibilis.MOD_ID, "round"));
        registry.register(new NodeType<>(NodeRoundDown::new, NodeRoundDown.class).setRegistryName(Visibilis.MOD_ID, "round_down"));
        registry.register(new NodeType<>(NodeRoundUp::new, NodeRoundUp.class).setRegistryName(Visibilis.MOD_ID, "round_up"));
        registry.register(new NodeType<>(NodeSines::new, NodeSines.class).setRegistryName(Visibilis.MOD_ID, "sines"));
        registry.register(new NodeType<>(NodeTangent::new, NodeTangent.class).setRegistryName(Visibilis.MOD_ID, "tangent"));
        
        registry.register(new NodeType<>(NodeAND::new, NodeAND.class).setRegistryName(Visibilis.MOD_ID, "and"));
        registry.register(new NodeType<>(NodeNAND::new, NodeNAND.class).setRegistryName(Visibilis.MOD_ID, "nand"));
        registry.register(new NodeType<>(NodeNOR::new, NodeNOR.class).setRegistryName(Visibilis.MOD_ID, "nor"));
        registry.register(new NodeType<>(NodeNOT::new, NodeNOT.class).setRegistryName(Visibilis.MOD_ID, "not"));
        registry.register(new NodeType<>(NodeOR::new, NodeOR.class).setRegistryName(Visibilis.MOD_ID, "or"));
        registry.register(new NodeType<>(NodeXNOR::new, NodeXNOR.class).setRegistryName(Visibilis.MOD_ID, "xnor"));
        registry.register(new NodeType<>(NodeXOR::new, NodeXOR.class).setRegistryName(Visibilis.MOD_ID, "xor"));
        
        registry.register(new NodeType<>(NodeBranch::new, NodeBranch.class).setRegistryName(Visibilis.MOD_ID, "branch"));
        registry.register(new NodeType<>(NodeMerge::new, NodeMerge.class).setRegistryName(Visibilis.MOD_ID, "merge"));
        registry.register(new NodeType<>(NodeFor::new, NodeFor.class).setRegistryName(Visibilis.MOD_ID, "for"));
        registry.register(new NodeType<>(NodeWhile::new, NodeWhile.class).setRegistryName(Visibilis.MOD_ID, "while"));
        
        registry.register(new NodeType<>(NodeFloatCompare::new, NodeFloatCompare.class).setRegistryName(Visibilis.MOD_ID, "equals"));
        registry.register(new NodeType<>(NodeFloatToInteger::new, NodeFloatToInteger.class).setRegistryName(Visibilis.MOD_ID, "cast_float_to_integer"));
        
        registry.register(new NodeType<>(NodeVector3dCreate::new, NodeVector3dCreate.class).setRegistryName(Visibilis.MOD_ID, "vector3d_create"));
        registry.register(new NodeType<>(NodeVector3dSplit::new, NodeVector3dSplit.class).setRegistryName(Visibilis.MOD_ID, "vector3d_split"));
        
        registry.register(new NodeType<>(NodePrint::new, NodePrint.class).setRegistryName(Visibilis.MOD_ID, "print"));
    }
}
