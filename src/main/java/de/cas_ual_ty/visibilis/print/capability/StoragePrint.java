package de.cas_ual_ty.visibilis.print.capability;

import de.cas_ual_ty.visibilis.print.Print;
import de.cas_ual_ty.visibilis.util.VNBTUtility;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.INBT;
import net.minecraft.util.Direction;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.Capability.IStorage;

public class StoragePrint implements IStorage<Print>
{
    @Override
    public INBT writeNBT(Capability<Print> capability, Print instance, Direction side)
    {
        return VNBTUtility.savePrintToNBT(instance);
    }
    
    @Override
    public void readNBT(Capability<Print> capability, Print instance, Direction side, INBT nbt)
    {
        VNBTUtility.readPrintFromNBT(instance, (CompoundNBT)nbt);
    }
}
