package de.cas_ual_ty.visibilis.util;

import java.util.function.Supplier;

import de.cas_ual_ty.visibilis.datatype.DataType;
import net.minecraftforge.common.ForgeConfigSpec.Builder;
import net.minecraftforge.common.ForgeConfigSpec.DoubleValue;

public class VConfigUtility
{
    public static final String[] COLORS = new String[] { "Red", "Green", "Blue" };
    public static final String[] COLORS_LOWER = new String[] { VConfigUtility.COLORS[0].toLowerCase(), VConfigUtility.COLORS[1].toLowerCase(), VConfigUtility.COLORS[2].toLowerCase() };
    
    public static VConfigUtility.ColorLoadHelper buildColorConfigValue(Builder builder, String modId, String dataTypeName, Supplier<DataType<?>> dataType, float[] color, float[] textColor)
    {
        DoubleValue[] colorValues = new DoubleValue[3];
        DoubleValue[] textColorValues = new DoubleValue[3];
        
        builder.push(dataTypeName);
        
        builder.push("background");
        for(int i = 0; i < VConfigUtility.COLORS.length; ++i)
        {
            colorValues[i] = builder
                //                .comment(VConfigUtility.COLORS[i] + " Color")
                .translation(modId + ".config." + dataTypeName + "." + VConfigUtility.COLORS_LOWER[i])
                .defineInRange(VConfigUtility.COLORS_LOWER[i], color[i], 0F, 1F);
        }
        builder.pop();
        
        builder.push("text");
        for(int i = 0; i < VConfigUtility.COLORS.length; ++i)
        {
            textColorValues[i] = builder
                //                .comment(VConfigUtility.COLORS[i] + " Text Color")
                .translation(modId + ".config." + dataTypeName + "." + VConfigUtility.COLORS_LOWER[i])
                .defineInRange(VConfigUtility.COLORS_LOWER[i], textColor[i], 0F, 1F);
        }
        builder.pop();
        
        builder.pop();
        
        return new VConfigUtility.ColorLoadHelper(dataType, () -> VConfigUtility.toColor(colorValues), () -> VConfigUtility.toColor(textColorValues));
    }
    
    public static float[] toColor(DoubleValue[] values)
    {
        return new float[] { values[0].get().floatValue(), values[1].get().floatValue(), values[2].get().floatValue() };
    }
    
    public static float[] toColor(int r, int g, int b)
    {
        return VUtility.toColor(r, g, b);
    }
    
    public static class ColorLoadHelper
    {
        public final Supplier<DataType<?>> dataType;
        public final Supplier<float[]> color;
        public final Supplier<float[]> textColor;
        
        public ColorLoadHelper(Supplier<DataType<?>> dataType, Supplier<float[]> color, Supplier<float[]> textColor)
        {
            this.dataType = dataType;
            this.color = color;
            this.textColor = textColor;
        }
        
        public void overrideColors()
        {
            this.dataType.get().setColor(this.color.get()).setTextColor(this.textColor.get());
        }
    }
}
