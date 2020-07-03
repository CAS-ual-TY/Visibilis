package de.cas_ual_ty.visibilis.registries;

import de.cas_ual_ty.visibilis.Visibilis;
import de.cas_ual_ty.visibilis.datatype.DataType;
import de.cas_ual_ty.visibilis.datatype.DynamicDataType;
import de.cas_ual_ty.visibilis.datatype.EnumDataType;
import de.cas_ual_ty.visibilis.datatype.converter.ObjToStringConverter;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
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
    public static final DataType<Object> OBJECT = null;
    public static final DynamicDataType<Integer> INTEGER = null;
    public static final DynamicDataType<Float> FLOAT = null;
    public static final DynamicDataType<Double> DOUBLE = null;
    public static final DataType<Number> NUMBER = null;
    public static final EnumDataType<Boolean> BOOLEAN = null;
    public static final DynamicDataType<String> STRING = null;
    public static final DataType<Vec3d> VECTOR3D = null;
    public static final DataType<PlayerEntity> PLAYER = null;
    public static final DataType<BlockPos> BLOCK_POS = null;
    public static final DataType<World> WORLD = null;
    
    @SubscribeEvent
    public static void register(RegistryEvent.Register<DataType<?>> event)
    {
        IForgeRegistry<DataType<?>> registry = event.getRegistry();
        
        registry.register(new DataType<>((length) -> new Object[length]).setRegistryName(Visibilis.MOD_ID, "exec"));
        
        registry.register(new DataType<>((length) -> new Object[length]).setRegistryName(Visibilis.MOD_ID, "object"));
        
        registry.register(new DynamicDataType<Integer>((length) -> new Integer[length], 1)
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
            public Integer readFromNBT(CompoundNBT nbt, String key)
            {
                return nbt.getInt(key);
            }
            
            @Override
            public void writeToNBT(CompoundNBT nbt, String key, Integer value)
            {
                nbt.putInt(key, value);
            }
        }.setRegistryName(Visibilis.MOD_ID, "integer"));
        
        registry.register(new DynamicDataType<Float>((length) -> new Float[length], 1.0F)
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
            public Float readFromNBT(CompoundNBT nbt, String key)
            {
                return nbt.getFloat(key);
            }
            
            @Override
            public void writeToNBT(CompoundNBT nbt, String key, Float value)
            {
                nbt.putFloat(key, value);
            }
        }.setRegistryName(Visibilis.MOD_ID, "float"));
        
        registry.register(new DynamicDataType<Double>((length) -> new Double[length], 1.0D)
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
            public Double readFromNBT(CompoundNBT nbt, String key)
            {
                return nbt.getDouble(key);
            }
            
            @Override
            public void writeToNBT(CompoundNBT nbt, String key, Double value)
            {
                nbt.putDouble(key, value);
            }
        }.setRegistryName(Visibilis.MOD_ID, "double"));
        
        registry.register(new DataType<>((length) -> new Number[length]).setDefaultValue(0).setRegistryName(Visibilis.MOD_ID, "number"));
        
        registry.register(new EnumDataType<Boolean>((length) -> new Boolean[length])
        {
            @Override
            public boolean equals(Boolean obj1, Boolean obj2)
            {
                return obj1.booleanValue() == obj2.booleanValue();
            }
            
            @Override
            public Boolean readFromNBT(CompoundNBT nbt, String key)
            {
                return nbt.getBoolean(key);
            }
            
            @Override
            public void writeToNBT(CompoundNBT nbt, String key, Boolean value)
            {
                nbt.putBoolean(key, value);
            }
        }.addEnum(false).addEnum(true).setRegistryName(Visibilis.MOD_ID, "boolean"));
        
        registry.register(new DynamicDataType<String>((length) -> new String[length], "text")
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
            public String readFromNBT(CompoundNBT nbt, String key)
            {
                return nbt.getString(key);
            }
            
            @Override
            public void writeToNBT(CompoundNBT nbt, String key, String value)
            {
                nbt.putString(key, value);
            }
        }.setRegistryName(Visibilis.MOD_ID, "string"));
        
        registry.register(new DataType<>((length) -> new Vec3d[length]).setRegistryName(Visibilis.MOD_ID, "vector3d"));
        registry.register(new DataType<>((length) -> new PlayerEntity[length]).setRegistryName(Visibilis.MOD_ID, "player"));
        registry.register(new DataType<BlockPos>((length) -> new BlockPos[length])
        {
            @Override
            public boolean isSerializable()
            {
                return true;
            }
            
            @Override
            public BlockPos readFromNBT(CompoundNBT nbt, String key)
            {
                return new BlockPos(nbt.getInt(key + "_x"), nbt.getInt(key + "_y"), nbt.getInt(key + "_z"));
            }
            
            @Override
            public void writeToNBT(CompoundNBT nbt, String key, BlockPos value)
            {
                nbt.putInt(key + "_x", value.getX());
                nbt.putInt(key + "_y", value.getY());
                nbt.putInt(key + "_z", value.getZ());
            }
        }.setRegistryName(Visibilis.MOD_ID, "block_pos"));
        registry.register(new DataType<>((length) -> new World[length]).setRegistryName(Visibilis.MOD_ID, "world"));
    }
    
    // Called from FMLCommonSetupEvent
    public static void addConverters()
    {
        VDataTypes.OBJECT.registerGenericConverter(VDataTypes.INTEGER);
        VDataTypes.OBJECT.registerGenericConverter(VDataTypes.FLOAT);
        VDataTypes.OBJECT.registerGenericConverter(VDataTypes.DOUBLE);
        VDataTypes.OBJECT.registerGenericConverter(VDataTypes.NUMBER);
        VDataTypes.OBJECT.registerGenericConverter(VDataTypes.BOOLEAN);
        VDataTypes.OBJECT.registerGenericConverter(VDataTypes.STRING);
        VDataTypes.OBJECT.registerGenericConverter(VDataTypes.VECTOR3D);
        VDataTypes.OBJECT.registerGenericConverter(VDataTypes.PLAYER);
        VDataTypes.OBJECT.registerGenericConverter(VDataTypes.BLOCK_POS);
        VDataTypes.OBJECT.registerGenericConverter(VDataTypes.WORLD);
        
        VDataTypes.FLOAT.registerConverter(VDataTypes.INTEGER, (n) -> n.floatValue());
        VDataTypes.DOUBLE.registerConverter(VDataTypes.INTEGER, (n) -> n.doubleValue());
        VDataTypes.DOUBLE.registerConverter(VDataTypes.FLOAT, (n) -> n.doubleValue());
        
        VDataTypes.NUMBER.registerGenericConverter(VDataTypes.INTEGER);
        VDataTypes.NUMBER.registerGenericConverter(VDataTypes.FLOAT);
        VDataTypes.NUMBER.registerGenericConverter(VDataTypes.DOUBLE);
        
        VDataTypes.INTEGER.registerConverter(VDataTypes.NUMBER, (n) -> n.intValue());
        VDataTypes.FLOAT.registerConverter(VDataTypes.NUMBER, (n) -> n.floatValue());
        VDataTypes.DOUBLE.registerConverter(VDataTypes.NUMBER, (n) -> n.doubleValue());
        
        VDataTypes.STRING.registerConverter(VDataTypes.INTEGER, new ObjToStringConverter<>());
        VDataTypes.STRING.registerConverter(VDataTypes.FLOAT, new ObjToStringConverter<>());
        VDataTypes.STRING.registerConverter(VDataTypes.DOUBLE, new ObjToStringConverter<>());
        VDataTypes.STRING.registerConverter(VDataTypes.NUMBER, new ObjToStringConverter<>());
        VDataTypes.STRING.registerConverter(VDataTypes.BOOLEAN, new ObjToStringConverter<>());
        VDataTypes.STRING.registerConverter(VDataTypes.VECTOR3D, new ObjToStringConverter<>());
        VDataTypes.STRING.registerConverter(VDataTypes.PLAYER, new ObjToStringConverter<>());
        VDataTypes.STRING.registerConverter(VDataTypes.BLOCK_POS, new ObjToStringConverter<>());
        
        VDataTypes.BLOCK_POS.registerConverter(VDataTypes.VECTOR3D, BlockPos::new);
        VDataTypes.VECTOR3D.registerConverter(VDataTypes.BLOCK_POS, Vec3d::new);
    }
}
