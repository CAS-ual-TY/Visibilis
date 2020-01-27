package de.cas_ual_ty.visibilis.test;

import java.util.ArrayList;

import de.cas_ual_ty.visibilis.node.Node;
import de.cas_ual_ty.visibilis.node.event.NodeEvent;
import de.cas_ual_ty.visibilis.print.Print;
import de.cas_ual_ty.visibilis.print.item.PrintProviderItem;
import de.cas_ual_ty.visibilis.registries.VNodeTypes;
import net.minecraft.item.ItemStack;

public class VPrintProviderTest extends PrintProviderItem
{
    protected ArrayList<Node> list;
    
    public VPrintProviderTest(ItemStack itemStack, int slot)
    {
        super(itemStack, slot);
        this.list = new ArrayList<>();
        VPrintProviderTest.addAllNodesToList(this.list);
    }
    
    @Override
    public Print createNewPrint()
    {
        return new Print();
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
    
    public static void addAllNodesToList(ArrayList<Node> list)
    {
        list.add(VNodeTypes.COMMAND.instantiate());
        list.add(VNodeTypes.PRINT_DEBUG.instantiate());
        
        list.add(VNodeTypes.ADDITION.instantiate());
        list.add(VNodeTypes.SUBTRACTION.instantiate());
        list.add(VNodeTypes.MULTIPLICATION.instantiate());
        list.add(VNodeTypes.DIVISION.instantiate());
        list.add(VNodeTypes.MODULO.instantiate());
        list.add(VNodeTypes.EXPONENTIATION.instantiate());
        
        list.add(VNodeTypes.LOGARITHM_10.instantiate());
        list.add(VNodeTypes.LOGARITHM_1P.instantiate());
        list.add(VNodeTypes.LOGARITHM_E.instantiate());
        list.add(VNodeTypes.ROOT.instantiate());
        
        list.add(VNodeTypes.CONCATENATION.instantiate());
        
        list.add(VNodeTypes.E.instantiate());
        list.add(VNodeTypes.PI.instantiate());
        list.add(VNodeTypes.SQRT2.instantiate());
        
        list.add(VNodeTypes.CONSTANT_INTEGER.instantiate());
        list.add(VNodeTypes.CONSTANT_FLOAT.instantiate());
        list.add(VNodeTypes.CONSTANT_DOUBLE.instantiate());
        list.add(VNodeTypes.CONSTANT_BOOLEAN.instantiate());
        
        list.add(VNodeTypes.SINES.instantiate());
        list.add(VNodeTypes.COSINES.instantiate());
        list.add(VNodeTypes.TANGENT.instantiate());
        
        list.add(VNodeTypes.ROUND.instantiate());
        list.add(VNodeTypes.FLOOR.instantiate());
        list.add(VNodeTypes.CEIL.instantiate());
        
        list.add(VNodeTypes.NOT.instantiate());
        list.add(VNodeTypes.AND.instantiate());
        list.add(VNodeTypes.NAND.instantiate());
        list.add(VNodeTypes.OR.instantiate());
        list.add(VNodeTypes.NOR.instantiate());
        list.add(VNodeTypes.XOR.instantiate());
        list.add(VNodeTypes.XNOR.instantiate());
        
        list.add(VNodeTypes.BRANCH.instantiate());
        list.add(VNodeTypes.MERGE.instantiate());
        list.add(VNodeTypes.FOR.instantiate());
        list.add(VNodeTypes.WHILE.instantiate());
        
        list.add(VNodeTypes.EQUAL_TO.instantiate());
        list.add(VNodeTypes.NOT_EQUAL_TO.instantiate());
        list.add(VNodeTypes.GREATER_THAN.instantiate());
        list.add(VNodeTypes.GREATER_THAN_OR_EQUAL_TO.instantiate());
        list.add(VNodeTypes.LESS_THAN.instantiate());
        list.add(VNodeTypes.LESS_THAN_OR_EQUAL_TO.instantiate());
        
        list.add(VNodeTypes.CAST_FLOAT_TO_INTEGER.instantiate());
        list.add(VNodeTypes.CAST_DOUBLE_TO_INTEGER.instantiate());
        list.add(VNodeTypes.CAST_DOUBLE_TO_FLOAT.instantiate());
        
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
        
        list.add(VNodeTypes.PRINT.instantiate());
    }
}
