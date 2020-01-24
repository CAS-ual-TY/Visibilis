package de.cas_ual_ty.visibilis.test;

import java.util.ArrayList;

import de.cas_ual_ty.visibilis.node.Node;
import de.cas_ual_ty.visibilis.node.event.NodeEvent;
import de.cas_ual_ty.visibilis.print.Print;
import de.cas_ual_ty.visibilis.print.item.PrintProviderItem;
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
            this.list.add(index, node.clone());
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
        /* TODO current branch
        list.add(new NodeEvent(Visibilis.MOD_ID, "command"));
        list.add(new VNodePrintDebug());
        
        list.add(new NodeAddition());
        list.add(new NodeDivision());
        list.add(new NodeExponentiation());
        list.add(new NodeLogarithm10());
        list.add(new NodeLogarithm1p());
        list.add(new NodeLogarithmE());
        list.add(new NodeModulo());
        list.add(new NodeMultiplication());
        list.add(new NodeRoot());
        list.add(new NodeSubtraction());
        list.add(new NodeConcatenation());
        
        list.add(new NodeE());
        list.add(new NodePi());
        list.add(new NodeSQRT2());
        list.add(new NodeFloatV());
        list.add(new NodeBooleanV());
        
        list.add(new NodeRound());
        list.add(new NodeRoundUp());
        list.add(new NodeRoundDown());
        list.add(new NodeSines());
        list.add(new NodeCosines());
        list.add(new NodeTangent());
        
        list.add(new NodeAND());
        list.add(new NodeNAND());
        list.add(new NodeNOR());
        list.add(new NodeNOT());
        list.add(new NodeOR());
        list.add(new NodeXNOR());
        list.add(new NodeXOR());
        
        list.add(new NodeBranch());
        list.add(new NodeMerge());
        list.add(new NodeFor());
        list.add(new NodeWhile());
        
        list.add(new NodeFloatEquals());
        list.add(new NodeFloatGreater());
        list.add(new NodeFloatGreaterEquals());
        list.add(new NodeFloatLess());
        list.add(new NodeFloatLessEquals());
        
        list.add(new NodeFloatToInteger());
        
        list.add(new NodeVector3dCreate());
        list.add(new NodeVector3dSplit());
        list.add(new NodeVector3dScale());
        
        list.add(new NodePrint());
        */
    }
}
