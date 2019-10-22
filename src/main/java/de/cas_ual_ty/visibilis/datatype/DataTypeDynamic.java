package de.cas_ual_ty.visibilis.datatype;

import com.google.common.base.Predicate;
import com.google.common.base.Predicates;

import net.minecraft.util.StringUtils;

public abstract class DataTypeDynamic<A> extends DataType<A>
{
	protected Predicate<String> validator;
	
    // Default value is required for this type of a data type so it is added to the constructor.
    
    public DataTypeDynamic(String id, A defaultValue)
    {
        this(id, DataType.COLOR_DEFAULT_GREY, defaultValue);
    }
    
    public DataTypeDynamic(String id, float[] color, A defaultValue)
    {
        super(id, color);
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
        return new Predicate<String>()
        {
            @Override
            public boolean apply(String input)
            {
                return StringUtils.isNullOrEmpty(input) || DataTypeDynamic.this.canParseString(input);
            }
        };
    }
}
