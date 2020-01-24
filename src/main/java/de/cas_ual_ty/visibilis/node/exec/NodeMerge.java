package de.cas_ual_ty.visibilis.node.exec;

import de.cas_ual_ty.visibilis.datatype.DataType;
import de.cas_ual_ty.visibilis.node.NodeType;
import de.cas_ual_ty.visibilis.node.base.generic.NodeGenericX;
import de.cas_ual_ty.visibilis.node.field.Output;
import de.cas_ual_ty.visibilis.registries.VDataTypes;

public class NodeMerge extends NodeGenericX<Object>
{
    public NodeMerge(NodeType<?> type)
    {
        super(type);
    }
    
    @Override
    public Output<Object> getOutExec(int index)
    {
        return index == 0 ? this.out1O : null;
    }
    
    @Override
    public DataType<Object> getDataType()
    {
        return VDataTypes.EXEC;
    }
    
    @Override
    protected Object calculate(Object[] inputs)
    {
        return null;
    }
    
    @Override
    public float[] getColor()
    {
        return VDataTypes.EXEC.getColor();
    }
    
    @Override
    public float[] getTextColor()
    {
        return VDataTypes.EXEC.getTextColor();
    }
    
    @Override
    public String getID()
    {
        return "merge";
    }
}
