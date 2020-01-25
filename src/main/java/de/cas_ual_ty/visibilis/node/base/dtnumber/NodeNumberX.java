package de.cas_ual_ty.visibilis.node.base.dtnumber;

import de.cas_ual_ty.visibilis.datatype.DataType;
import de.cas_ual_ty.visibilis.node.NodeType;
import de.cas_ual_ty.visibilis.node.base.generic.NodeGenericX;
import de.cas_ual_ty.visibilis.registries.VDataTypes;
import de.cas_ual_ty.visibilis.util.VNumberHelper;

public abstract class NodeNumberX extends NodeGenericX<Number>
{
    public NodeNumberX(NodeType<?> type)
    {
        super(type);
    }
    
    @Override
    public DataType<Number> getDataType()
    {
        return VDataTypes.NUMBER;
    }
    
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
