package de.cas_ual_ty.visibilis.node.base.dtnumber;

import de.cas_ual_ty.visibilis.datatype.DataType;
import de.cas_ual_ty.visibilis.node.NodeType;
import de.cas_ual_ty.visibilis.node.base.generic.NodeGenericXP2;
import de.cas_ual_ty.visibilis.registries.VDataTypes;
import de.cas_ual_ty.visibilis.util.VNumberHelper;

public abstract class NodeNumberXP2 extends NodeGenericXP2<Number>
{
    public NodeNumberXP2(NodeType<?> type)
    {
        super(type);
    }
    
    @Override
    public DataType<Number> getDataType()
    {
        return VDataTypes.NUMBER;
    }
    
    @Override
    protected boolean canCalculate(Number input, Number in2)
    {
        return super.canCalculate(input, in2);
    }
    
    @Override
    protected Number calculate(Number input, Number in2)
    {
        DataType<? extends Number> t = VNumberHelper.getDataTypeFor(input, in2);
        
        if(t == VDataTypes.DOUBLE)
        {
            return this.calculateDouble(input.doubleValue(), in2.doubleValue());
        }
        else if(t == VDataTypes.FLOAT)
        {
            return this.calculateFloat(input.floatValue(), in2.floatValue());
        }
        else
        {
            return this.calculateInteger(input.intValue(), in2.intValue());
        }
    }
    
    protected abstract Integer calculateInteger(Integer input, Integer in2);
    
    protected abstract Float calculateFloat(Float input, Float in2);
    
    protected abstract Double calculateDouble(Double input, Double in2);
    
    @Override
    protected boolean canCalculate(Number[] inputs)
    {
        return super.canCalculate(inputs);
    }
    
    @Override
    protected Number calculate(Number[] inputs)
    {
        DataType<? extends Number> t = VNumberHelper.getDataTypeFor(inputs);
        
        if(t == VDataTypes.DOUBLE)
        {
            return this.calculateDouble((Double[])inputs);
        }
        else if(t == VDataTypes.FLOAT)
        {
            return this.calculateFloat((Float[])inputs);
        }
        else
        {
            return this.calculateInteger((Integer[])inputs);
        }
    }
    
    protected abstract Integer calculateInteger(Integer[] inputs);
    
    protected abstract Float calculateFloat(Float[] inputs);
    
    protected abstract Double calculateDouble(Double[] inputs);
}
