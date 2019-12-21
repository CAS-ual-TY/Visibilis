package de.cas_ual_ty.visibilis.node.compare;

import de.cas_ual_ty.visibilis.datatype.DataType;
import de.cas_ual_ty.visibilis.node.general.NodeCompare;

public class NodeNumberCompare extends NodeCompare<Number>
{
    public NodeNumberCompare()
    {
        super();
    }
    
    @Override
    public DataType getDataType()
    {
        return DataType.NUMBER;
    }
    
    @Override
    protected Boolean calculate(Number in1, Number in2)
    {
        return in1.floatValue() == in2.floatValue();
    }
    
    @Override
    public String getID()
    {
        return "Equals";
    }
}
