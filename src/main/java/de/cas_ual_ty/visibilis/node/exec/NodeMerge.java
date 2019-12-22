package de.cas_ual_ty.visibilis.node.exec;

import de.cas_ual_ty.visibilis.datatype.DataType;
import de.cas_ual_ty.visibilis.node.INodeExec;
import de.cas_ual_ty.visibilis.node.base.NodeGenericX;
import de.cas_ual_ty.visibilis.node.field.Output;

public class NodeMerge extends NodeGenericX implements INodeExec
{
    public NodeMerge()
    {
        super();
    }
    
    @Override
    public Output getOutExec(int index)
    {
        return index == 0 ? this.out1 : null;
    }
    
    @Override
    public DataType getDataType()
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
