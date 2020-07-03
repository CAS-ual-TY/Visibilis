package de.cas_ual_ty.visibilis.print.entity;

import de.cas_ual_ty.visibilis.print.Print;
import de.cas_ual_ty.visibilis.print.capability.IPrintHolder;
import de.cas_ual_ty.visibilis.print.capability.PrintHolderCapabilityProvider;
import de.cas_ual_ty.visibilis.util.VNBTUtility;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.Direction;
import net.minecraft.world.World;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.util.LazyOptional;

public abstract class PrintLivingEntity extends LivingEntity implements IPrintEntity
{
    protected Print print;
    protected LazyOptional<IPrintHolder> printOptional;
    
    protected PrintLivingEntity(EntityType<? extends LivingEntity> entityTypeIn, World worldIn)
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
    public void readAdditional(CompoundNBT compound)
    {
        super.readAdditional(compound);
        VNBTUtility.readPrintFromNBT(this.getPrint(), compound, true);
    }
    
    @Override
    public void writeAdditional(CompoundNBT compound)
    {
        super.readAdditional(compound);
        VNBTUtility.writePrintToNBT(this.getPrint(), compound, true);
    }
}
