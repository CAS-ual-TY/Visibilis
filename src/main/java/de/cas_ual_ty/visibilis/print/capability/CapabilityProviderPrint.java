package de.cas_ual_ty.visibilis.print.capability;

import de.cas_ual_ty.visibilis.print.Print;
import net.minecraft.nbt.INBT;
import net.minecraft.util.Direction;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityInject;
import net.minecraftforge.common.capabilities.ICapabilitySerializable;
import net.minecraftforge.common.util.LazyOptional;

public class CapabilityProviderPrint implements ICapabilitySerializable<INBT>
{
    @CapabilityInject(value = Print.class)
    public static final Capability<Print> CAPABILITY_PRINT = null;
    
    private LazyOptional<Print> instance = LazyOptional.of(CapabilityProviderPrint.CAPABILITY_PRINT::getDefaultInstance);
    
    @Override
    public <T> LazyOptional<T> getCapability(Capability<T> cap, Direction side)
    {
        return cap == CapabilityProviderPrint.CAPABILITY_PRINT ? this.instance.cast() : LazyOptional.empty();
    }
    
    @Override
    public INBT serializeNBT()
    {
        return CapabilityProviderPrint.CAPABILITY_PRINT.getStorage().writeNBT(CapabilityProviderPrint.CAPABILITY_PRINT, this.instance.orElseThrow(() -> new IllegalArgumentException("LazyOptional must not be empty!")), null);
    }
    
    @Override
    public void deserializeNBT(INBT nbt)
    {
        CapabilityProviderPrint.CAPABILITY_PRINT.getStorage().readNBT(CapabilityProviderPrint.CAPABILITY_PRINT, this.instance.orElseThrow(() -> new IllegalArgumentException("LazyOptional must not be empty!")), null, nbt);
    }
    
    public Runnable getListener()
    {
        return this.instance::invalidate;
    }
}
