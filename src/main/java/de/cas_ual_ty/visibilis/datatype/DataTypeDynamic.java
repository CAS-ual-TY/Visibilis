package de.cas_ual_ty.visibilis.datatype;

import com.google.common.base.Predicate;

import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.StringUtils;

public abstract class DataTypeDynamic<A> extends DataType<A>
{
    protected Predicate<String> validator;
    
    // Default value is required for this type of a data type so it is added to the constructor.
    
    public DataTypeDynamic(ArrayFactory<A> arrayFactory, A defaultValue)
    {
        super(arrayFactory);
        this.setDefaultValue(defaultValue);
        this.validator = this.createValidator();
    }
    
    /**
     * Can this string be parsed to a value in {@link #stringToValue(String)} (then return <b>true</b>)?
     */
    public abstract boolean canParseString(String s);
    
    /**
     * Turn a string into a value
     */
    public abstract A stringToValue(String s);
    
    /**
     * Returns a validator used for {@link net.minecraft.client.gui.GuiTextField}.
     */
    public Predicate<String> getValidator()
    {
        return this.validator;
    }
    
    /**
     * Creates a validator returned in {@link #getValidator()}.
     */
    protected Predicate<String> createValidator()
    {
        return (input) -> StringUtils.isNullOrEmpty(input) || this.canParseString(input);
    }
    
    @Override
    public boolean isSerializable()
    {
        return true;
    }
    
    @Override
    public A readFromNBT(CompoundNBT nbt, String key)
    {
        return this.stringToValue(nbt.getString(key));
    }
    
    @Override
    public void writeToNBT(CompoundNBT nbt, String key, A value)
    {
        nbt.putString(key, this.valueToString(value));
    }
}
