package de.cas_ual_ty.visibilis.print.entity;

import de.cas_ual_ty.visibilis.print.Print;
import de.cas_ual_ty.visibilis.print.capability.IPrintHolder;
import de.cas_ual_ty.visibilis.print.capability.PrintHolderCapabilityProvider;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.Direction;
import net.minecraft.world.World;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.util.LazyOptional;

public abstract class PrintEntity extends Entity implements IPrintEntity
{
    protected Print print;
    protected LazyOptional<IPrintHolder> printOptional;
    
    public PrintEntity(EntityType<?> entityTypeIn, World worldIn)
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
        return cap == PrintHolderCapabilityProvider.CAPABILITY_PRINT_HOLDER ? this.printOptional.cast() : super.getCapability(cap, side);
    }
    
    @Override
    protected void readAdditional(CompoundNBT compound)
    {
        this.getPrint().readFromNBT(compound, true);
    }
    
    @Override
    protected void writeAdditional(CompoundNBT compound)
    {
        this.getPrint().writeToNBT(compound, true);
    }
}
