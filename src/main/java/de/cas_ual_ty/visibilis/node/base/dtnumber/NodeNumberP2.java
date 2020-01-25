package de.cas_ual_ty.visibilis.node.base.dtnumber;

import de.cas_ual_ty.visibilis.datatype.DataType;
import de.cas_ual_ty.visibilis.node.NodeType;
import de.cas_ual_ty.visibilis.node.base.generic.NodeGenericP2;
import de.cas_ual_ty.visibilis.registries.VDataTypes;
import de.cas_ual_ty.visibilis.util.VNumberHelper;

public abstract class NodeNumberP2 extends NodeGenericP2<Number>
{
    public NodeNumberP2(NodeType<?> type)
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
}
