package de.cas_ual_ty.visibilis.print.entity;

import de.cas_ual_ty.visibilis.Visibilis;
import de.cas_ual_ty.visibilis.print.provider.NodeListProvider;
import de.cas_ual_ty.visibilis.print.provider.PrintProviderCapability;
import net.minecraft.entity.Entity;
import net.minecraft.nbt.CompoundNBT;

public class PrintProviderEntity extends PrintProviderCapability
{
    public Entity entity;
    
    public PrintProviderEntity(NodeListProvider nodeListProvider, Entity entity)
    {
        super(nodeListProvider, entity);
        this.entity = entity;
    }
    
    @Override
    public void synchToServer(CompoundNBT nbt)
    {
        Visibilis.channel.sendToServer(new MessageEntity(this.entity));
    }
}
