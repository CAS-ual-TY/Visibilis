package de.cas_ual_ty.visibilis.test;

import de.cas_ual_ty.visibilis.Print;
import de.cas_ual_ty.visibilis.node.calculate.NodeAddition;
import de.cas_ual_ty.visibilis.node.constant.NodeE;
import de.cas_ual_ty.visibilis.node.constant.NodePi;
import de.cas_ual_ty.visibilis.node.general.NodeFloatConst;
import de.cas_ual_ty.visibilis.node.general.NodeNumber2to1;

public class PrintTest extends Print
{
    public static PrintTest printTest = new PrintTest();
    
    public PrintTest()
    {
        super();
        
        NodeFloatConst const_e = (NodeFloatConst) new NodeE().setPosition(0, 0);
        NodeFloatConst const_pi = (NodeFloatConst) new NodePi().setPosition(12, 48);
        
        NodeNumber2to1 plus = (NodeNumber2to1) new NodeAddition().setPosition(240, 18);
        
        this.addNode(const_e);
        this.addNode(const_pi);
        this.addNode(plus);
        
        const_e.out1.tryConnectTo(plus.in1);
        const_pi.out1.tryConnectTo(plus.in2);
    }
}
