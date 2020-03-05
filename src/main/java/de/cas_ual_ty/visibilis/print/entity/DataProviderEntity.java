package de.cas_ual_ty.visibilis.print.entity;

import de.cas_ual_ty.visibilis.print.provider.DataProvider;
import net.minecraft.command.CommandSource;
import net.minecraft.entity.Entity;

public class DataProviderEntity extends DataProvider
{
    protected Entity entity;
    
    public DataProviderEntity(Entity entity)
    {
        super(entity.getCommandSource());
        this.entity = entity;
    }
    
    public DataProviderEntity(CommandSource source)
    {
        super(source);
        this.entity = source.getEntity();
    }
    
    public Entity getEntity()
    {
        return this.entity;
    }
}
