package de.cas_ual_ty.visibilis.print.provider;

import java.util.ArrayList;
import java.util.List;

import de.cas_ual_ty.visibilis.node.Node;
import de.cas_ual_ty.visibilis.node.NodeType;
import de.cas_ual_ty.visibilis.registries.VNodeTypes;

public class NodeListProviderBase extends NodeListProvider
{
    protected List<NodeType<?>> typeList;
    protected List<Node> list;
    
    public NodeListProviderBase(List<NodeType<?>> typeList)
    {
        this.typeList = typeList;
        this.list = this.buildNodeList();
    }
    
    public NodeListProviderBase()
    {
        this(NodeListProviderBase.addAllNodesToList(new ArrayList<>()));
    }
    
    @Override
    public List<NodeType<?>> getAvailableNodeTypes()
    {
        return this.typeList;
    }
    
    protected List<Node> buildNodeList()
    {
        List<Node> list = new ArrayList<>(this.getAvailableNodeTypes().size());
        for(NodeType<?> type : this.getAvailableNodeTypes())
        {
            list.add(type.instantiate());
        }
        return list;
    }
    
    @Override
    public List<Node> getAvailableNodes()
    {
        return this.list;
    }
    
    public static List<NodeType<?>> addAllNodesToList(List<NodeType<?>> list)
    {
        NodeListProviderBase.debug(list);
        NodeListProviderBase.exec(list);
        NodeListProviderBase.general(list);
        NodeListProviderBase.base(list);
        NodeListProviderBase.player(list);
        NodeListProviderBase.world(list);
        return list;
    }
    
    public static List<NodeType<?>> debug(List<NodeType<?>> list)
    {
        list.add(VNodeTypes.EVENT_COMMAND);
        list.add(VNodeTypes.EVENT_RIGHT_CLICK);
        list.add(VNodeTypes.PRINT_DEBUG);
        list.add(VNodeTypes.PRINT);
        return list;
    }
    
    public static List<NodeType<?>> exec(List<NodeType<?>> list)
    {
        list.add(VNodeTypes.BRANCH);
        list.add(VNodeTypes.MERGE);
        list.add(VNodeTypes.FOR);
        list.add(VNodeTypes.WHILE);
        list.add(VNodeTypes.MULTI_EQUALS_BRANCH);
        return list;
    }
    
    public static List<NodeType<?>> general(List<NodeType<?>> list)
    {
        list.add(VNodeTypes.MULTI_CONSTANT);
        return list;
    }
    
    public static List<NodeType<?>> player(List<NodeType<?>> list)
    {
        list.add(VNodeTypes.GET_PLAYER_OPTIONAL);
        list.add(VNodeTypes.SPLIT_PLAYER);
        list.add(VNodeTypes.SET_PLAYER_TRANSFORM);
        return list;
    }
    
    // ----------------------------------------
    
    public static List<NodeType<?>> base(List<NodeType<?>> list)
    {
        NodeListProviderBase.calculation(list);
        NodeListProviderBase.extendedCalculation(list);
        NodeListProviderBase.string(list);
        NodeListProviderBase.numberConstants(list);
        NodeListProviderBase.numberSerialization(list);
        NodeListProviderBase.trigonometry(list);
        NodeListProviderBase.logic(list);
        NodeListProviderBase.numberComparisons(list);
        NodeListProviderBase.numberRoundingNCasting(list);
        NodeListProviderBase.vectors(list);
        return list;
    }
    
    public static List<NodeType<?>> calculation(List<NodeType<?>> list)
    {
        list.add(VNodeTypes.ADDITION);
        list.add(VNodeTypes.SUBTRACTION);
        list.add(VNodeTypes.MULTIPLICATION);
        list.add(VNodeTypes.DIVISION);
        list.add(VNodeTypes.MODULO);
        list.add(VNodeTypes.EXPONENTIATION);
        return list;
    }
    
    public static List<NodeType<?>> extendedCalculation(List<NodeType<?>> list)
    {
        list.add(VNodeTypes.LOGARITHM_10);
        list.add(VNodeTypes.LOGARITHM_1P);
        list.add(VNodeTypes.LOGARITHM_E);
        list.add(VNodeTypes.ROOT);
        return list;
    }
    
    public static List<NodeType<?>> numberConstants(List<NodeType<?>> list)
    {
        list.add(VNodeTypes.E);
        list.add(VNodeTypes.PI);
        list.add(VNodeTypes.SQRT_2);
        
        list.add(VNodeTypes.CONSTANT_INTEGER);
        list.add(VNodeTypes.CONSTANT_FLOAT);
        list.add(VNodeTypes.CONSTANT_DOUBLE);
        list.add(VNodeTypes.CONSTANT_BOOLEAN);
        return list;
    }
    
    public static List<NodeType<?>> numberSerialization(List<NodeType<?>> list)
    {
        list.add(VNodeTypes.LOAD_INTEGER);
        list.add(VNodeTypes.LOAD_INTEGER_OPTIONAL);
        list.add(VNodeTypes.SAVE_INTEGER);
        list.add(VNodeTypes.LOAD_FLOAT);
        list.add(VNodeTypes.LOAD_FLOAT_OPTIONAL);
        list.add(VNodeTypes.SAVE_FLOAT);
        list.add(VNodeTypes.LOAD_DOUBLE);
        list.add(VNodeTypes.LOAD_DOUBLE_OPTIONAL);
        list.add(VNodeTypes.SAVE_DOUBLE);
        return list;
    }
    
    public static List<NodeType<?>> numberComparisons(List<NodeType<?>> list)
    {
        list.add(VNodeTypes.EQUAL_TO);
        list.add(VNodeTypes.NOT_EQUAL_TO);
        list.add(VNodeTypes.GREATER_THAN);
        list.add(VNodeTypes.GREATER_THAN_OR_EQUAL_TO);
        list.add(VNodeTypes.LESS_THAN);
        list.add(VNodeTypes.LESS_THAN_OR_EQUAL_TO);
        return list;
    }
    
    public static List<NodeType<?>> trigonometry(List<NodeType<?>> list)
    {
        list.add(VNodeTypes.SINES);
        list.add(VNodeTypes.COSINES);
        list.add(VNodeTypes.TANGENT);
        return list;
    }
    
    public static List<NodeType<?>> logic(List<NodeType<?>> list)
    {
        list.add(VNodeTypes.NOT);
        list.add(VNodeTypes.AND);
        list.add(VNodeTypes.NAND);
        list.add(VNodeTypes.OR);
        list.add(VNodeTypes.NOR);
        list.add(VNodeTypes.XOR);
        list.add(VNodeTypes.XNOR);
        
        list.add(VNodeTypes.LOAD_BOOLEAN);
        list.add(VNodeTypes.LOAD_BOOLEAN_OPTIONAL);
        list.add(VNodeTypes.SAVE_BOOLEAN);
        return list;
    }
    
    public static List<NodeType<?>> numberRoundingNCasting(List<NodeType<?>> list)
    {
        list.add(VNodeTypes.ROUND);
        list.add(VNodeTypes.FLOOR);
        list.add(VNodeTypes.CEIL);
        
        list.add(VNodeTypes.CAST_NUMBER_TO_INTEGER);
        list.add(VNodeTypes.CAST_NUMBER_TO_FLOAT);
        list.add(VNodeTypes.CAST_NUMBER_TO_DOUBLE);
        return list;
    }
    
    public static List<NodeType<?>> vectors(List<NodeType<?>> list)
    {
        list.add(VNodeTypes.VECTOR3D_CREATE);
        list.add(VNodeTypes.VECTOR3D_SPLIT);
        list.add(VNodeTypes.VECTOR3D_SCALE);
        list.add(VNodeTypes.VECTOR3D_ADDITION);
        list.add(VNodeTypes.VECTOR3D_SUBTRACTION);
        list.add(VNodeTypes.VECTOR3D_NORMALIZATION);
        list.add(VNodeTypes.VECTOR3D_DOT_PRODUCT);
        list.add(VNodeTypes.VECTOR3D_CROSS_PRODUCT);
        list.add(VNodeTypes.VECTOR3D_LENGTH);
        list.add(VNodeTypes.VECTOR3D_LENGTH_SQUARED);
        return list;
    }
    
    public static List<NodeType<?>> string(List<NodeType<?>> list)
    {
        list.add(VNodeTypes.CONSTANT_STRING);
        list.add(VNodeTypes.CONCATENATION);
        
        list.add(VNodeTypes.LOAD_STRING);
        list.add(VNodeTypes.LOAD_STRING_OPTIONAL);
        list.add(VNodeTypes.SAVE_STRING);
        return list;
    }
    
    public static List<NodeType<?>> world(List<NodeType<?>> list)
    {
        list.add(VNodeTypes.GET_WORLD_OPTIONAL);
        list.add(VNodeTypes.SET_BLOCK);
        list.add(VNodeTypes.BLOCK_POS_CREATE);
        list.add(VNodeTypes.BLOCK_POS_SPLIT);
        list.add(VNodeTypes.GET_BLOCK);
        
        list.add(VNodeTypes.LOAD_BLOCK_POS);
        list.add(VNodeTypes.LOAD_BLOCK_POS_OPTIONAL);
        list.add(VNodeTypes.SAVE_BLOCK_POS);
        return list;
    }
}
