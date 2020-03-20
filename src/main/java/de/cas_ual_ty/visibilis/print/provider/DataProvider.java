package de.cas_ual_ty.visibilis.print.provider;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.function.BiConsumer;

import de.cas_ual_ty.visibilis.print.Print;
import net.minecraft.command.CommandSource;
import net.minecraft.entity.Entity;
import net.minecraft.util.text.ITextComponent;

public class DataProvider
{
    protected Print print;
    protected BiConsumer<ITextComponent, Boolean> feedback;
    
    protected Map<DataKey<?>, Object> map;
    
    public DataProvider(Print print, BiConsumer<ITextComponent, Boolean> feedback)
    {
        this.print = print;
        this.feedback = feedback;
        this.map = new HashMap<>();
    }
    
    public DataProvider(Print print, CommandSource source)
    {
        this(print, source::sendFeedback);
        this.addData(DataKey.KEY_COMMAND_SOURCE, source);
        this.addData(DataKey.KEY_WORLD, source.getWorld());
    }
    
    public DataProvider(Print print, Entity entity)
    {
        this(print, entity.getCommandSource());
        this.addData(DataKey.KEY_ENTITY, entity);
    }
    
    public Print getPrint()
    {
        return this.print;
    }
    
    public void sendFeedback(ITextComponent text, boolean allowFeedback)
    {
        this.feedback.accept(text, allowFeedback);
    }
    
    public <I> DataProvider addData(DataKey<I> key, I object)
    {
        this.map.put(key, object);
        return this;
    }
    
    public <I> I getData(DataKey<I> key)
    {
        return key.cast(this.map.get(key));
    }
    
    public <I> Optional<I> getDataOptional(DataKey<I> key)
    {
        return Optional.ofNullable(key.cast(this.map.get(key)));
    }
}
