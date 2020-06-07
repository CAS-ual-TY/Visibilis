package de.cas_ual_ty.visibilis.print.capability;

import de.cas_ual_ty.visibilis.print.Print;
import de.cas_ual_ty.visibilis.util.VNBTUtility;
import net.minecraft.nbt.CompoundNBT;

public interface IPrintHolder
{
    public void setPrint(Print print);
    
    public Print getPrint();
    
    public default void setPrint(CompoundNBT nbt)
    {
        this.setPrint(VNBTUtility.loadPrintFromNBT(nbt, true));
    }
}
