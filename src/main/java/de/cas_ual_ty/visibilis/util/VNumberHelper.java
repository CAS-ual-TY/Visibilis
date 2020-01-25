package de.cas_ual_ty.visibilis.util;

import java.util.function.BiFunction;
import java.util.function.Function;

import de.cas_ual_ty.visibilis.datatype.DataType;
import de.cas_ual_ty.visibilis.registries.VDataTypes;

public class VNumberHelper
{
    public static DataType<? extends Number> getDataTypeFor(Number n)
    {
        if(n instanceof Double)
        {
            return VDataTypes.DOUBLE;
        }
        else if(n instanceof Float)
        {
            return VDataTypes.FLOAT;
        }
        else
        {
            return VDataTypes.INTEGER;
        }
    }
    
    public static DataType<? extends Number> getDataTypeFor(Number n1, Number n2)
    {
        if(n1 instanceof Double || n2 instanceof Double)
        {
            return VDataTypes.DOUBLE;
        }
        else if(n1 instanceof Float || n2 instanceof Float)
        {
            return VDataTypes.FLOAT;
        }
        else
        {
            return VDataTypes.INTEGER;
        }
    }
    
    public static DataType<? extends Number> getDataTypeFor(Number[] ns)
    {
        DataType<? extends Number> t = VDataTypes.INTEGER;
        
        for(Number n : ns)
        {
            if(n instanceof Double)
            {
                return VDataTypes.DOUBLE;
            }
            else if(n instanceof Float && t != VDataTypes.FLOAT)
            {
                t = VDataTypes.FLOAT;
            }
        }
        
        return t;
    }
    
    public static class NumberFunctionP<T> implements Function<Number, T>
    {
        private final Function<Integer, T> functionInteger;
        private final Function<Float, T> functionFloat;
        private final Function<Double, T> functionDouble;
        
        public NumberFunctionP(Function<Integer, T> functionInteger, Function<Float, T> functionFloat, Function<Double, T> functionDouble)
        {
            super();
            this.functionInteger = functionInteger;
            this.functionFloat = functionFloat;
            this.functionDouble = functionDouble;
        }
        
        @Override
        public T apply(Number n)
        {
            DataType<? extends Number> t = VNumberHelper.getDataTypeFor(n);
            
            if(t == VDataTypes.DOUBLE)
            {
                return this.functionDouble.apply(n.doubleValue());
            }
            else if(t == VDataTypes.FLOAT)
            {
                return this.functionFloat.apply(n.floatValue());
            }
            else
            {
                return this.functionInteger.apply(n.intValue());
            }
        }
    }
    
    public static class NumberFunctionX<T> implements Function<Number[], T>
    {
        private final Function<Integer[], T> functionInteger;
        private final Function<Float[], T> functionFloat;
        private final Function<Double[], T> functionDouble;
        
        public NumberFunctionX(Function<Integer[], T> functionInteger, Function<Float[], T> functionFloat, Function<Double[], T> functionDouble)
        {
            super();
            this.functionInteger = functionInteger;
            this.functionFloat = functionFloat;
            this.functionDouble = functionDouble;
        }
        
        @Override
        public T apply(Number[] ns)
        {
            DataType<? extends Number> t = VNumberHelper.getDataTypeFor(ns);
            
            if(t == VDataTypes.DOUBLE)
            {
                return this.functionDouble.apply((Double[])ns);
            }
            else if(t == VDataTypes.FLOAT)
            {
                return this.functionFloat.apply((Float[])ns);
            }
            else
            {
                return this.functionInteger.apply((Integer[])ns);
            }
        }
    }
    
    public static class NumberFunctionP2<T> implements BiFunction<Number, Number, T>
    {
        private final BiFunction<Integer, Integer, T> functionInteger;
        private final BiFunction<Float, Float, T> functionFloat;
        private final BiFunction<Double, Double, T> functionDouble;
        
        public NumberFunctionP2(BiFunction<Integer, Integer, T> functionInteger, BiFunction<Float, Float, T> functionFloat, BiFunction<Double, Double, T> functionDouble)
        {
            super();
            this.functionInteger = functionInteger;
            this.functionFloat = functionFloat;
            this.functionDouble = functionDouble;
        }
        
        @Override
        public T apply(Number n1, Number n2)
        {
            DataType<? extends Number> t = VNumberHelper.getDataTypeFor(n1, n2);
            
            if(t == VDataTypes.DOUBLE)
            {
                return this.functionDouble.apply(n1.doubleValue(), n2.doubleValue());
            }
            else if(t == VDataTypes.FLOAT)
            {
                return this.functionFloat.apply(n1.floatValue(), n2.floatValue());
            }
            else
            {
                return this.functionInteger.apply(n1.intValue(), n2.intValue());
            }
        }
    }
}
