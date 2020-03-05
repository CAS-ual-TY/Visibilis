package de.cas_ual_ty.visibilis.print.entity;

import de.cas_ual_ty.visibilis.node.dataprovider.DataProvider;
import net.minecraft.entity.Entity;

public class DataProviderEntity extends DataProvider
{
    protected Entity entity;
    
    public DataProviderEntity(Entity entity)
    {
        super(entity.getCommandSource());
        this.entity = entity;
    }
    
    public Entity getEntity()
    {
        return this.entity;
    }
}
