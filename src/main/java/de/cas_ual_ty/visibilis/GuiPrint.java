package de.cas_ual_ty.visibilis;

import org.lwjgl.opengl.GL11;

import de.cas_ual_ty.visibilis.node.Node;
import de.cas_ual_ty.visibilis.node.NodeField;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.renderer.GlStateManager;

public class GuiPrint extends GuiScreen
{
	protected Print print;
	
	public float zoom = 1F;
	
	protected ScaledResolution sr;
	protected int scaleFactor;
	
	protected Node mouseAttachedNode;
	protected NodeField mouseAttachedField;
	
	public GuiPrint(Print print)
	{
		this.print = print;
	}
	
	@Override
	public void initGui()
	{
		this.sr = new ScaledResolution(this.mc);
		this.scaleFactor = this.sr.getScaleFactor();
	}
	
	/**
	 * Called from the main game loop to update the screen.
	 */
	@Override
	public void updateScreen()
	{
	}
	
	@Override
	public void drawScreen(int mouseX, int mouseY, float partialTicks)
	{
		GL11.glPushMatrix();
		
		//Apply zoom
		GL11.glScalef(this.zoom, this.zoom, 0);
		
		//TODO window size? Maybe plan layout before starting?
//		this.scissorStart(x, y, w, h);
		
		this.drawPrint(this.print);
		
		//Draw buttons and labels
		super.drawScreen(mouseX, mouseY, partialTicks);
		
//		this.scissorEnd();
		GL11.glPopMatrix();
	}
	
	public void drawPrint(Print print)
	{
		
	}
	
	public void drawNode(Node node, int x, int y)
	{
		
	}
	
	public void drawNodeField(NodeField field, int x, int y)
	{
		
	}
	
	/**
	 * Apply the zoom factor.
	 */
	public float guiToPrint(int i)
	{
		return i * this.zoom;
	}
	
	/**
	 * Remove the zoom factor.
	 */
	public int printToGui(float f)
	{
		return Math.round(f / this.zoom);
	}
	
	/**
	 * Cut everything off beyond the given edges. Call this, then the all the draw code, then {@link #scissorEnd()} for cleanup.
	 * @param x Pos X of the rectangle.
	 * @param y Pos Y of the rectangle.
	 * @param w Width of the rectangle.
	 * @param h Height of the rectangle.
	 */
	protected void scissorStart(int x, int y, int w, int h)
	{
		GL11.glEnable(GL11.GL_SCISSOR_TEST);
	//	GlStateManager.disableLighting();
		GL11.glScissor(x * this.scaleFactor, (this.sr.getScaledHeight() - y - h) * this.scaleFactor, w * this.scaleFactor, h * this.scaleFactor);
	}
	
	/**
	 * Scissor cleanup. Call {@link #scissorStart(int, int, int, int)}, then all the draw code, then this.
	 */
	protected void scissorEnd()
	{
	//	GlStateManager.enableLighting();
		GL11.glDisable(GL11.GL_SCISSOR_TEST);
	}
}
