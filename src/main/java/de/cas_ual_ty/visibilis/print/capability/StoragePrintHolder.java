package de.cas_ual_ty.visibilis.print.capability;

import de.cas_ual_ty.visibilis.util.VNBTUtility;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.INBT;
import net.minecraft.util.Direction;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.Capability.IStorage;

public class StoragePrintHolder implements IStorage<IPrintHolder>
{
    @Override
    public INBT writeNBT(Capability<IPrintHolder> capability, IPrintHolder instance, Direction side)
    {
        return VNBTUtility.savePrintToNBT(instance.getPrint());
    }
    
    @Override
    public void readNBT(Capability<IPrintHolder> capability, IPrintHolder instance, Direction side, INBT nbt)
    {
        instance.setPrint(VNBTUtility.loadPrintFromNBT((CompoundNBT)nbt));
    }
}
