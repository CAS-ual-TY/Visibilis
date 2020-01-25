package de.cas_ual_ty.visibilis.registries;

import de.cas_ual_ty.visibilis.Visibilis;
import de.cas_ual_ty.visibilis.node.NodeType;
import de.cas_ual_ty.visibilis.node.base.bigeneric.NodeBiGenericP;
import de.cas_ual_ty.visibilis.node.base.dtvector3d.NodeVector3dCreate;
import de.cas_ual_ty.visibilis.node.base.dtvector3d.NodeVector3dScale;
import de.cas_ual_ty.visibilis.node.base.dtvector3d.NodeVector3dSplit;
import de.cas_ual_ty.visibilis.node.base.generic.NodeGenericCompare;
import de.cas_ual_ty.visibilis.node.base.generic.NodeGenericConstant;
import de.cas_ual_ty.visibilis.node.base.generic.NodeGenericHardcoded;
import de.cas_ual_ty.visibilis.node.base.generic.NodeGenericP;
import de.cas_ual_ty.visibilis.node.base.generic.NodeGenericP2;
import de.cas_ual_ty.visibilis.node.base.generic.NodeGenericXP2;
import de.cas_ual_ty.visibilis.node.calculate.NodeConcatenation;
import de.cas_ual_ty.visibilis.node.calculate.NodeExponentiation;
import de.cas_ual_ty.visibilis.node.calculate.NodeLogarithm10;
import de.cas_ual_ty.visibilis.node.calculate.NodeLogarithm1p;
import de.cas_ual_ty.visibilis.node.calculate.NodeLogarithmE;
import de.cas_ual_ty.visibilis.node.calculate.NodeRoot;
import de.cas_ual_ty.visibilis.node.event.NodeEvent;
import de.cas_ual_ty.visibilis.node.exec.NodeBranch;
import de.cas_ual_ty.visibilis.node.exec.NodeFor;
import de.cas_ual_ty.visibilis.node.exec.NodeMerge;
import de.cas_ual_ty.visibilis.node.exec.NodeWhile;
import de.cas_ual_ty.visibilis.node.function.NodeRound;
import de.cas_ual_ty.visibilis.node.function.NodeRoundDown;
import de.cas_ual_ty.visibilis.node.function.NodeRoundUp;
import de.cas_ual_ty.visibilis.node.general.NodePrint;
import de.cas_ual_ty.visibilis.test.VNodePrintDebug;
import de.cas_ual_ty.visibilis.util.VNumberHelper.NumberFunctionP2;
import de.cas_ual_ty.visibilis.util.VNumberHelper.NumberFunctionX;
import net.minecraft.util.math.MathHelper;
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
        
        registry.register(NodeGenericXP2.createTypeGenericXP2(VDataTypes.NUMBER,
            new NumberFunctionX<>(
                (ints) ->
                {
                    int r = 0;
                    for(Integer i : ints)
                    {
                        r += i.intValue();
                    }
                    return r;
                },
                (floats) ->
                {
                    float r = 0;
                    for(Float i : floats)
                    {
                        r += i.floatValue();
                    }
                    return r;
                },
                (doubles) ->
                {
                    double r = 0;
                    for(Double i : doubles)
                    {
                        r += i.doubleValue();
                    }
                    return r;
                }),
            new NumberFunctionP2<>(
                (i, j) -> i.intValue() + j.intValue(),
                (i, j) -> i.floatValue() + j.floatValue(),
                (i, j) -> i.doubleValue() + j.doubleValue()))
            .setRegistryName(Visibilis.MOD_ID, "addition"));
        
        registry.register(NodeGenericP2.createTypeGenericP2(VDataTypes.NUMBER,
            new NumberFunctionP2<>(
                (i, j) -> i.intValue() - j.intValue(),
                (i, j) -> i.floatValue() - j.floatValue(),
                (i, j) -> i.doubleValue() - j.doubleValue()))
            .setRegistryName(Visibilis.MOD_ID, "subtraction"));
        
        registry.register(NodeGenericXP2.createTypeGenericXP2(VDataTypes.NUMBER,
            new NumberFunctionX<>(
                (ints) ->
                {
                    int r = 1;
                    for(Integer i : ints)
                    {
                        r *= i.intValue();
                    }
                    return r;
                },
                (floats) ->
                {
                    float r = 1;
                    for(Float i : floats)
                    {
                        r *= i.floatValue();
                    }
                    return r;
                },
                (doubles) ->
                {
                    double r = 1;
                    for(Double i : doubles)
                    {
                        r *= i.doubleValue();
                    }
                    return r;
                }),
            new NumberFunctionP2<>(
                (i, j) -> i.intValue() + j.intValue(),
                (i, j) -> i.floatValue() + j.floatValue(),
                (i, j) -> i.doubleValue() + j.doubleValue()))
            .setRegistryName(Visibilis.MOD_ID, "multiplication"));
        
        registry.register(NodeGenericP2.createTypeGenericP2(VDataTypes.NUMBER,
            new NumberFunctionP2<>(
                (i, j) -> i.intValue() / j.intValue(),
                (i, j) -> i.floatValue() / j.floatValue(),
                (i, j) -> i.doubleValue() / j.doubleValue()),
            new NumberFunctionP2<>(
                (i, j) -> j.intValue() != 0,
                (i, j) -> j.floatValue() != 0,
                (i, j) -> j.doubleValue() != 0))
            .setRegistryName(Visibilis.MOD_ID, "division"));
        
        registry.register(NodeGenericP2.createTypeGenericP2(VDataTypes.NUMBER,
            new NumberFunctionP2<>(
                (i, j) -> i.intValue() % j.intValue(),
                (i, j) -> i.floatValue() % j.floatValue(),
                (i, j) -> i.doubleValue() % j.doubleValue()),
            new NumberFunctionP2<>(
                (i, j) -> j.intValue() != 0,
                (i, j) -> j.floatValue() != 0,
                (i, j) -> j.doubleValue() != 0))
            .setRegistryName(Visibilis.MOD_ID, "modulo"));
        
        registry.register(new NodeType<>(NodeExponentiation::new).setRegistryName(Visibilis.MOD_ID, "exponentiation"));
        registry.register(new NodeType<>(NodeLogarithm10::new).setRegistryName(Visibilis.MOD_ID, "logarithm_10"));
        registry.register(new NodeType<>(NodeLogarithm1p::new).setRegistryName(Visibilis.MOD_ID, "logarithm_1p"));
        registry.register(new NodeType<>(NodeLogarithmE::new).setRegistryName(Visibilis.MOD_ID, "logarithm_e"));
        registry.register(new NodeType<>(NodeRoot::new).setRegistryName(Visibilis.MOD_ID, "root"));
        registry.register(new NodeType<>(NodeConcatenation::new).setRegistryName(Visibilis.MOD_ID, "concatenation"));
        
        registry.register(NodeGenericHardcoded.createTypeGenericConstant(VDataTypes.DOUBLE, Math.E).setRegistryName(Visibilis.MOD_ID, "e"));
        registry.register(NodeGenericHardcoded.createTypeGenericConstant(VDataTypes.DOUBLE, Math.PI).setRegistryName(Visibilis.MOD_ID, "pi"));
        registry.register(NodeGenericHardcoded.createTypeGenericConstant(VDataTypes.FLOAT, MathHelper.SQRT_2).setRegistryName(Visibilis.MOD_ID, "sqrt2"));
        
        registry.register(NodeGenericConstant.createTypeGenericV(VDataTypes.INTEGER).setRegistryName(Visibilis.MOD_ID, "constant_integer"));
        registry.register(NodeGenericConstant.createTypeGenericV(VDataTypes.FLOAT).setRegistryName(Visibilis.MOD_ID, "constant_float"));
        registry.register(NodeGenericConstant.createTypeGenericV(VDataTypes.DOUBLE).setRegistryName(Visibilis.MOD_ID, "constant_double"));
        registry.register(NodeGenericConstant.createTypeGenericV(VDataTypes.BOOLEAN).setRegistryName(Visibilis.MOD_ID, "constant_boolean"));
        
        registry.register(NodeGenericP.createTypeGenericP(VDataTypes.DOUBLE, (d) -> Math.sin(d)).setRegistryName(Visibilis.MOD_ID, "sines"));
        registry.register(NodeGenericP.createTypeGenericP(VDataTypes.DOUBLE, (d) -> Math.cos(d)).setRegistryName(Visibilis.MOD_ID, "cosines"));
        registry.register(NodeGenericP.createTypeGenericP(VDataTypes.DOUBLE, (d) -> Math.tan(d)).setRegistryName(Visibilis.MOD_ID, "tangent"));
        
        registry.register(new NodeType<>(NodeRound::new).setRegistryName(Visibilis.MOD_ID, "round"));
        registry.register(new NodeType<>(NodeRoundDown::new).setRegistryName(Visibilis.MOD_ID, "round_down"));
        registry.register(new NodeType<>(NodeRoundUp::new).setRegistryName(Visibilis.MOD_ID, "round_up"));
        
        registry.register(NodeGenericP.createTypeGenericP(VDataTypes.BOOLEAN,
            (b) ->
            {
                return !b;
            })
            .setRegistryName(Visibilis.MOD_ID, "not"));
        
        registry.register(NodeGenericXP2.createTypeGenericXP2(VDataTypes.BOOLEAN,
            (booleans) ->
            {
                for(boolean b : booleans)
                {
                    if(!b)
                    {
                        return false;
                    }
                }
                return true;
            },
            (b1, b2) ->
            {
                return b1 && b2;
            })
            .setRegistryName(Visibilis.MOD_ID, "and"));
        
        registry.register(NodeGenericXP2.createTypeGenericXP2(VDataTypes.BOOLEAN,
            (booleans) ->
            {
                for(boolean b : booleans)
                {
                    if(!b)
                    {
                        return true;
                    }
                }
                return false;
            },
            (b1, b2) ->
            {
                return !(b1 && b2);
            })
            .setRegistryName(Visibilis.MOD_ID, "nand"));
        
        registry.register(NodeGenericXP2.createTypeGenericXP2(VDataTypes.BOOLEAN,
            (booleans) ->
            {
                for(boolean b : booleans)
                {
                    if(b)
                    {
                        return true;
                    }
                }
                return false;
            },
            (b1, b2) ->
            {
                return b1 || b2;
            })
            .setRegistryName(Visibilis.MOD_ID, "or"));
        
        registry.register(NodeGenericXP2.createTypeGenericXP2(VDataTypes.BOOLEAN,
            (booleans) ->
            {
                for(boolean b : booleans)
                {
                    if(b)
                    {
                        return false;
                    }
                }
                return true;
            },
            (b1, b2) ->
            {
                return !(b1 || b2);
            })
            .setRegistryName(Visibilis.MOD_ID, "nor"));
        
        registry.register(NodeGenericXP2.createTypeGenericXP2(VDataTypes.BOOLEAN,
            (booleans) ->
            {
                boolean flag = false;
                for(boolean b : booleans)
                {
                    if(b)
                    {
                        flag = !flag;
                    }
                }
                return flag;
            },
            (b1, b2) ->
            {
                return b1 != b2;
            })
            .setRegistryName(Visibilis.MOD_ID, "xor"));
        
        registry.register(NodeGenericXP2.createTypeGenericXP2(VDataTypes.BOOLEAN,
            (booleans) ->
            {
                boolean flag = true;
                for(boolean b : booleans)
                {
                    if(b)
                    {
                        flag = !flag;
                    }
                }
                return flag;
            },
            (b1, b2) ->
            {
                return b1 == b2;
            })
            .setRegistryName(Visibilis.MOD_ID, "xnor"));
        
        registry.register(new NodeType<>(NodeBranch::new).setRegistryName(Visibilis.MOD_ID, "branch"));
        registry.register(new NodeType<>(NodeMerge::new).setRegistryName(Visibilis.MOD_ID, "merge"));
        registry.register(new NodeType<>(NodeFor::new).setRegistryName(Visibilis.MOD_ID, "for"));
        registry.register(new NodeType<>(NodeWhile::new).setRegistryName(Visibilis.MOD_ID, "while"));
        
        registry.register(NodeGenericCompare.createTypeGenericCompare(VDataTypes.NUMBER,
            new NumberFunctionP2<>(
                (i, j) -> i.intValue() == j.intValue(),
                (i, j) -> i.floatValue() == j.floatValue(),
                (i, j) -> i.doubleValue() == j.doubleValue()))
            .setRegistryName(Visibilis.MOD_ID, "equals"));
        
        registry.register(NodeGenericCompare.createTypeGenericCompare(VDataTypes.NUMBER,
            new NumberFunctionP2<>(
                (i, j) -> i.intValue() > j.intValue(),
                (i, j) -> i.floatValue() > j.floatValue(),
                (i, j) -> i.doubleValue() > j.doubleValue()))
            .setRegistryName(Visibilis.MOD_ID, "greater_than"));
        
        registry.register(NodeGenericCompare.createTypeGenericCompare(VDataTypes.NUMBER,
            new NumberFunctionP2<>(
                (i, j) -> i.intValue() >= j.intValue(),
                (i, j) -> i.floatValue() >= j.floatValue(),
                (i, j) -> i.doubleValue() >= j.doubleValue()))
            .setRegistryName(Visibilis.MOD_ID, "greater_than_or_equals"));
        
        registry.register(NodeGenericCompare.createTypeGenericCompare(VDataTypes.NUMBER,
            new NumberFunctionP2<>(
                (i, j) -> i.intValue() < j.intValue(),
                (i, j) -> i.floatValue() < j.floatValue(),
                (i, j) -> i.doubleValue() < j.doubleValue()))
            .setRegistryName(Visibilis.MOD_ID, "less_than"));
        
        registry.register(NodeGenericCompare.createTypeGenericCompare(VDataTypes.NUMBER,
            new NumberFunctionP2<>(
                (i, j) -> i.intValue() <= j.intValue(),
                (i, j) -> i.floatValue() <= j.floatValue(),
                (i, j) -> i.doubleValue() <= j.doubleValue()))
            .setRegistryName(Visibilis.MOD_ID, "less_than_or_equals"));
        
        registry.register(NodeBiGenericP.createTypeBiGenericP(VDataTypes.INTEGER, VDataTypes.FLOAT, (f) -> f.intValue()).setRegistryName(Visibilis.MOD_ID, "cast_float_to_integer"));
        registry.register(NodeBiGenericP.createTypeBiGenericP(VDataTypes.INTEGER, VDataTypes.DOUBLE, (d) -> d.intValue()).setRegistryName(Visibilis.MOD_ID, "cast_double_to_integer"));
        registry.register(NodeBiGenericP.createTypeBiGenericP(VDataTypes.FLOAT, VDataTypes.DOUBLE, (f) -> f.floatValue()).setRegistryName(Visibilis.MOD_ID, "cast_double_to_float"));
        
        registry.register(new NodeType<>(NodeVector3dCreate::new).setRegistryName(Visibilis.MOD_ID, "vector3d_create"));
        registry.register(new NodeType<>(NodeVector3dSplit::new).setRegistryName(Visibilis.MOD_ID, "vector3d_split"));
        registry.register(new NodeType<>(NodeVector3dScale::new).setRegistryName(Visibilis.MOD_ID, "vector3d_scale"));
        
        registry.register(new NodeType<>(NodePrint::new).setRegistryName(Visibilis.MOD_ID, "print"));
    }
}
