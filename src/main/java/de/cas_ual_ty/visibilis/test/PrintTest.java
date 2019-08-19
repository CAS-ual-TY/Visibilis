package de.cas_ual_ty.visibilis.test;

import de.cas_ual_ty.visibilis.Print;
import de.cas_ual_ty.visibilis.node.calculate.FloatPlus;
import de.cas_ual_ty.visibilis.node.constant.FloatConstE;
import de.cas_ual_ty.visibilis.node.constant.FloatConstPi;
import de.cas_ual_ty.visibilis.node.general.Float2to1;
import de.cas_ual_ty.visibilis.node.general.FloatConst;

public class PrintTest extends Print
{
    public static PrintTest printTest = new PrintTest();

    public PrintTest()
    {
        super();

        FloatConst const_e = (FloatConst) new FloatConstE().setPosition(0, 0);
        FloatConst const_pi = (FloatConst) new FloatConstPi().setPosition(12, 48);

        Float2to1 plus = (Float2to1) new FloatPlus().setPosition(240, 18);

        this.addNode(const_e);
        this.addNode(const_pi);
        this.addNode(plus);

        const_e.out1.tryConnectTo(plus.in1);
        const_pi.out1.tryConnectTo(plus.in2);
    }
}
