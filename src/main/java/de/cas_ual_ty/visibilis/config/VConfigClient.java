package de.cas_ual_ty.visibilis.config;

import de.cas_ual_ty.visibilis.Visibilis;
import de.cas_ual_ty.visibilis.datatype.DataType;
import de.cas_ual_ty.visibilis.util.VUtility;
import net.minecraftforge.common.ForgeConfigSpec.Builder;
import net.minecraftforge.common.ForgeConfigSpec.DoubleValue;

public class VConfigClient
{
    public static final String[] COLORS = new String[] { "Red", "Green", "Blue" };
    
    public static final String[] SUFFIX_COLOR = new String[] { "_" + VConfigClient.COLORS[0].toLowerCase(), "_" + VConfigClient.COLORS[1].toLowerCase(), "_" + VConfigClient.COLORS[2].toLowerCase() };
    
    public static final String KEY_COLOR_EXEC = "exec";
    public static final String KEY_COLOR_OBJECT = "object";
    public static final String KEY_COLOR_INTEGER = "integer";
    public static final String KEY_COLOR_FLOAT = "float";
    public static final String KEY_COLOR_DOUBLE = "double";
    public static final String KEY_COLOR_NUMBER = "number";
    public static final String KEY_COLOR_BOOLEAN = "boolean";
    public static final String KEY_COLOR_STRING = "string";
    public static final String KEY_COLOR_VECTOR3D = "vector3d";
    public static final String KEY_COLOR_PLAYER = "player";
    public static final String KEY_COLOR_BLOCK_POS = "block_pos";
    public static final String KEY_COLOR_WORLD = "world";
    
    public final DoubleValue[] color_exec;
    public final DoubleValue[] text_color_exec;
    public final DoubleValue[] color_object;
    public final DoubleValue[] text_color_object;
    public final DoubleValue[] color_integer;
    public final DoubleValue[] text_color_integer;
    public final DoubleValue[] color_float;
    public final DoubleValue[] text_color_float;
    public final DoubleValue[] color_double;
    public final DoubleValue[] text_color_double;
    public final DoubleValue[] color_number;
    public final DoubleValue[] text_color_number;
    public final DoubleValue[] color_boolean;
    public final DoubleValue[] text_color_boolean;
    public final DoubleValue[] color_string;
    public final DoubleValue[] text_color_string;
    public final DoubleValue[] color_vector3d;
    public final DoubleValue[] text_color_vector3d;
    public final DoubleValue[] color_player;
    public final DoubleValue[] text_color_player;
    public final DoubleValue[] color_block_pos;
    public final DoubleValue[] text_color_block_pos;
    public final DoubleValue[] color_world;
    public final DoubleValue[] text_color_world;
    
    public VConfigClient(Builder builder)
    {
        builder.push("colors");
        
        this.color_exec = VUtility.buildColorConfigValue(builder, Visibilis.MOD_ID + ".config.", VConfigClient.KEY_COLOR_EXEC, new float[] { 1F, 0F, 0F });
        this.color_object = VUtility.buildColorConfigValue(builder, Visibilis.MOD_ID + ".config.", VConfigClient.KEY_COLOR_OBJECT, new float[] { 0.25F, 0.25F, 0.25F });
        this.color_integer = VUtility.buildColorConfigValue(builder, Visibilis.MOD_ID + ".config.", VConfigClient.KEY_COLOR_INTEGER, new float[] { 1F, 1F, 0.75F });
        this.color_float = VUtility.buildColorConfigValue(builder, Visibilis.MOD_ID + ".config.", VConfigClient.KEY_COLOR_FLOAT, new float[] { 1F, 1F, 0.5F });
        this.color_double = VUtility.buildColorConfigValue(builder, Visibilis.MOD_ID + ".config.", VConfigClient.KEY_COLOR_DOUBLE, new float[] { 1F, 1F, 0.25F });
        this.color_number = VUtility.buildColorConfigValue(builder, Visibilis.MOD_ID + ".config.", VConfigClient.KEY_COLOR_NUMBER, new float[] { 1F, 1F, 0F });
        this.color_boolean = VUtility.buildColorConfigValue(builder, Visibilis.MOD_ID + ".config.", VConfigClient.KEY_COLOR_BOOLEAN, new float[] { 1F, 0F, 1F });
        this.color_string = VUtility.buildColorConfigValue(builder, Visibilis.MOD_ID + ".config.", VConfigClient.KEY_COLOR_STRING, new float[] { 1F, 1F, 1F });
        this.color_vector3d = VUtility.buildColorConfigValue(builder, Visibilis.MOD_ID + ".config.", VConfigClient.KEY_COLOR_VECTOR3D, new float[] { 1F, 0.75F, 0.75F });
        this.color_player = VUtility.buildColorConfigValue(builder, Visibilis.MOD_ID + ".config.", VConfigClient.KEY_COLOR_PLAYER, new float[] { 84 / 256F, 150 / 256F, 231 / 256F });
        this.color_block_pos = VUtility.buildColorConfigValue(builder, Visibilis.MOD_ID + ".config.", VConfigClient.KEY_COLOR_BLOCK_POS, new float[] { 1F, 0.75F, 0.75F });
        this.color_world = VUtility.buildColorConfigValue(builder, Visibilis.MOD_ID + ".config.", VConfigClient.KEY_COLOR_WORLD, new float[] { 38 / 256F, 102 / 256F, 180 / 256F });
        
        builder.pop();
        
        builder.push("text_colors");
        
        this.text_color_exec = VUtility.buildTextColorConfigValue(builder, Visibilis.MOD_ID + ".config.", VConfigClient.KEY_COLOR_EXEC, DataType.COLOR_TEXT_WHITE);
        this.text_color_object = VUtility.buildTextColorConfigValue(builder, Visibilis.MOD_ID + ".config.", VConfigClient.KEY_COLOR_OBJECT, DataType.COLOR_TEXT_WHITE);
        this.text_color_integer = VUtility.buildTextColorConfigValue(builder, Visibilis.MOD_ID + ".config.", VConfigClient.KEY_COLOR_INTEGER, DataType.COLOR_TEXT_BLACK);
        this.text_color_float = VUtility.buildTextColorConfigValue(builder, Visibilis.MOD_ID + ".config.", VConfigClient.KEY_COLOR_FLOAT, DataType.COLOR_TEXT_BLACK);
        this.text_color_double = VUtility.buildTextColorConfigValue(builder, Visibilis.MOD_ID + ".config.", VConfigClient.KEY_COLOR_DOUBLE, DataType.COLOR_TEXT_BLACK);
        this.text_color_number = VUtility.buildTextColorConfigValue(builder, Visibilis.MOD_ID + ".config.", VConfigClient.KEY_COLOR_NUMBER, DataType.COLOR_TEXT_BLACK);
        this.text_color_boolean = VUtility.buildTextColorConfigValue(builder, Visibilis.MOD_ID + ".config.", VConfigClient.KEY_COLOR_BOOLEAN, DataType.COLOR_TEXT_WHITE);
        this.text_color_string = VUtility.buildTextColorConfigValue(builder, Visibilis.MOD_ID + ".config.", VConfigClient.KEY_COLOR_STRING, DataType.COLOR_TEXT_BLACK);
        this.text_color_vector3d = VUtility.buildTextColorConfigValue(builder, Visibilis.MOD_ID + ".config.", VConfigClient.KEY_COLOR_VECTOR3D, DataType.COLOR_TEXT_BLACK);
        this.text_color_player = VUtility.buildTextColorConfigValue(builder, Visibilis.MOD_ID + ".config.", VConfigClient.KEY_COLOR_PLAYER, DataType.COLOR_TEXT_WHITE);
        this.text_color_block_pos = VUtility.buildTextColorConfigValue(builder, Visibilis.MOD_ID + ".config.", VConfigClient.KEY_COLOR_BLOCK_POS, DataType.COLOR_TEXT_BLACK);
        this.text_color_world = VUtility.buildTextColorConfigValue(builder, Visibilis.MOD_ID + ".config.", VConfigClient.KEY_COLOR_WORLD, DataType.COLOR_TEXT_WHITE);
        
        builder.pop();
    }
}
