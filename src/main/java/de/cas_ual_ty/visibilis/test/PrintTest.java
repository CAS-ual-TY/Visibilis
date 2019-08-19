package de.cas_ual_ty.visibilis.test;

import de.cas_ual_ty.visibilis.Print;
import de.cas_ual_ty.visibilis.node.calculate.FloatPlus;
import de.cas_ual_ty.visibilis.node.constant.FloatConstE;
import de.cas_ual_ty.visibilis.node.constant.FloatConstPi;
import de.cas_ual_ty.visibilis.node.general.Float2to1;
import de.cas_ual_ty.visibilis.node.general.Float0to1;

public class PrintTest extends Print
{
	public static PrintTest printTest = new PrintTest();
	
	public PrintTest()
	{
		super();
		
		Float0to1 const_e = (Float0to1) new FloatConstE().setPosition(0, 0);
		Float0to1 const_pi = (Float0to1) new FloatConstPi().setPosition(12, 48);
		
		Float2to1 plus = (Float2to1) new FloatPlus().setPosition(240, 18);
		
		this.addNode(const_e);
		this.addNode(const_pi);
		this.addNode(plus);
		
		const_e.out.tryConnectTo(plus.in1);
		const_pi.out.tryConnectTo(plus.in2);
	}
}
