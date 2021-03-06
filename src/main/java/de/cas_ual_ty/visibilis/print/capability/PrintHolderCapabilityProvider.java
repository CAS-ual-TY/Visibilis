package de.cas_ual_ty.visibilis.print.capability;

import net.minecraft.nbt.INBT;
import net.minecraft.util.Direction;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityInject;
import net.minecraftforge.common.capabilities.ICapabilitySerializable;
import net.minecraftforge.common.util.LazyOptional;

public class PrintHolderCapabilityProvider implements ICapabilitySerializable<INBT>
{
    @CapabilityInject(value = IPrintHolder.class)
    public static final Capability<IPrintHolder> CAPABILITY_PRINT_HOLDER = null;
    
    private LazyOptional<IPrintHolder> instance = LazyOptional.of(PrintHolderCapabilityProvider.CAPABILITY_PRINT_HOLDER::getDefaultInstance);
    
    @Override
    public <T> LazyOptional<T> getCapability(Capability<T> cap, Direction side)
    {
        return cap == PrintHolderCapabilityProvider.CAPABILITY_PRINT_HOLDER ? this.instance.cast() : LazyOptional.empty();
    }
    
    @Override
    public INBT serializeNBT()
    {
        return PrintHolderCapabilityProvider.CAPABILITY_PRINT_HOLDER.getStorage().writeNBT(PrintHolderCapabilityProvider.CAPABILITY_PRINT_HOLDER, this.instance.orElseThrow(() -> new IllegalArgumentException("LazyOptional must not be empty!")), null);
    }
    
    @Override
    public void deserializeNBT(INBT nbt)
    {
        PrintHolderCapabilityProvider.CAPABILITY_PRINT_HOLDER.getStorage().readNBT(PrintHolderCapabilityProvider.CAPABILITY_PRINT_HOLDER, this.instance.orElseThrow(() -> new IllegalArgumentException("LazyOptional must not be empty!")), null, nbt);
    }
    
    public Runnable getListener()
    {
        return this.instance::invalidate;
    }
}
