package de.cas_ual_ty.visibilis.print;

import java.util.ArrayList;

import de.cas_ual_ty.visibilis.VUtility;
import de.cas_ual_ty.visibilis.Visibilis;
import de.cas_ual_ty.visibilis.node.Node;
import de.cas_ual_ty.visibilis.test.VPrintTest;
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
        // TODO Auto-generated method stub
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
    
    public void readFromNBT(NBTTagCompound nbt0)
    {
        if (!nbt0.hasKey(Visibilis.MOD_ID))
        {
            this.print = new VPrintTest(); // TODO HIGHEST: change this to default (this is for debugging)
            return;
        }
        
        NBTTagCompound nbt = nbt0.getCompoundTag(Visibilis.MOD_ID);
        this.print = VUtility.loadPrintFromNBT(nbt);
    }
    
    public void writeToNBT(NBTTagCompound nbt0)
    {
        NBTTagCompound nbt = VUtility.savePrintToNBT(this.print);
        nbt0.setTag(Visibilis.MOD_ID, nbt);
    }
}
