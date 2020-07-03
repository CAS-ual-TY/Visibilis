package de.cas_ual_ty.visibilis.print.entity;

import de.cas_ual_ty.visibilis.Visibilis;
import de.cas_ual_ty.visibilis.print.provider.CapabilityPrintProvider;
import de.cas_ual_ty.visibilis.print.provider.NodeListProvider;
import net.minecraft.entity.Entity;
import net.minecraft.nbt.CompoundNBT;

public class EntityPrintProvider extends CapabilityPrintProvider
{
    public Entity entity;
    
    public EntityPrintProvider(NodeListProvider nodeListProvider, Entity entity)
    {
        super(nodeListProvider, entity);
        this.entity = entity;
    }
    
    @Override
    public void onGuiClose()
    {
        super.onGuiClose();
    }
    
    @Override
    public void synchToServer(CompoundNBT nbt)
    {
        Visibilis.channel.sendToServer(new SynchEntityToServerMessage(this.entity, this.getPrint()));
    }
}
