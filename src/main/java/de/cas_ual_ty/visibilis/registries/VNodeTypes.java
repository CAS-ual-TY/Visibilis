package de.cas_ual_ty.visibilis.registries;

import de.cas_ual_ty.visibilis.Visibilis;
import de.cas_ual_ty.visibilis.node.Node;
import de.cas_ual_ty.visibilis.node.NodeEvent;
import de.cas_ual_ty.visibilis.node.NodeType;
import de.cas_ual_ty.visibilis.node.base.bigeneric.NodeBiGenericP;
import de.cas_ual_ty.visibilis.node.base.generic.NodeGenericCompare;
import de.cas_ual_ty.visibilis.node.base.generic.NodeGenericConstant;
import de.cas_ual_ty.visibilis.node.base.generic.NodeGenericHardcoded;
import de.cas_ual_ty.visibilis.node.base.generic.NodeGenericP;
import de.cas_ual_ty.visibilis.node.base.generic.NodeGenericP2;
import de.cas_ual_ty.visibilis.node.base.generic.NodeGenericXP2;
import de.cas_ual_ty.visibilis.node.base.trigeneric.NodeTriGenericP2;
import de.cas_ual_ty.visibilis.node.exec.NodeBranch;
import de.cas_ual_ty.visibilis.node.exec.NodeFor;
import de.cas_ual_ty.visibilis.node.exec.NodeMerge;
import de.cas_ual_ty.visibilis.node.exec.NodeWhile;
import de.cas_ual_ty.visibilis.node.general.NodePrint;
import de.cas_ual_ty.visibilis.node.player.NodeGetPlayerOptional;
import de.cas_ual_ty.visibilis.node.player.NodeSetPlayerTransform;
import de.cas_ual_ty.visibilis.node.player.NodeSplitPlayer;
import de.cas_ual_ty.visibilis.node.vector3d.NodeVector3dCreate;
import de.cas_ual_ty.visibilis.node.vector3d.NodeVector3dSplit;
import de.cas_ual_ty.visibilis.test.VNodePrintDebug;
import de.cas_ual_ty.visibilis.util.VNumberHelper.NumberFunctionP;
import de.cas_ual_ty.visibilis.util.VNumberHelper.NumberFunctionP2;
import de.cas_ual_ty.visibilis.util.VNumberHelper.NumberFunctionX;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
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
    public static final NodeType<Node> EVENT_COMMAND = null;
    public static final NodeType<Node> PRINT_DEBUG = null;
    
    public static final NodeType<Node> ADDITION = null;
    public static final NodeType<Node> SUBTRACTION = null;
    public static final NodeType<Node> MULTIPLICATION = null;
    public static final NodeType<Node> DIVISION = null;
    public static final NodeType<Node> MODULO = null;
    public static final NodeType<Node> EXPONENTIATION = null;
    
    public static final NodeType<Node> LOGARITHM_10 = null;
    public static final NodeType<Node> LOGARITHM_1P = null;
    public static final NodeType<Node> LOGARITHM_E = null;
    public static final NodeType<Node> ROOT = null;
    
    public static final NodeType<Node> CONCATENATION = null;
    
    public static final NodeType<Node> E = null;
    public static final NodeType<Node> PI = null;
    public static final NodeType<Node> SQRT_2 = null;
    
    public static final NodeType<Node> CONSTANT_INTEGER = null;
    public static final NodeType<Node> CONSTANT_FLOAT = null;
    public static final NodeType<Node> CONSTANT_DOUBLE = null;
    public static final NodeType<Node> CONSTANT_BOOLEAN = null;
    
    public static final NodeType<Node> SINES = null;
    public static final NodeType<Node> COSINES = null;
    public static final NodeType<Node> TANGENT = null;
    
    public static final NodeType<Node> ROUND = null;
    public static final NodeType<Node> FLOOR = null;
    public static final NodeType<Node> CEIL = null;
    
    public static final NodeType<Node> NOT = null;
    public static final NodeType<Node> AND = null;
    public static final NodeType<Node> NAND = null;
    public static final NodeType<Node> OR = null;
    public static final NodeType<Node> NOR = null;
    public static final NodeType<Node> XOR = null;
    public static final NodeType<Node> XNOR = null;
    
    public static final NodeType<Node> BRANCH = null;
    public static final NodeType<Node> MERGE = null;
    public static final NodeType<Node> FOR = null;
    public static final NodeType<Node> WHILE = null;
    
    public static final NodeType<Node> EQUAL_TO = null;
    public static final NodeType<Node> NOT_EQUAL_TO = null;
    public static final NodeType<Node> GREATER_THAN = null;
    public static final NodeType<Node> GREATER_THAN_OR_EQUAL_TO = null;
    public static final NodeType<Node> LESS_THAN = null;
    public static final NodeType<Node> LESS_THAN_OR_EQUAL_TO = null;
    
    public static final NodeType<Node> CAST_NUMBER_TO_INTEGER = null;
    public static final NodeType<Node> CAST_NUMBER_TO_FLOAT = null;
    public static final NodeType<Node> CAST_NUMBER_TO_DOUBLE = null;
    
    public static final NodeType<Node> VECTOR3D_CREATE = null;
    public static final NodeType<Node> VECTOR3D_SPLIT = null;
    public static final NodeType<Node> VECTOR3D_SCALE = null;
    public static final NodeType<Node> VECTOR3D_ADDITION = null;
    public static final NodeType<Node> VECTOR3D_SUBTRACTION = null;
    public static final NodeType<Node> VECTOR3D_NORMALIZATION = null;
    public static final NodeType<Node> VECTOR3D_DOT_PRODUCT = null;
    public static final NodeType<Node> VECTOR3D_CROSS_PRODUCT = null;
    public static final NodeType<Node> VECTOR3D_LENGTH = null;
    public static final NodeType<Node> VECTOR3D_LENGTH_SQUARED = null;
    
    public static final NodeType<Node> PRINT = null;
    
    public static final NodeType<Node> GET_PLAYER_OPTIONAL = null;
    public static final NodeType<Node> SPLIT_PLAYER = null;
    public static final NodeType<Node> SET_PLAYER_TRANSFORM = null;
    
    @SubscribeEvent
    public static void register(RegistryEvent.Register<NodeType<? extends Node>> event)
    {
        IForgeRegistry<NodeType<? extends Node>> registry = event.getRegistry();
        
        registry.register(new NodeType<>((type) -> new NodeEvent(type, Visibilis.MOD_ID, "command")).setRegistryName(Visibilis.MOD_ID, "event_command"));
        registry.register(new NodeType<>(VNodePrintDebug::new).setRegistryName(Visibilis.MOD_ID, "print_debug"));
        
        registry.register(new NodeType<>(NodeGenericXP2.createTypeGenericXP2(VDataTypes.NUMBER,
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
                (i, j) -> i.doubleValue() + j.doubleValue())))
                    .setRegistryName(Visibilis.MOD_ID, "addition"));
        
        registry.register(new NodeType<>(NodeGenericP2.createTypeGenericP2(VDataTypes.NUMBER,
            new NumberFunctionP2<>(
                (i, j) -> i.intValue() - j.intValue(),
                (i, j) -> i.floatValue() - j.floatValue(),
                (i, j) -> i.doubleValue() - j.doubleValue())))
                    .setRegistryName(Visibilis.MOD_ID, "subtraction"));
        
        registry.register(new NodeType<>(NodeGenericXP2.createTypeGenericXP2(VDataTypes.NUMBER,
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
                (i, j) -> i.doubleValue() + j.doubleValue())))
                    .setRegistryName(Visibilis.MOD_ID, "multiplication"));
        
        registry.register(new NodeType<>(NodeGenericP2.createTypeGenericP2(VDataTypes.NUMBER,
            new NumberFunctionP2<>(
                (i, j) -> i.intValue() / j.intValue(),
                (i, j) -> i.floatValue() / j.floatValue(),
                (i, j) -> i.doubleValue() / j.doubleValue()),
            new NumberFunctionP2<>(
                (i, j) -> j.intValue() != 0,
                (i, j) -> j.floatValue() != 0,
                (i, j) -> j.doubleValue() != 0)))
                    .setRegistryName(Visibilis.MOD_ID, "division"));
        
        registry.register(new NodeType<>(NodeGenericP2.createTypeGenericP2(VDataTypes.NUMBER,
            new NumberFunctionP2<>(
                (i, j) -> i.intValue() % j.intValue(),
                (i, j) -> i.floatValue() % j.floatValue(),
                (i, j) -> i.doubleValue() % j.doubleValue()),
            new NumberFunctionP2<>(
                (i, j) -> j.intValue() != 0,
                (i, j) -> j.floatValue() != 0,
                (i, j) -> j.doubleValue() != 0)))
                    .setRegistryName(Visibilis.MOD_ID, "modulo"));
        
        registry.register(new NodeType<>(NodeGenericP2.createTypeGenericP2(VDataTypes.NUMBER,
            new NumberFunctionP2<>(
                (i, j) ->
                {
                    int r = 1;
                    for(int k = 0; k < j; ++k)
                    {
                        r *= i;
                    }
                    return r;
                },
                (i, j) -> (float)Math.pow(i.floatValue(), j.floatValue()),
                (i, j) -> Math.pow(i.doubleValue(), j.doubleValue()))))
                    .setRegistryName(Visibilis.MOD_ID, "exponentiation"));
        
        registry.register(new NodeType<>(NodeGenericP.createTypeGenericP(VDataTypes.DOUBLE, (d) -> Math.log10(d))).setRegistryName(Visibilis.MOD_ID, "logarithm_10"));
        registry.register(new NodeType<>(NodeGenericP.createTypeGenericP(VDataTypes.DOUBLE, (d) -> Math.log1p(d))).setRegistryName(Visibilis.MOD_ID, "logarithm_1p"));
        registry.register(new NodeType<>(NodeGenericP.createTypeGenericP(VDataTypes.DOUBLE, (d) -> Math.log(d))).setRegistryName(Visibilis.MOD_ID, "logarithm_e"));
        registry.register(new NodeType<>(NodeGenericP2.createTypeGenericP2(VDataTypes.DOUBLE, (d1, d2) -> Math.pow(Math.E, Math.log(d1.doubleValue()) / d2.doubleValue()))).setRegistryName(Visibilis.MOD_ID, "root"));
        
        registry.register(new NodeType<>(NodeGenericXP2.createTypeGenericXP2(VDataTypes.STRING,
            (ss) ->
            {
                String r = "";
                for(String s : ss)
                {
                    r += s;
                }
                return r;
            },
            (s1, s2) ->
            {
                return s1 + s2;
            })).setRegistryName(Visibilis.MOD_ID, "concatenation"));
        
        registry.register(new NodeType<>(NodeGenericHardcoded.createTypeGenericConstant(VDataTypes.DOUBLE, Math.E)).setRegistryName(Visibilis.MOD_ID, "e"));
        registry.register(new NodeType<>(NodeGenericHardcoded.createTypeGenericConstant(VDataTypes.DOUBLE, Math.PI)).setRegistryName(Visibilis.MOD_ID, "pi"));
        registry.register(new NodeType<>(NodeGenericHardcoded.createTypeGenericConstant(VDataTypes.FLOAT, MathHelper.SQRT_2)).setRegistryName(Visibilis.MOD_ID, "sqrt_2"));
        
        registry.register(new NodeType<>(NodeGenericConstant.createTypeGenericV(VDataTypes.INTEGER)).setRegistryName(Visibilis.MOD_ID, "constant_integer"));
        registry.register(new NodeType<>(NodeGenericConstant.createTypeGenericV(VDataTypes.FLOAT)).setRegistryName(Visibilis.MOD_ID, "constant_float"));
        registry.register(new NodeType<>(NodeGenericConstant.createTypeGenericV(VDataTypes.DOUBLE)).setRegistryName(Visibilis.MOD_ID, "constant_double"));
        registry.register(new NodeType<>(NodeGenericConstant.createTypeGenericV(VDataTypes.BOOLEAN)).setRegistryName(Visibilis.MOD_ID, "constant_boolean"));
        
        registry.register(new NodeType<>(NodeGenericP.createTypeGenericP(VDataTypes.DOUBLE, (d) -> Math.sin(d))).setRegistryName(Visibilis.MOD_ID, "sines"));
        registry.register(new NodeType<>(NodeGenericP.createTypeGenericP(VDataTypes.DOUBLE, (d) -> Math.cos(d))).setRegistryName(Visibilis.MOD_ID, "cosines"));
        registry.register(new NodeType<>(NodeGenericP.createTypeGenericP(VDataTypes.DOUBLE, (d) -> Math.tan(d))).setRegistryName(Visibilis.MOD_ID, "tangent"));
        
        registry.register(new NodeType<>(NodeBiGenericP.createTypeBiGenericP(VDataTypes.INTEGER, VDataTypes.NUMBER,
            new NumberFunctionP<>(
                (i) -> i.intValue(),
                (i) -> Math.round(i.floatValue()),
                (i) -> (int)Math.round(i.doubleValue())))).setRegistryName(Visibilis.MOD_ID, "round"));
        
        registry.register(new NodeType<>(NodeBiGenericP.createTypeBiGenericP(VDataTypes.INTEGER, VDataTypes.NUMBER,
            new NumberFunctionP<>(
                (i) -> i.intValue(),
                (i) -> MathHelper.floor(i.floatValue()),
                (i) -> MathHelper.floor(i.doubleValue())))).setRegistryName(Visibilis.MOD_ID, "floor"));
        
        registry.register(new NodeType<>(NodeBiGenericP.createTypeBiGenericP(VDataTypes.INTEGER, VDataTypes.NUMBER,
            new NumberFunctionP<>(
                (i) -> i.intValue(),
                (i) -> MathHelper.ceil(i.floatValue()),
                (i) -> MathHelper.ceil(i.doubleValue())))).setRegistryName(Visibilis.MOD_ID, "ceil"));
        
        registry.register(new NodeType<>(NodeGenericP.createTypeGenericP(VDataTypes.BOOLEAN,
            (b) ->
            {
                return !b;
            })).setRegistryName(Visibilis.MOD_ID, "not"));
        
        registry.register(new NodeType<>(NodeGenericXP2.createTypeGenericXP2(VDataTypes.BOOLEAN,
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
            })).setRegistryName(Visibilis.MOD_ID, "and"));
        
        registry.register(new NodeType<>(NodeGenericXP2.createTypeGenericXP2(VDataTypes.BOOLEAN,
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
            })).setRegistryName(Visibilis.MOD_ID, "nand"));
        
        registry.register(new NodeType<>(NodeGenericXP2.createTypeGenericXP2(VDataTypes.BOOLEAN,
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
            })).setRegistryName(Visibilis.MOD_ID, "or"));
        
        registry.register(new NodeType<>(NodeGenericXP2.createTypeGenericXP2(VDataTypes.BOOLEAN,
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
            })).setRegistryName(Visibilis.MOD_ID, "nor"));
        
        registry.register(new NodeType<>(NodeGenericXP2.createTypeGenericXP2(VDataTypes.BOOLEAN,
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
            })).setRegistryName(Visibilis.MOD_ID, "xor"));
        
        registry.register(new NodeType<>(NodeGenericXP2.createTypeGenericXP2(VDataTypes.BOOLEAN,
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
            })).setRegistryName(Visibilis.MOD_ID, "xnor"));
        
        registry.register(new NodeType<>(NodeBranch::new).setRegistryName(Visibilis.MOD_ID, "branch"));
        registry.register(new NodeType<>(NodeMerge::new).setRegistryName(Visibilis.MOD_ID, "merge"));
        registry.register(new NodeType<>(NodeFor::new).setRegistryName(Visibilis.MOD_ID, "for"));
        registry.register(new NodeType<>(NodeWhile::new).setRegistryName(Visibilis.MOD_ID, "while"));
        
        registry.register(new NodeType<>(NodeGenericCompare.createTypeGenericCompare(VDataTypes.NUMBER,
            new NumberFunctionP2<>(
                (i, j) -> i.intValue() == j.intValue(),
                (i, j) -> i.floatValue() == j.floatValue(),
                (i, j) -> i.doubleValue() == j.doubleValue()))).setRegistryName(Visibilis.MOD_ID, "equal_to"));
        
        registry.register(new NodeType<>(NodeGenericCompare.createTypeGenericCompare(VDataTypes.NUMBER,
            new NumberFunctionP2<>(
                (i, j) -> i.intValue() != j.intValue(),
                (i, j) -> i.floatValue() != j.floatValue(),
                (i, j) -> i.doubleValue() != j.doubleValue()))).setRegistryName(Visibilis.MOD_ID, "not_equal_to"));
        
        registry.register(new NodeType<>(NodeGenericCompare.createTypeGenericCompare(VDataTypes.NUMBER,
            new NumberFunctionP2<>(
                (i, j) -> i.intValue() > j.intValue(),
                (i, j) -> i.floatValue() > j.floatValue(),
                (i, j) -> i.doubleValue() > j.doubleValue()))).setRegistryName(Visibilis.MOD_ID, "greater_than"));
        
        registry.register(new NodeType<>(NodeGenericCompare.createTypeGenericCompare(VDataTypes.NUMBER,
            new NumberFunctionP2<>(
                (i, j) -> i.intValue() >= j.intValue(),
                (i, j) -> i.floatValue() >= j.floatValue(),
                (i, j) -> i.doubleValue() >= j.doubleValue()))).setRegistryName(Visibilis.MOD_ID, "greater_than_or_equal_to"));
        
        registry.register(new NodeType<>(NodeGenericCompare.createTypeGenericCompare(VDataTypes.NUMBER,
            new NumberFunctionP2<>(
                (i, j) -> i.intValue() < j.intValue(),
                (i, j) -> i.floatValue() < j.floatValue(),
                (i, j) -> i.doubleValue() < j.doubleValue()))).setRegistryName(Visibilis.MOD_ID, "less_than"));
        
        registry.register(new NodeType<>(NodeGenericCompare.createTypeGenericCompare(VDataTypes.NUMBER,
            new NumberFunctionP2<>(
                (i, j) -> i.intValue() <= j.intValue(),
                (i, j) -> i.floatValue() <= j.floatValue(),
                (i, j) -> i.doubleValue() <= j.doubleValue()))).setRegistryName(Visibilis.MOD_ID, "less_than_or_equal_to"));
        
        registry.register(new NodeType<>(NodeBiGenericP.createTypeBiGenericP(VDataTypes.INTEGER, VDataTypes.NUMBER, (n) -> n.intValue())).setRegistryName(Visibilis.MOD_ID, "cast_number_to_integer"));
        registry.register(new NodeType<>(NodeBiGenericP.createTypeBiGenericP(VDataTypes.FLOAT, VDataTypes.NUMBER, (n) -> n.floatValue())).setRegistryName(Visibilis.MOD_ID, "cast_number_to_float"));
        registry.register(new NodeType<>(NodeBiGenericP.createTypeBiGenericP(VDataTypes.DOUBLE, VDataTypes.NUMBER, (n) -> n.doubleValue())).setRegistryName(Visibilis.MOD_ID, "cast_number_to_double"));
        
        registry.register(new NodeType<>(NodeVector3dCreate::new).setRegistryName(Visibilis.MOD_ID, "vector3d_create"));
        registry.register(new NodeType<>(NodeVector3dSplit::new).setRegistryName(Visibilis.MOD_ID, "vector3d_split"));
        
        registry.register(new NodeType<>(NodeTriGenericP2.createTypeTriGenericP2(VDataTypes.VECTOR3D, VDataTypes.VECTOR3D, VDataTypes.NUMBER, (vec, n) -> vec.scale(n.doubleValue()))).setRegistryName(Visibilis.MOD_ID, "vector3d_scale"));
        registry.register(new NodeType<>(NodeGenericXP2.createTypeGenericXP2(VDataTypes.VECTOR3D,
            (vecs) ->
            {
                Vec3d r = Vec3d.ZERO;
                for(Vec3d vec : vecs)
                {
                    r = r.add(vec);
                }
                return r;
            },
            (vec1, vec2) -> vec1.add(vec2))).setRegistryName(Visibilis.MOD_ID, "vector3d_addition"));
        registry.register(new NodeType<>(NodeGenericP2.createTypeGenericP2(VDataTypes.VECTOR3D, (vec1, vec2) -> vec1.subtract(vec2))).setRegistryName(Visibilis.MOD_ID, "vector3d_subtraction"));
        registry.register(new NodeType<>(NodeGenericP.createTypeGenericP(VDataTypes.VECTOR3D, (vec1) -> vec1.normalize())).setRegistryName(Visibilis.MOD_ID, "vector3d_normalization"));
        registry.register(new NodeType<>(NodeTriGenericP2.createTypeTriGenericP2(VDataTypes.DOUBLE, VDataTypes.VECTOR3D, VDataTypes.VECTOR3D, (vec1, vec2) -> vec1.dotProduct(vec2))).setRegistryName(Visibilis.MOD_ID, "vector3d_dot_product"));
        registry.register(new NodeType<>(NodeGenericP2.createTypeGenericP2(VDataTypes.VECTOR3D, (vec1, vec2) -> vec1.crossProduct(vec2))).setRegistryName(Visibilis.MOD_ID, "vector3d_cross_product"));
        registry.register(new NodeType<>(NodeBiGenericP.createTypeBiGenericP(VDataTypes.DOUBLE, VDataTypes.VECTOR3D, (vec) -> vec.length())).setRegistryName(Visibilis.MOD_ID, "vector3d_length"));
        registry.register(new NodeType<>(NodeBiGenericP.createTypeBiGenericP(VDataTypes.DOUBLE, VDataTypes.VECTOR3D, (vec) -> vec.lengthSquared())).setRegistryName(Visibilis.MOD_ID, "vector3d_length_squared"));
        
        registry.register(new NodeType<>(NodePrint::new).setRegistryName(Visibilis.MOD_ID, "print"));
        
        registry.register(new NodeType<>(NodeGetPlayerOptional::new).setRegistryName(Visibilis.MOD_ID, "get_player_optional"));
        registry.register(new NodeType<>(NodeSplitPlayer::new).setRegistryName(Visibilis.MOD_ID, "split_player"));
        registry.register(new NodeType<>(NodeSetPlayerTransform::new).setRegistryName(Visibilis.MOD_ID, "set_player_transform"));
    }
}
