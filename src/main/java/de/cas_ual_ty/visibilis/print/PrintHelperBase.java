package de.cas_ual_ty.visibilis.print;

import java.util.ArrayList;

import de.cas_ual_ty.visibilis.Visibilis;
import de.cas_ual_ty.visibilis.node.Node;
import de.cas_ual_ty.visibilis.util.NBTUtility;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.ListNBT;
import net.minecraftforge.common.util.Constants.NBT;

public abstract class PrintHelperBase implements IPrintProvider
{
    public Print print;
    
    public PrintHelperBase()
    {
    }
    
    @Override
    public Print getPrint(Screen gui)
    {
        return this.print;
    }
    
    @Override
    public ArrayList<Node> getAvailableNodes(Screen gui)
    {
        return null;
    }
    
    @Override
    public void onGuiOpen(Screen gui)
    {
        this.readFromNBT(this.getNBT());
    }
    
    @Override
    public void onGuiClose(Screen gui)
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
    public void synchToServer(CompoundNBT nbt)
    {
    }
    
    public void readFromNBT(CompoundNBT nbt0)
    {
        if (!nbt0.contains(Visibilis.MOD_ID))
        {
            this.print = this.createNewPrint();
            return;
        }
        
        CompoundNBT nbt = nbt0.getCompound(Visibilis.MOD_ID);
        this.print = NBTUtility.loadPrintFromNBT(nbt);
    }
    
    public void writeToNBT(CompoundNBT nbt0)
    {
        CompoundNBT nbt = NBTUtility.savePrintToNBT(this.print);
        nbt0.put(Visibilis.MOD_ID, nbt);
        
        this.synchToServer(nbt0);
    }
    
    public static void recPrintTree(CompoundNBT nbt)
    {
        PrintHelperBase.recPrintTree(0, nbt);
    }
    
    public static void recPrintTree(int indent, CompoundNBT nbt)
    {
        for (String key : nbt.keySet())
        {
            Visibilis.debug(PrintHelperBase.indent(indent) + key);
            
            if (nbt.get(key) instanceof ListNBT)
            {
                PrintHelperBase.recPrintList(indent + 1, nbt.getList(key, NBT.TAG_COMPOUND));
            }
            else if (nbt.get(key) instanceof CompoundNBT)
            {
                PrintHelperBase.recPrintTree(indent + 1, nbt.getCompound(key));
            }
        }
    }
    
    public static void recPrintList(int indent, ListNBT nbt0)
    {
        for (int i = 0; i < nbt0.size(); ++i)
        {
            PrintHelperBase.recPrintTree(indent + 1, nbt0.getCompound(i));
        }
    }
    
    public static String indent(int amt)
    {
        String s = "";
        
        for (; amt > 0; --amt)
        {
            s += "  ";
        }
        
        return s;
    }
}
