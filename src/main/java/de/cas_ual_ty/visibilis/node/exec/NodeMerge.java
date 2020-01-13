package de.cas_ual_ty.visibilis.node.exec;

import de.cas_ual_ty.visibilis.datatype.DataType;
import de.cas_ual_ty.visibilis.node.INodeExec;
import de.cas_ual_ty.visibilis.node.base.generic.NodeGenericX;
import de.cas_ual_ty.visibilis.node.field.Output;

public class NodeMerge extends NodeGenericX<Object> implements INodeExec
{
    public NodeMerge()
    {
        super();
    }
    
    @Override
    public Output<Object> getOutExec(int index)
    {
        return index == 0 ? this.out1O : null;
    }
    
    @Override
    public DataType<Object> getDataType()
    {
        return DataType.EXEC;
    }
    
    @Override
    protected Object calculate(Object[] inputs)
    {
        return null;
    }
    
    @Override
    public String getID()
    {
        return "merge";
    }
}
