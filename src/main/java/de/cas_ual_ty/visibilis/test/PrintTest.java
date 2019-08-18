package de.cas_ual_ty.visibilis.test;

import de.cas_ual_ty.visibilis.Print;
import de.cas_ual_ty.visibilis.node.calculate.FFtoFPlus;
import de.cas_ual_ty.visibilis.node.constant.E;
import de.cas_ual_ty.visibilis.node.constant.Node0toF;
import de.cas_ual_ty.visibilis.node.constant.Pi;
import de.cas_ual_ty.visibilis.node.general.FFtoF;

public class PrintTest extends Print
{
	public static PrintTest printTest = new PrintTest();
	
	public PrintTest()
	{
		super();
		
		Node0toF const_e = new E(0, 0);
		Node0toF const_pi = new Pi(12, 48);
		
		FFtoF plus = new FFtoFPlus(240, 18);
		
		this.addNode(const_e);
		this.addNode(const_pi);
		this.addNode(plus);
		
		const_e.out.tryConnectTo(plus.in1);
		const_pi.out.tryConnectTo(plus.in2);
	}
}
