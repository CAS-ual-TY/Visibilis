package de.cas_ual_ty.visibilis.event;

import com.mojang.brigadier.builder.LiteralArgumentBuilder;

import net.minecraft.command.CommandSource;
import net.minecraftforge.eventbus.api.Event;

public class CommandBuilderEvent extends Event
{
    private LiteralArgumentBuilder<CommandSource> base;
    
    public CommandBuilderEvent(LiteralArgumentBuilder<CommandSource> base)
    {
        this.base = base;
    }
    
    public LiteralArgumentBuilder<CommandSource> getArgumentBase()
    {
        return this.base;
    }
}
