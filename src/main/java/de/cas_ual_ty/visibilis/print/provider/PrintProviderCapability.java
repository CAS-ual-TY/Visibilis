package de.cas_ual_ty.visibilis.print.provider;

import de.cas_ual_ty.visibilis.print.capability.CapabilityProviderPrintHolder;
import de.cas_ual_ty.visibilis.util.VNBTUtility;
import net.minecraft.nbt.CompoundNBT;
import net.minecraftforge.common.capabilities.CapabilityProvider;

public abstract class PrintProviderCapability extends PrintProvider
{
    public CapabilityProvider<?> capabilityProvider;
    
    public PrintProviderCapability(NodeListProvider nodeListProvider, CapabilityProvider<?> capabilityProvider)
    {
        super(nodeListProvider);
        this.capabilityProvider = capabilityProvider;
    }
    
    @Override
    public void init()
    {
        this.undoList.setFirst(this.capabilityProvider.getCapability(CapabilityProviderPrintHolder.CAPABILITY_PRINT_HOLDER).orElseThrow(() -> new IllegalArgumentException("LazyOptional must not be empty!")).getPrint());
        super.init();
    }
    
    @Override
    public void onGuiClose()
    {
        this.synchToServer(VNBTUtility.savePrintToNBT(this.getPrint()));
        super.onGuiClose();
    }
    
    public abstract void synchToServer(CompoundNBT nbt);
}
