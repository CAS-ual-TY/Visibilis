package de.cas_ual_ty.visibilis.test;

import de.cas_ual_ty.visibilis.Visibilis;
import de.cas_ual_ty.visibilis.node.constant.NodeFalse;
import de.cas_ual_ty.visibilis.node.constant.NodeTrue;
import de.cas_ual_ty.visibilis.node.event.NodeEvent;
import de.cas_ual_ty.visibilis.node.logic.NodeAND;
import de.cas_ual_ty.visibilis.node.logic.NodeNAND;
import de.cas_ual_ty.visibilis.node.logic.NodeNOR;
import de.cas_ual_ty.visibilis.node.logic.NodeNOT;
import de.cas_ual_ty.visibilis.node.logic.NodeOR;
import de.cas_ual_ty.visibilis.node.logic.NodeXNOR;
import de.cas_ual_ty.visibilis.node.logic.NodeXOR;
import de.cas_ual_ty.visibilis.print.Print;

public class VPrintTest extends Print
{
    public static VPrintTest printTest = new VPrintTest();
    
    public VPrintTest()
    {
        super();
        
        /*
         * NodeFloatConst const_e = (NodeFloatConst) new NodeE().setPosition(0, 0); NodeFloatConst const_pi = (NodeFloatConst) new NodePi().setPosition(12, 48);
         * 
         * NodeNumber2to1 plus = (NodeNumber2to1) new NodeAddition().setPosition(240, 18);
         * 
         * this.addNode(const_e); this.addNode(const_pi); this.addNode(plus);
         * 
         * NodeField.tryConnect(const_e.out1, plus.in1); NodeField.tryConnect(const_pi.out1, plus.in2);
         * 
         * Node node1 = new NodeEvent(); Node node2 = new NodeCondition();
         * 
         * this.addNode(node1); this.addNode(node2);
         */
        
        /*
         * this.addNode(new NodeAddition()); this.addNode(new NodeSubtraction()); this.addNode(new NodeMultiplication()); this.addNode(new NodeDivision()); this.addNode(new NodeExponentiation()); this.addNode(new NodeRoot()); this.addNode(new NodeLogarithm10()); this.addNode(new NodeLogarithmE());
         */
        
        /*
         * this.addNode(new NodeE()); this.addNode(new NodePi()); this.addNode(new NodeSQRT2());
         * 
         * this.addNode(new NodeSines()); this.addNode(new NodeCosines()); this.addNode(new NodeTangent());
         */
        
        this.addNode(new NodeAND());
        this.addNode(new NodeNAND());
        this.addNode(new NodeNOR());
        this.addNode(new NodeNOT());
        this.addNode(new NodeOR());
        this.addNode(new NodeXNOR());
        this.addNode(new NodeXOR());
        this.addNode(new NodeTrue());
        this.addNode(new NodeFalse());
        this.addNode(new NodeEvent(Visibilis.MOD_ID, "command"));
        this.addNode(new VNodeTest());
    }
}
