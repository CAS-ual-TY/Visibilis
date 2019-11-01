package de.cas_ual_ty.visibilis.test;

import java.util.ArrayList;

import de.cas_ual_ty.visibilis.Visibilis;
import de.cas_ual_ty.visibilis.node.Node;
import de.cas_ual_ty.visibilis.node.calculate.NodeAddition;
import de.cas_ual_ty.visibilis.node.calculate.NodeDivision;
import de.cas_ual_ty.visibilis.node.calculate.NodeExponentiation;
import de.cas_ual_ty.visibilis.node.calculate.NodeLogarithm10;
import de.cas_ual_ty.visibilis.node.calculate.NodeLogarithm1p;
import de.cas_ual_ty.visibilis.node.calculate.NodeLogarithmE;
import de.cas_ual_ty.visibilis.node.calculate.NodeModulo;
import de.cas_ual_ty.visibilis.node.calculate.NodeMultiplication;
import de.cas_ual_ty.visibilis.node.calculate.NodeRoot;
import de.cas_ual_ty.visibilis.node.calculate.NodeSubtraction;
import de.cas_ual_ty.visibilis.node.constant.NodeConstBoolean;
import de.cas_ual_ty.visibilis.node.constant.NodeConstNumber;
import de.cas_ual_ty.visibilis.node.constant.NodeE;
import de.cas_ual_ty.visibilis.node.constant.NodePi;
import de.cas_ual_ty.visibilis.node.constant.NodeSQRT2;
import de.cas_ual_ty.visibilis.node.event.NodeEvent;
import de.cas_ual_ty.visibilis.node.function.NodeCosines;
import de.cas_ual_ty.visibilis.node.function.NodeRound;
import de.cas_ual_ty.visibilis.node.function.NodeRoundDown;
import de.cas_ual_ty.visibilis.node.function.NodeRoundUp;
import de.cas_ual_ty.visibilis.node.function.NodeSines;
import de.cas_ual_ty.visibilis.node.function.NodeTangent;
import de.cas_ual_ty.visibilis.node.logic.NodeAND;
import de.cas_ual_ty.visibilis.node.logic.NodeNAND;
import de.cas_ual_ty.visibilis.node.logic.NodeNOR;
import de.cas_ual_ty.visibilis.node.logic.NodeNOT;
import de.cas_ual_ty.visibilis.node.logic.NodeOR;
import de.cas_ual_ty.visibilis.node.logic.NodeXNOR;
import de.cas_ual_ty.visibilis.node.logic.NodeXOR;
import de.cas_ual_ty.visibilis.print.Print;
import de.cas_ual_ty.visibilis.print.item.PrintHelperItem;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumHand;

public class VPrintHelperTest extends PrintHelperItem
{
    public VPrintHelperTest(ItemStack itemStack, EnumHand hand)
    {
        super(itemStack, hand);
    }
    
    @Override
    public Print createNewPrint()
    {
        return new Print();
    }
    
    @Override
    public ArrayList<Node> getAvailableNodes(GuiScreen gui)
    {
        ArrayList<Node> list = new ArrayList<Node>();
        
        list.add(new NodeEvent(1, Visibilis.MOD_ID, "command"));
        list.add(new VNodeTest());
        
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
        
        list.add(new NodeE());
        list.add(new NodePi());
        list.add(new NodeSQRT2());
        list.add(new NodeConstNumber());
        list.add(new NodeConstBoolean());
        
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
        
        return list;
    }
}
