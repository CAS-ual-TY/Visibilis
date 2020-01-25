package de.cas_ual_ty.visibilis.node.base.dtnumber;

import de.cas_ual_ty.visibilis.datatype.DataType;
import de.cas_ual_ty.visibilis.node.NodeType;
import de.cas_ual_ty.visibilis.node.base.generic.NodeGenericCompare;
import de.cas_ual_ty.visibilis.registries.VDataTypes;
import de.cas_ual_ty.visibilis.util.VNumberHelper;

public abstract class NodeNumberCompare extends NodeGenericCompare<Number>
{
    public NodeNumberCompare(NodeType<?> type)
    {
        super(type);
    }
    
    @Override
    public DataType<Number> getDataType()
    {
        return VDataTypes.NUMBER;
    }
    
    @Override
    protected Boolean compare(Number input, Number in2)
    {
        DataType<? extends Number> t = VNumberHelper.getDataTypeFor(input, in2);
        
        if(t == VDataTypes.DOUBLE)
        {
            return this.compareDouble(input.doubleValue(), in2.doubleValue());
        }
        else if(t == VDataTypes.FLOAT)
        {
            return this.compareFloat(input.floatValue(), in2.floatValue());
        }
        else
        {
            return this.compareInteger(input.intValue(), in2.intValue());
        }
    }
    
    protected abstract boolean compareInteger(Integer input, Integer in2);
    
    protected abstract boolean compareFloat(Float input, Float in2);
    
    protected abstract boolean compareDouble(Double input, Double in2);
}
