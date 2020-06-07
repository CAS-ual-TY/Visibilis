package de.cas_ual_ty.visibilis.print.entity;

import de.cas_ual_ty.visibilis.print.Print;
import de.cas_ual_ty.visibilis.print.capability.CapabilityProviderPrintHolder;
import de.cas_ual_ty.visibilis.print.capability.IPrintHolder;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.Direction;
import net.minecraft.world.World;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.util.LazyOptional;

public abstract class EntityPrint extends Entity implements IEntityPrint
{
    protected Print print;
    protected LazyOptional<IPrintHolder> printOptional;
    
    public EntityPrint(EntityType<?> entityTypeIn, World worldIn)
    {
        super(entityTypeIn, worldIn);
        this.print = new Print();
        this.printOptional = LazyOptional.of(() -> this);
    }
    
    @Override
    public Print getPrint()
    {
        return this.print;
    }
    
    @Override
    public void setPrint(Print print)
    {
        this.print = print;
    }
    
    @Override
    public <T> LazyOptional<T> getCapability(Capability<T> cap, Direction side)
    {
        return cap == CapabilityProviderPrintHolder.CAPABILITY_PRINT_HOLDER ? CapabilityProviderPrintHolder.CAPABILITY_PRINT_HOLDER.orEmpty(cap, this.printOptional) : super.getCapability(cap, side);
    }
    
    @Override
    protected void readAdditional(CompoundNBT compound)
    {
        this.getPrint().readFromNBT(compound);
    }
    
    @Override
    protected void writeAdditional(CompoundNBT compound)
    {
        this.getPrint().writeToNBT(compound);
    }
}
