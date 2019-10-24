package de.cas_ual_ty.visibilis.print;

import java.util.ArrayList;

import de.cas_ual_ty.visibilis.Visibilis;
import de.cas_ual_ty.visibilis.node.Node;
import de.cas_ual_ty.visibilis.util.NBTUtility;
import net.minecraft.nbt.NBTTagCompound;

public abstract class PrintHelperBase implements IPrintHelper
{
    public Print print;
    
    public PrintHelperBase()
    {
    }
    
    @Override
    public Print getPrint(GuiPrint gui)
    {
        return this.print;
    }
    
    @Override
    public ArrayList<Node> getAvailableNodes(GuiPrint gui)
    {
        return null;
    }
    
    @Override
    public void onGuiOpen(GuiPrint gui)
    {
        this.readFromNBT(this.getNBT());
    }
    
    @Override
    public void onGuiInit(GuiPrint gui)
    {
    }
    
    @Override
    public void onGuiClose(GuiPrint gui)
    {
        this.writeToNBT(this.getNBT());
    }
    
    /**
     * Get the tag to save/write the print from/to. No need to make a sub compound, since this is done in {@link #readFromNBT(NBTTagCompound)} and {@link #writeToNBT(NBTTagCompound)} already
     */
    public abstract NBTTagCompound getNBT();
    
    /**
     * Create a new print in case the NBT does not contain data
     */
    public abstract Print createNewPrint();
    
    /**
     * Called after the nbt has been written to.
     */
    public void synchToServer(NBTTagCompound nbt)
    {
    }
    
    public void readFromNBT(NBTTagCompound nbt0)
    {
        if (!nbt0.hasKey(Visibilis.MOD_ID))
        {
            this.print = this.createNewPrint();
            return;
        }
        
        NBTTagCompound nbt = nbt0.getCompoundTag(Visibilis.MOD_ID);
        this.print = NBTUtility.loadPrintFromNBT(nbt);
    }
    
    public void writeToNBT(NBTTagCompound nbt0)
    {
        NBTTagCompound nbt = NBTUtility.savePrintToNBT(this.print);
        nbt0.setTag(Visibilis.MOD_ID, nbt);
        this.synchToServer(nbt0);
    }
}
