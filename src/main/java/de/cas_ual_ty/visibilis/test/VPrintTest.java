package de.cas_ual_ty.visibilis.test;

import de.cas_ual_ty.visibilis.Print;
import de.cas_ual_ty.visibilis.node.Node;
import de.cas_ual_ty.visibilis.node.NodeField;
import de.cas_ual_ty.visibilis.node.calculate.NodeAddition;
import de.cas_ual_ty.visibilis.node.calculate.NodeDivision;
import de.cas_ual_ty.visibilis.node.calculate.NodeExponentiation;
import de.cas_ual_ty.visibilis.node.calculate.NodeLogarithm10;
import de.cas_ual_ty.visibilis.node.calculate.NodeLogarithm1p;
import de.cas_ual_ty.visibilis.node.calculate.NodeLogarithmE;
import de.cas_ual_ty.visibilis.node.calculate.NodeMultiplication;
import de.cas_ual_ty.visibilis.node.calculate.NodeRoot;
import de.cas_ual_ty.visibilis.node.calculate.NodeSubtraction;
import de.cas_ual_ty.visibilis.node.constant.NodeE;
import de.cas_ual_ty.visibilis.node.constant.NodePi;
import de.cas_ual_ty.visibilis.node.constant.NodeSQRT2;
import de.cas_ual_ty.visibilis.node.event.NodeEvent;
import de.cas_ual_ty.visibilis.node.exec.NodeCondition;
import de.cas_ual_ty.visibilis.node.general.NodeFloatConst;
import de.cas_ual_ty.visibilis.node.general.NodeNumber2to1;

public class VPrintTest extends Print
{
    public static VPrintTest printTest = new VPrintTest();
    
    public VPrintTest()
    {
        super();
        
        /*
        NodeFloatConst const_e = (NodeFloatConst) new NodeE().setPosition(0, 0);
        NodeFloatConst const_pi = (NodeFloatConst) new NodePi().setPosition(12, 48);
        
        NodeNumber2to1 plus = (NodeNumber2to1) new NodeAddition().setPosition(240, 18);
        
        this.addNode(const_e);
        this.addNode(const_pi);
        this.addNode(plus);
        
        NodeField.tryConnect(const_e.out1, plus.in1);
        NodeField.tryConnect(const_pi.out1, plus.in2);
        
        Node node1 = new NodeEvent();
        Node node2 = new NodeCondition();
        
        this.addNode(node1);
        this.addNode(node2);
        */
        
        /*
        this.addNode(new NodeAddition());
        this.addNode(new NodeSubtraction());
        this.addNode(new NodeMultiplication());
        this.addNode(new NodeDivision());
        this.addNode(new NodeExponentiation());
        this.addNode(new NodeRoot());
        this.addNode(new NodeLogarithm10());
        this.addNode(new NodeLogarithmE());
        */
        
        this.addNode(new NodeE());
        this.addNode(new NodePi());
        this.addNode(new NodeSQRT2());
    }
}
