package de.cas_ual_ty.visibilis.registries;

import de.cas_ual_ty.visibilis.Visibilis;
import de.cas_ual_ty.visibilis.datatype.DataType;
import de.cas_ual_ty.visibilis.datatype.DataTypeDynamic;
import de.cas_ual_ty.visibilis.datatype.DataTypeEnum;
import de.cas_ual_ty.visibilis.datatype.converter.AnyString;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber.Bus;
import net.minecraftforge.registries.IForgeRegistry;
import net.minecraftforge.registries.ObjectHolder;

@EventBusSubscriber(modid = Visibilis.MOD_ID, bus = Bus.MOD)
@ObjectHolder(Visibilis.MOD_ID)
public class VDataTypes
{
    public static final DataType<Object> EXEC = null;
    public static final DataTypeDynamic<Integer> INTEGER = null;
    public static final DataTypeDynamic<Float> FLOAT = null;
    public static final DataTypeDynamic<Double> DOUBLE = null;
    public static final DataType<Number> NUMBER = null;
    public static final DataTypeEnum<Boolean> BOOLEAN = null;
    public static final DataTypeDynamic<String> STRING = null;
    public static final DataType<Vec3d> VECTOR3D = null;
    public static final DataType<BlockPos> BLOCK_POS = null;
    public static final DataType<PlayerEntity> PLAYER = null;
    
    @SubscribeEvent
    public static void register(RegistryEvent.Register<DataType<?>> event)
    {
        IForgeRegistry<DataType<?>> registry = event.getRegistry();
        
        registry.register(new DataType<>(new float[] { 1F, 0F, 0F }, (length) -> null).setRegistryName(Visibilis.MOD_ID, "exec"));
        
        registry.register(new DataTypeDynamic<Integer>(new float[] { 1F, 1F, 0.75F }, (length) -> new Integer[length], 1)
        {
            @Override
            public boolean equals(Integer obj1, Integer obj2)
            {
                return obj1.intValue() == obj2.intValue();
            }
            
            @Override
            public boolean canParseString(String s)
            {
                if(s.equals("-"))
                {
                    return true;
                }
                
                try
                {
                    this.stringToValue(s);
                    return true;
                }
                catch (NumberFormatException e)
                {
                    return false;
                }
            }
            
            @Override
            public Integer stringToValue(String s)
            {
                if(s.equals("-"))
                {
                    return 0;
                }
                return Integer.parseInt(s);
            }
            
            @Override
            public Integer loadFromNBT(CompoundNBT nbt)
            {
                return nbt.getInt(DataType.KEY_DATA);
            }
            
            @Override
            public CompoundNBT saveToNBT(Integer data)
            {
                CompoundNBT nbt = new CompoundNBT();
                nbt.putInt(DataType.KEY_DATA, data.intValue());
                return nbt;
            }
        }.setBlackText().setRegistryName(Visibilis.MOD_ID, "integer"));
        
        registry.register(new DataTypeDynamic<Float>(new float[] { 1F, 1F, 0.5F }, (length) -> new Float[length], 1.0F)
        {
            @Override
            public boolean equals(Float obj1, Float obj2)
            {
                return obj1.floatValue() == obj2.floatValue();
            }
            
            @Override
            public boolean canParseString(String s)
            {
                if(s.equals("-"))
                {
                    return true;
                }
                
                try
                {
                    this.stringToValue(s);
                    return true;
                }
                catch (NumberFormatException e)
                {
                    return false;
                }
            }
            
            @Override
            public Float stringToValue(String s)
            {
                if(s.equals("-"))
                {
                    return 0F;
                }
                return Float.parseFloat(s);
            }
            
            @Override
            public Float loadFromNBT(CompoundNBT nbt)
            {
                return nbt.getFloat(DataType.KEY_DATA);
            }
            
            @Override
            public CompoundNBT saveToNBT(Float data)
            {
                CompoundNBT nbt = new CompoundNBT();
                nbt.putFloat(DataType.KEY_DATA, data.floatValue());
                return nbt;
            }
        }.setBlackText().setRegistryName(Visibilis.MOD_ID, "float"));
        
        registry.register(new DataTypeDynamic<Double>(new float[] { 1F, 1F, 0.25F }, (length) -> new Double[length], 1.0D)
        {
            @Override
            public boolean equals(Double obj1, Double obj2)
            {
                return obj1.doubleValue() == obj2.doubleValue();
            }
            
            @Override
            public boolean canParseString(String s)
            {
                if(s.equals("-"))
                {
                    return true;
                }
                
                try
                {
                    this.stringToValue(s);
                    return true;
                }
                catch (NumberFormatException e)
                {
                    return false;
                }
            }
            
            @Override
            public Double stringToValue(String s)
            {
                if(s.equals("-"))
                {
                    return 0D;
                }
                return Double.parseDouble(s);
            }
            
            @Override
            public Double loadFromNBT(CompoundNBT nbt)
            {
                return nbt.getDouble(DataType.KEY_DATA);
            }
            
            @Override
            public CompoundNBT saveToNBT(Double data)
            {
                CompoundNBT nbt = new CompoundNBT();
                nbt.putDouble(DataType.KEY_DATA, data.doubleValue());
                return nbt;
            }
        }.setBlackText().setRegistryName(Visibilis.MOD_ID, "double"));
        
        registry.register(new DataType<>(new float[] { 1F, 1F, 0F }, (length) -> new Number[length]).setRegistryName(Visibilis.MOD_ID, "number").setBlackText());
        
        registry.register(new DataTypeEnum<Boolean>(new float[] { 1F, 0F, 1F }, (length) -> new Boolean[length])
        {
            @Override
            public boolean equals(Boolean obj1, Boolean obj2)
            {
                return obj1.booleanValue() == obj2.booleanValue();
            }
        }.addEnum(false).addEnum(true).setRegistryName(Visibilis.MOD_ID, "boolean"));
        
        registry.register(new DataTypeDynamic<String>(new float[] { 1F, 1F, 1F }, (length) -> new String[length], "text")
        {
            @Override
            public boolean canParseString(String s)
            {
                return true; // TODO Maybe not everything should be allowed? eg \". Problems when loading from NBT?
            }
            
            @Override
            public String stringToValue(String s)
            {
                return s;
            }
            
            @Override
            public String loadFromNBT(CompoundNBT nbt)
            {
                return nbt.getString(DataType.KEY_DATA);
            }
            
            @Override
            public CompoundNBT saveToNBT(String data)
            {
                CompoundNBT nbt = new CompoundNBT();
                nbt.putString(DataType.KEY_DATA, data);
                return nbt;
            }
        }.setBlackText().setRegistryName(Visibilis.MOD_ID, "string"));
        
        registry.register(new DataType<>(new float[] { 1F, 0.5F, 0.5F }, (length) -> new Vec3d[length]).setRegistryName(Visibilis.MOD_ID, "vector3d").setBlackText());
        registry.register(new DataType<>(new float[] { 1F, 0.75F, 0.75F }, (length) -> new BlockPos[length]).setRegistryName(Visibilis.MOD_ID, "block_pos").setBlackText());
        
        registry.register(new DataType<>(new float[] { 0.5F, 0F, 1F }, (length) -> new PlayerEntity[length]).setRegistryName(Visibilis.MOD_ID, "player")/*.setBlackText()*/);
    }
    
    // Called from FMLCommonSetupEvent
    public static void addConverters()
    {
        VDataTypes.FLOAT.registerConverter(VDataTypes.INTEGER, (n) -> n.floatValue());
        VDataTypes.DOUBLE.registerConverter(VDataTypes.INTEGER, (n) -> n.doubleValue());
        VDataTypes.DOUBLE.registerConverter(VDataTypes.FLOAT, (n) -> n.doubleValue());
        
        VDataTypes.NUMBER.registerGenericConverter(VDataTypes.INTEGER);
        VDataTypes.NUMBER.registerGenericConverter(VDataTypes.FLOAT);
        VDataTypes.NUMBER.registerGenericConverter(VDataTypes.DOUBLE);
        
        VDataTypes.INTEGER.registerConverter(VDataTypes.NUMBER, (n) -> n.intValue());
        VDataTypes.FLOAT.registerConverter(VDataTypes.NUMBER, (n) -> n.floatValue());
        VDataTypes.DOUBLE.registerConverter(VDataTypes.NUMBER, (n) -> n.doubleValue());
        
        VDataTypes.STRING.registerConverter(VDataTypes.INTEGER, new AnyString<>());
        VDataTypes.STRING.registerConverter(VDataTypes.FLOAT, new AnyString<>());
        VDataTypes.STRING.registerConverter(VDataTypes.DOUBLE, new AnyString<>());
        VDataTypes.STRING.registerConverter(VDataTypes.NUMBER, new AnyString<>());
        VDataTypes.STRING.registerConverter(VDataTypes.BOOLEAN, new AnyString<>());
        VDataTypes.STRING.registerConverter(VDataTypes.VECTOR3D, new AnyString<>());
        VDataTypes.STRING.registerConverter(VDataTypes.PLAYER, new AnyString<>());
        VDataTypes.STRING.registerConverter(VDataTypes.BLOCK_POS, new AnyString<>());
    }
}
