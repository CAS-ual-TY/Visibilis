package de.cas_ual_ty.visibilis.print.provider;

import de.cas_ual_ty.visibilis.print.capability.PrintHolderCapabilityProvider;
import de.cas_ual_ty.visibilis.util.VNBTUtility;
import net.minecraft.nbt.CompoundNBT;
import net.minecraftforge.common.capabilities.CapabilityProvider;

public abstract class CapabilityPrintProvider extends PrintProvider
{
    public CapabilityProvider<?> capabilityProvider;
    
    public CapabilityPrintProvider(NodeListProvider nodeListProvider, CapabilityProvider<?> capabilityProvider)
    {
        super(nodeListProvider);
        this.capabilityProvider = capabilityProvider;
    }
    
    @Override
    public void init()
    {
        this.undoList.setFirst(this.capabilityProvider.getCapability(PrintHolderCapabilityProvider.CAPABILITY_PRINT_HOLDER).orElseThrow(() -> new IllegalArgumentException("LazyOptional must not be empty!")).getPrint());
        super.init();
    }
    
    @Override
    public void onGuiClose()
    {
        this.synchToServer(VNBTUtility.savePrintToNBT(this.getPrint(), false));
        super.onGuiClose();
    }
    
    public abstract void synchToServer(CompoundNBT nbt);
}
