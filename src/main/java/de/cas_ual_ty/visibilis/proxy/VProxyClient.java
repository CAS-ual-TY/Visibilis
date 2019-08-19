package de.cas_ual_ty.visibilis.proxy;

import de.cas_ual_ty.visibilis.handler.VEventHandlerClient;
import net.minecraftforge.common.MinecraftForge;

public class VProxyClient implements ISidedProxy
{
	public static VEventHandlerClient eventHandlerClient;
	
	@Override
	public void preInit()
	{
		
	}
	
	@Override
	public void init()
	{
		MinecraftForge.EVENT_BUS.register((eventHandlerClient = new VEventHandlerClient()));
	}
}
