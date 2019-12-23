package de.cas_ual_ty.visibilis.print;

import java.util.ArrayList;

import de.cas_ual_ty.visibilis.Visibilis;
import de.cas_ual_ty.visibilis.node.Node;
import de.cas_ual_ty.visibilis.util.NBTUtility;
import net.minecraft.nbt.CompoundNBT;

public abstract class PrintProviderNBT extends PrintProvider
{
    public PrintProviderNBT()
    {
        super();
    }
    
    @Override
    public ArrayList<Node> getAvailableNodes()
    {
        return new ArrayList<Node>();
    }
    
    @Override
    public void init()
    {
        this.readFromNBT(this.getNBT());
    }
    
    @Override
    public void onGuiOpen()
    {
        
    }
    
    @Override
    public void onGuiClose()
    {
        this.writeToNBT(this.getNBT());
    }
    
    /**
     * Get the tag to save/write the print from/to. No need to make a sub compound, since this is done in {@link #readFromNBT(CompoundNBT)} and {@link #writeToNBT(CompoundNBT)} already
     */
    public abstract CompoundNBT getNBT();
    
    /**
     * Create a new print in case the NBT does not contain data
     */
    public abstract Print createNewPrint();
    
    /**
     * Called after the nbt has been written to.
     */
    public abstract void synchToServer(CompoundNBT nbt);
    
    public void readFromNBT(CompoundNBT nbt0)
    {
        Print print;
        
        if (!nbt0.contains(Visibilis.MOD_ID))
        {
            print = this.createNewPrint();
        }
        else
        {
            CompoundNBT nbt = nbt0.getCompound(Visibilis.MOD_ID);
            print = NBTUtility.loadPrintFromNBT(nbt);
        }
        
        this.undoList.setFirst(print);
    }
    
    public void writeToNBT(CompoundNBT nbt0)
    {
        CompoundNBT nbt = NBTUtility.savePrintToNBT(this.getPrint());
        nbt0.put(Visibilis.MOD_ID, nbt);
        
        this.synchToServer(nbt0);
    }
}
