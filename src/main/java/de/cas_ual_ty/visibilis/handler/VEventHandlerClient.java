package de.cas_ual_ty.visibilis.handler;

import de.cas_ual_ty.visibilis.GuiPrint;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.InputEvent.KeyInputEvent;

public class VEventHandlerClient
{
	/**
	 * Used to intercept scrolling and zoom keys which are not separate keys.
	 * @see de.cas_ual_ty.visibilis.GuiPrint#onKeyInput(KeyInputEvent)
	 */
	@SubscribeEvent
	public static void onEvent(KeyInputEvent event)
	{
		EntityPlayerSP player = Minecraft.getMinecraft().player;
		
		if(player != null)
		{
			if(Minecraft.getMinecraft().currentScreen instanceof GuiPrint)
			{
				GuiPrint gui = (GuiPrint) Minecraft.getMinecraft().currentScreen;
				gui.onKeyInput(event);
			}
		}
	}
}
