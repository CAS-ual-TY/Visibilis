package de.cas_ual_ty.visibilis.print.provider;

import java.util.ArrayList;

import de.cas_ual_ty.visibilis.node.Node;
import de.cas_ual_ty.visibilis.node.NodeEvent;
import de.cas_ual_ty.visibilis.print.Print;
import de.cas_ual_ty.visibilis.registries.VNodeTypes;

public class NodeListProviderBase extends NodeListProvider
{
    protected ArrayList<Node> list;
    
    public NodeListProviderBase()
    {
        this.list = new ArrayList<>();
        NodeListProviderBase.addAllNodesToList(this.list);
    }
    
    @Override
    public ArrayList<Node> getAvailableNodes()
    {
        return this.list;
    }
    
    @Override
    public void onNodeAdded(Node node)
    {
        if(node instanceof NodeEvent)
        {
            this.list.remove(node);
        }
        else
        {
            int index = this.list.indexOf(node);
            this.list.remove(index);
            this.list.add(index, node.type.instantiate());
        }
        
        super.onNodeAdded(node);
    }
    
    @Override
    public void onNodeRemoved(Node node)
    {
        if(node instanceof NodeEvent)
        {
            this.list.add(node);
        }
        
        super.onNodeRemoved(node);
    }
    
    @Override
    public void onOpen(Print print)
    {
        // Remove all already existing event nodes so they cant be added twice
        
        Node f;
        for(NodeEvent n : print.getEvents())
        {
            f = null;
            for(Node n1 : this.list)
            {
                if(n.type == n1.type)
                {
                    f = n1;
                    break;
                }
            }
            
            if(f != null)
            {
                this.list.remove(f);
            }
        }
    }
    
    public static void addAllNodesToList(ArrayList<Node> list)
    {
        NodeListProviderBase.debug(list);
        NodeListProviderBase.exec(list);
        NodeListProviderBase.general(list);
        NodeListProviderBase.base(list);
        NodeListProviderBase.player(list);
        NodeListProviderBase.world(list);
    }
    
    public static void debug(ArrayList<Node> list)
    {
        list.add(VNodeTypes.EVENT_COMMAND.instantiate());
        list.add(VNodeTypes.EVENT_RIGHT_CLICK.instantiate());
        list.add(VNodeTypes.PRINT_DEBUG.instantiate());
        list.add(VNodeTypes.PRINT.instantiate());
    }
    
    public static void exec(ArrayList<Node> list)
    {
        list.add(VNodeTypes.BRANCH.instantiate());
        list.add(VNodeTypes.MERGE.instantiate());
        list.add(VNodeTypes.FOR.instantiate());
        list.add(VNodeTypes.WHILE.instantiate());
        list.add(VNodeTypes.MULTI_EQUALS_BRANCH.instantiate());
    }
    
    public static void general(ArrayList<Node> list)
    {
        list.add(VNodeTypes.MULTI_CONSTANT.instantiate());
    }
    
    public static void player(ArrayList<Node> list)
    {
        list.add(VNodeTypes.GET_PLAYER_OPTIONAL.instantiate());
        list.add(VNodeTypes.SPLIT_PLAYER.instantiate());
        list.add(VNodeTypes.SET_PLAYER_TRANSFORM.instantiate());
    }
    
    // ----------------------------------------
    
    public static void base(ArrayList<Node> list)
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
    }
    
    public static void calculation(ArrayList<Node> list)
    {
        list.add(VNodeTypes.ADDITION.instantiate());
        list.add(VNodeTypes.SUBTRACTION.instantiate());
        list.add(VNodeTypes.MULTIPLICATION.instantiate());
        list.add(VNodeTypes.DIVISION.instantiate());
        list.add(VNodeTypes.MODULO.instantiate());
        list.add(VNodeTypes.EXPONENTIATION.instantiate());
    }
    
    public static void extendedCalculation(ArrayList<Node> list)
    {
        list.add(VNodeTypes.LOGARITHM_10.instantiate());
        list.add(VNodeTypes.LOGARITHM_1P.instantiate());
        list.add(VNodeTypes.LOGARITHM_E.instantiate());
        list.add(VNodeTypes.ROOT.instantiate());
    }
    
    public static void numberConstants(ArrayList<Node> list)
    {
        list.add(VNodeTypes.E.instantiate());
        list.add(VNodeTypes.PI.instantiate());
        list.add(VNodeTypes.SQRT_2.instantiate());
        
        list.add(VNodeTypes.CONSTANT_INTEGER.instantiate());
        list.add(VNodeTypes.CONSTANT_FLOAT.instantiate());
        list.add(VNodeTypes.CONSTANT_DOUBLE.instantiate());
        list.add(VNodeTypes.CONSTANT_BOOLEAN.instantiate());
    }
    
    public static void numberSerialization(ArrayList<Node> list)
    {
        list.add(VNodeTypes.LOAD_INTEGER.instantiate());
        list.add(VNodeTypes.LOAD_INTEGER_OPTIONAL.instantiate());
        list.add(VNodeTypes.SAVE_INTEGER.instantiate());
    }
    
    public static void numberComparisons(ArrayList<Node> list)
    {
        list.add(VNodeTypes.EQUAL_TO.instantiate());
        list.add(VNodeTypes.NOT_EQUAL_TO.instantiate());
        list.add(VNodeTypes.GREATER_THAN.instantiate());
        list.add(VNodeTypes.GREATER_THAN_OR_EQUAL_TO.instantiate());
        list.add(VNodeTypes.LESS_THAN.instantiate());
        list.add(VNodeTypes.LESS_THAN_OR_EQUAL_TO.instantiate());
    }
    
    public static void trigonometry(ArrayList<Node> list)
    {
        list.add(VNodeTypes.SINES.instantiate());
        list.add(VNodeTypes.COSINES.instantiate());
        list.add(VNodeTypes.TANGENT.instantiate());
    }
    
    public static void logic(ArrayList<Node> list)
    {
        list.add(VNodeTypes.NOT.instantiate());
        list.add(VNodeTypes.AND.instantiate());
        list.add(VNodeTypes.NAND.instantiate());
        list.add(VNodeTypes.OR.instantiate());
        list.add(VNodeTypes.NOR.instantiate());
        list.add(VNodeTypes.XOR.instantiate());
        list.add(VNodeTypes.XNOR.instantiate());
    }
    
    public static void numberRoundingNCasting(ArrayList<Node> list)
    {
        list.add(VNodeTypes.ROUND.instantiate());
        list.add(VNodeTypes.FLOOR.instantiate());
        list.add(VNodeTypes.CEIL.instantiate());
        
        list.add(VNodeTypes.CAST_NUMBER_TO_INTEGER.instantiate());
        list.add(VNodeTypes.CAST_NUMBER_TO_FLOAT.instantiate());
        list.add(VNodeTypes.CAST_NUMBER_TO_DOUBLE.instantiate());
    }
    
    public static void vectors(ArrayList<Node> list)
    {
        list.add(VNodeTypes.VECTOR3D_CREATE.instantiate());
        list.add(VNodeTypes.VECTOR3D_SPLIT.instantiate());
        list.add(VNodeTypes.VECTOR3D_SCALE.instantiate());
        list.add(VNodeTypes.VECTOR3D_ADDITION.instantiate());
        list.add(VNodeTypes.VECTOR3D_SUBTRACTION.instantiate());
        list.add(VNodeTypes.VECTOR3D_NORMALIZATION.instantiate());
        list.add(VNodeTypes.VECTOR3D_DOT_PRODUCT.instantiate());
        list.add(VNodeTypes.VECTOR3D_CROSS_PRODUCT.instantiate());
        list.add(VNodeTypes.VECTOR3D_LENGTH.instantiate());
        list.add(VNodeTypes.VECTOR3D_LENGTH_SQUARED.instantiate());
    }
    
    public static void string(ArrayList<Node> list)
    {
        list.add(VNodeTypes.CONSTANT_STRING.instantiate());
        list.add(VNodeTypes.CONCATENATION.instantiate());
    }
    
    public static void world(ArrayList<Node> list)
    {
        list.add(VNodeTypes.GET_WORLD_OPTIONAL.instantiate());
        list.add(VNodeTypes.SET_BLOCK.instantiate());
        list.add(VNodeTypes.BLOCK_POS_CREATE.instantiate());
        list.add(VNodeTypes.BLOCK_POS_SPLIT.instantiate());
        list.add(VNodeTypes.GET_BLOCK.instantiate());
    }
}
