package de.cas_ual_ty.visibilis.config;

import java.util.LinkedList;
import java.util.List;

import de.cas_ual_ty.visibilis.Visibilis;
import de.cas_ual_ty.visibilis.registries.VDataTypes;
import de.cas_ual_ty.visibilis.util.VConfigUtility;
import de.cas_ual_ty.visibilis.util.VUtility;
import net.minecraftforge.common.ForgeConfigSpec.Builder;

public class VConfigClient
{
    public final List<VConfigUtility.ColorLoadHelper> list;
    
    public VConfigClient(Builder builder)
    {
        builder.push("datatypes");
        
        this.list = new LinkedList<>();
        this.list.add(VConfigUtility.buildColorConfigValue(builder, Visibilis.MOD_ID, "exec", () -> VDataTypes.EXEC, new float[] { 1.0F, 0F, 0F }, VUtility.COLOR_DEFAULT_WHITE));
        this.list.add(VConfigUtility.buildColorConfigValue(builder, Visibilis.MOD_ID, "object", () -> VDataTypes.OBJECT, new float[] { 0.25F, 0.25F, 0.25F }, VUtility.COLOR_DEFAULT_WHITE));
        this.list.add(VConfigUtility.buildColorConfigValue(builder, Visibilis.MOD_ID, "integer", () -> VDataTypes.INTEGER, new float[] { 1F, 1F, 0.75F }, VUtility.COLOR_DEFAULT_BLACK));
        this.list.add(VConfigUtility.buildColorConfigValue(builder, Visibilis.MOD_ID, "float", () -> VDataTypes.FLOAT, new float[] { 1F, 1F, 0.5F }, VUtility.COLOR_DEFAULT_BLACK));
        this.list.add(VConfigUtility.buildColorConfigValue(builder, Visibilis.MOD_ID, "double", () -> VDataTypes.DOUBLE, new float[] { 1F, 1F, 0.25F }, VUtility.COLOR_DEFAULT_BLACK));
        this.list.add(VConfigUtility.buildColorConfigValue(builder, Visibilis.MOD_ID, "number", () -> VDataTypes.NUMBER, new float[] { 1F, 1F, 0F }, VUtility.COLOR_DEFAULT_BLACK));
        this.list.add(VConfigUtility.buildColorConfigValue(builder, Visibilis.MOD_ID, "boolean", () -> VDataTypes.BOOLEAN, new float[] { 1F, 0F, 1F }, VUtility.COLOR_DEFAULT_WHITE));
        this.list.add(VConfigUtility.buildColorConfigValue(builder, Visibilis.MOD_ID, "string", () -> VDataTypes.STRING, new float[] { 1F, 1F, 1F }, VUtility.COLOR_DEFAULT_BLACK));
        this.list.add(VConfigUtility.buildColorConfigValue(builder, Visibilis.MOD_ID, "vector3d", () -> VDataTypes.VECTOR3D, VConfigUtility.toColor(0x30, 0x39, 0x60), new float[] { 1F, 1F, 0.25F }));
        this.list.add(VConfigUtility.buildColorConfigValue(builder, Visibilis.MOD_ID, "player", () -> VDataTypes.PLAYER, VUtility.toColor(0x53, 0x95, 0xE6), VUtility.COLOR_DEFAULT_WHITE));
        this.list.add(VConfigUtility.buildColorConfigValue(builder, Visibilis.MOD_ID, "block_pos", () -> VDataTypes.BLOCK_POS, VConfigUtility.toColor(0x30, 0x39, 0x60), new float[] { 1F, 1F, 0.75F }));
        this.list.add(VConfigUtility.buildColorConfigValue(builder, Visibilis.MOD_ID, "world", () -> VDataTypes.WORLD, VUtility.toColor(0x25, 0x65, 0xB3), VUtility.COLOR_DEFAULT_WHITE));
        
        builder.pop();
    }
    
    // Called when config is baked, VConfigHelper::bakeClient
    public void overrideColors()
    {
        for(VConfigUtility.ColorLoadHelper h : this.list)
        {
            h.overrideColors();
        }
    }
}
