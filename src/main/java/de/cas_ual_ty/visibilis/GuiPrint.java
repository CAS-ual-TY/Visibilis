package de.cas_ual_ty.visibilis;

import org.lwjgl.opengl.GL11;

import de.cas_ual_ty.visibilis.node.Node;
import de.cas_ual_ty.visibilis.node.NodeField;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;

public class GuiPrint extends GuiScreen
{
	protected Print print;
	
	public float zoom = 1F;
	
	protected ScaledResolution sr;
	protected int scaleFactor;
	
	// --- Mouse clicks on node or field -> temporarily (and additionally) stored here ---
	//
	//The thing clicked on
	protected Node mouseAttachedNode;
	protected NodeField mouseAttachedField;
	//
	//The position it was at when clicked on
	protected int attachedPrevX;
	protected int attachedPrevY;
	//
	// --- End temporarily stored things someone clicked on ---
	
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
		GlStateManager.disableLighting();
		
//		this.innerStart(x, y, w, h); //TODO window size? Maybe plan layout before starting?
		this.drawInner(mouseX, mouseY, partialTicks);
//		this.innerEnd();
		
		//Draw buttons and labels
		super.drawScreen(mouseX, mouseY, partialTicks);
		
		GlStateManager.enableLighting();
	}
	
	public void drawInner(int mouseX, int mouseY, float partialTicks)
	{
		this.drawPrint(this.print);
	}
	
	public void drawPrint(Print print)
	{
		int x;
		int y;
		
		//Loop through all nodes. Nodes at the end of the list will be drawn on top.
		for(Node node : print.getNodes())
		{
			//TODO adjust these coordinates later for scrolling
			x = node.posX;
			y = node.posY;
			this.drawNode(node, x, y);
		}
	}
	
	/**
	 * Draw a node at the given coordinates (not the node's coordinates!) and all its node fields.
	 */
	public void drawNode(Node node, int x, int y)
	{
		//Sizes in px for each field as well as the header (keep 1px border in mind, or maybe not?)
		final int height = 12;
		final int width = 120;
		
		//The amount of needed space for node fields below. Since inputs and outputs share a line, only the higher amount needs to be taken care of
		int fieldMax;
		
		if(node.getInputAmt() > node.getOutputAmt())
		{
			fieldMax = node.getInputAmt();
		}
		else
		{
			fieldMax = node.getOutputAmt();
		}
		
		//Include the header
		++fieldMax;
		
		// --- Start drawing node itself ---
		
		//Draw entire node background, maybe add border later?
		this.drawRect(x, y, width, height * fieldMax, (byte)0, (byte)0, (byte)0);
		
		//#SelfExplainingCodeIsNotAlwaysAMeme
		this.drawNodeHeader(node, x, y, width, height);
		
		// --- Done drawing node, now drawing fields (inputs and outputs) ---
		
		//Draw below the header
		y += height;
		
		int i;
		NodeField field;
		
		for(i = 0; i < node.getInputAmt(); ++i)
		{
			field = node.getInput(i);
			this.drawNodeField(field, x, y + height * i, width / 2, height);
		}
		
		//Outputs are on the right
		x += width / 2;
		
		for(i = 0; i < node.getOutputAmt(); ++i)
		{
			field = node.getOutput(i);
			this.drawNodeField(field, x + width / 2, y + height * i, width / 2, height); //Outputs are on the right, so add half width of the node
		}
		
		// --- End drawing fields ---
	}
	
	/**
	 * Draw a node header at the given coordinates (not the node's coordinates!).
	 */
	public void drawNodeHeader(Node node, int x, int y, final int width, final int height)
	{
		//Draw the inner colored rectangle
		drawRect(x + 1, y + 1, width - 2, height - 2, node.getColor()[0], node.getColor()[1], node.getColor()[2]);
		
		//Draw the name
		String name = node.getUnlocalizedName(); //TODO translate
		name = this.fontRenderer.trimStringToWidth(name, width - 4); //Trim the name in case it is too big
		this.fontRenderer.drawString(name, x + 2, y + 2, 0xFFFFFFFF); //Draw the trimmed name, maybe add shadow?
	}
	
	/**
	 * Draw a node field at the given coordinates (not the node's coordinates!).
	 */
	public void drawNodeField(NodeField field, int x, int y, final int width, final int height)
	{
		final int dotSize = 4; //Size of the dot. 4x4 drawn inside height x height
		
		int xDot; //Where to draw the dot
		int xName; //Where to draw the name
		
		//width - dot - extra pixels: to make sure there is a gap between inputs and outputs
		int wName = width - height - height / 2;
		
		if(field.isInput())
		{
			//Input, so draw the dot on the left, the name on the right
			xDot = x;
			xName = xDot + height;
		}
		else
		{
			//Output, so draw the dot on the right, the name on the left
			xName = x;
			xDot = x + width - height;
		}
		
		//See de.cas_ual_ty.visibilis.datatype.DataType for explantion of deprecation. TODO Remove as this only works for Visibilis only data types. THIS IS TEMPORARY CALM DOWN
		EnumVDataType type = EnumVDataType.valueOf(field.name.toUpperCase());
		
		//Draw dot
		drawRect(xDot + (height - dotSize) / 2, y + (height - dotSize) / 2, dotSize, dotSize, type.r, type.g, type.b);
		
		//Draw inner colored rectangle
		drawRect(xName + 1, y + 1, wName - 2, height - 2, type.r, type.g, type.b);
		
		//Draw name
		String name = field.getUnlocalizedName(); //TODO translate
		name = this.fontRenderer.trimStringToWidth(name, width - 4); //Trim the name in case it is too big
		this.fontRenderer.drawString(name, xName + 2, y + 2, 0xFFFFFFFF); //Draw the trimmed name, maybe add shadow?
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
	 * Cut everything off outside the given rectangle. Call this, then the all the draw code, then {@link #innerEnd()} for cleanup.
	 * @param x Pos X of the rectangle.
	 * @param y Pos Y of the rectangle.
	 * @param w Width of the rectangle.
	 * @param h Height of the rectangle.
	 */
	protected void innerStart(int x, int y, int w, int h)
	{
		GL11.glEnable(GL11.GL_SCISSOR_TEST);
		
		//* scaleFactor due to the automatic resizing depending on window size (or the GUI size settings)
		//All the derparoundery with the Y position because Minecraft 0,0 is at the top left, but lwjgl 0,0 is at the bottom left
		GL11.glScissor(x * this.scaleFactor, (this.sr.getScaledHeight() - y - h) * this.scaleFactor, w * this.scaleFactor, h * this.scaleFactor);
		
		GL11.glPushMatrix();
		GL11.glScalef(this.zoom, this.zoom, 1); //Apply zoom, 2x zoom means 2x size of prints, so this is fine
	}
	
	/**
	 * Scissor cleanup. Call {@link #innerStart(int, int, int, int)}, then all the draw code, then this.
	 */
	protected void innerEnd()
	{
		GL11.glPopMatrix();
		GL11.glDisable(GL11.GL_SCISSOR_TEST);
	}
	
	/**
	 * {@link #drawRect(int, int, int, int, byte, byte, byte, byte)} but without alpha.
	 */
	public static void drawRect(int x, int y, int w, int h, byte r, byte g, byte b)
	{
		//TODO write separate code for rects without alpha, maybe...
		drawRect(x, y, w, h, r, g, b, (byte)255);
	}
	
	/**
	 * (This is the almost-equivalent of the vanilla Gui class's drawRect method. The differences are direct byte rgba input without bit shifting, and width/height instead of max x/y.)
	 * Draws a solid color rectangle with the specified coordinates and color.
	 * @see net.minecraft.client.gui.Gui#drawRect(int, int, int, int, int)
	 */
	public static void drawRect(int x, int y, int w, int h, byte r, byte g, byte b, byte a)
	{
		Tessellator tessellator = Tessellator.getInstance();
		BufferBuilder bufferbuilder = tessellator.getBuffer();
		
		//Prep time
		GlStateManager.enableBlend(); //We do need blending
		GlStateManager.disableTexture2D(); //We dont need textures
		
		//Make sure alpha is working
		GlStateManager.tryBlendFuncSeparate(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA, GlStateManager.SourceFactor.ONE, GlStateManager.DestFactor.ZERO);
		
		//Set the color!
		GL11.glColor4b(r, g, b, a);
		
		//Start drawing
		bufferbuilder.begin(7, DefaultVertexFormats.POSITION);
		
		//Add vertices
		bufferbuilder.pos(x, y + h, 0.0D).endVertex();		//BL
		bufferbuilder.pos(x + w, y + h, 0.0D).endVertex();	//BR
		bufferbuilder.pos(x + w, y, 0.0D).endVertex();		//TR
		bufferbuilder.pos(x, y, 0.0D).endVertex();			//TL
		
		//End drawing
		tessellator.draw();
		
		//Cleanup time
		GlStateManager.enableTexture2D(); //Turn textures back on
		GlStateManager.disableBlend(); //Turn blending uhh... back off?
	}
}
