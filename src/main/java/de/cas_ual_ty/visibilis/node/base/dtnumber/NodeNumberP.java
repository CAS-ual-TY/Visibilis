package de.cas_ual_ty.visibilis.node.base.dtnumber;

import de.cas_ual_ty.visibilis.datatype.DataType;
import de.cas_ual_ty.visibilis.node.NodeType;
import de.cas_ual_ty.visibilis.node.base.generic.NodeGenericP;
import de.cas_ual_ty.visibilis.registries.VDataTypes;
import de.cas_ual_ty.visibilis.util.VNumberHelper;

public abstract class NodeNumberP extends NodeGenericP<Number>
{
    public NodeNumberP(NodeType<?> type)
    {
        super(type);
    }
    
    @Override
    public DataType<Number> getDataType()
    {
        return VDataTypes.NUMBER;
    }
    
    @Override
    protected boolean canCalculate(Number input)
    {
        return super.canCalculate(input);
    }
    
    @Override
    protected Number calculate(Number input)
    {
        DataType<? extends Number> t = VNumberHelper.getDataTypeFor(input);
        
        if(t == VDataTypes.DOUBLE)
        {
            return this.calculateDouble(input.doubleValue());
        }
        else if(t == VDataTypes.FLOAT)
        {
            return this.calculateFloat(input.floatValue());
        }
        else
        {
            return this.calculateInteger(input.intValue());
        }
    }
    
    protected abstract Integer calculateInteger(Integer input);
    
    protected abstract Float calculateFloat(Float input);
    
    protected abstract Double calculateDouble(Double input);
}
