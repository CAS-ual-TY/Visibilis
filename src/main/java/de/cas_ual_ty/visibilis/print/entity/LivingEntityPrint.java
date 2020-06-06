package de.cas_ual_ty.visibilis.print.entity;

import de.cas_ual_ty.visibilis.print.Print;
import de.cas_ual_ty.visibilis.print.capability.CapabilityProviderPrint;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.Direction;
import net.minecraft.world.World;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.util.LazyOptional;

public abstract class LivingEntityPrint extends LivingEntity implements IEntityPrint
{
    protected Print print;
    protected LazyOptional<Print> printOptional;
    
    protected LivingEntityPrint(EntityType<? extends LivingEntity> entityTypeIn, World worldIn)
    {
        super(entityTypeIn, worldIn);
        this.print = new Print();
        this.printOptional = LazyOptional.of(() -> this.getPrint());
    }
    
    @Override
    public Print getPrint()
    {
        return this.print;
    }
    
    @Override
    public <T> LazyOptional<T> getCapability(Capability<T> cap, Direction side)
    {
        return cap == CapabilityProviderPrint.CAPABILITY_PRINT ? CapabilityProviderPrint.CAPABILITY_PRINT.orEmpty(cap, this.printOptional) : super.getCapability(cap, side);
    }
    
    @Override
    public void readAdditional(CompoundNBT compound)
    {
        super.readAdditional(compound);
        this.getPrint().readFromNBT(compound);
    }
    
    @Override
    public void writeAdditional(CompoundNBT compound)
    {
        super.readAdditional(compound);
        this.getPrint().writeToNBT(compound);
    }
}
