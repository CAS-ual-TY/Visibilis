package de.cas_ual_ty.visibilis.test;

import de.cas_ual_ty.visibilis.Print;
import de.cas_ual_ty.visibilis.node.calculate.FFtoFPlus;
import de.cas_ual_ty.visibilis.node.constant.E;
import de.cas_ual_ty.visibilis.node.constant.Pi;
import de.cas_ual_ty.visibilis.node.general.FFtoF;
import de.cas_ual_ty.visibilis.node.general.NtoF;

public class PrintTest extends Print
{
	public static PrintTest printTest = new PrintTest();
	
	public PrintTest()
	{
		super();
		
		NtoF const_e = (NtoF) new E().setPosition(0, 0);
		NtoF const_pi = (NtoF) new Pi().setPosition(12, 48);
		
		FFtoF plus = (FFtoF) new FFtoFPlus().setPosition(240, 18);
		
		this.addNode(const_e);
		this.addNode(const_pi);
		this.addNode(plus);
		
		const_e.out.tryConnectTo(plus.in1);
		const_pi.out.tryConnectTo(plus.in2);
	}
}
