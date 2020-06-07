package de.cas_ual_ty.visibilis.print.provider;

import de.cas_ual_ty.visibilis.Visibilis;
import de.cas_ual_ty.visibilis.print.Print;
import de.cas_ual_ty.visibilis.util.VNBTUtility;
import net.minecraft.nbt.CompoundNBT;

public abstract class PrintProviderNBT extends PrintProvider
{
    public PrintProviderNBT(NodeListProvider nodeListProvider)
    {
        super(nodeListProvider);
    }
    
    @Override
    public void init()
    {
        this.readFromNBT(this.getNBT());
        super.init();
    }
    
    @Override
    public void onGuiClose()
    {
        this.writeToNBT(this.getNBT());
        super.onGuiClose();
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
        
        if(!nbt0.contains(Visibilis.MOD_ID))
        {
            print = this.createNewPrint();
        }
        else
        {
            CompoundNBT nbt = nbt0.getCompound(Visibilis.MOD_ID);
            print = VNBTUtility.loadPrintFromNBT(nbt, true);
        }
        
        this.undoList.setFirst(print);
    }
    
    public void writeToNBT(CompoundNBT nbt0)
    {
        CompoundNBT nbt = VNBTUtility.savePrintToNBT(this.getPrint(), true);
        nbt0.put(Visibilis.MOD_ID, nbt);
        
        this.synchToServer(nbt0);
    }
}
