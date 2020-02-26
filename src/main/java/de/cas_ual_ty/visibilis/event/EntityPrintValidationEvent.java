package de.cas_ual_ty.visibilis.event;

import net.minecraft.entity.Entity;
import net.minecraftforge.eventbus.api.Event;

public class EntityPrintValidationEvent extends Event
{
    public final Entity entity;
    
    public EntityPrintValidationEvent(Entity entity)
    {
        this.entity = entity;
    }
    
    @Override
    public boolean isCancelable()
    {
        return true;
    }
}
