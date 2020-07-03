package de.cas_ual_ty.visibilis.node.function;

import de.cas_ual_ty.visibilis.datatype.DataType;
import de.cas_ual_ty.visibilis.node.Node;
import de.cas_ual_ty.visibilis.node.NodeType;

public abstract class FunctionFieldsNode extends Node
{
    protected FunctionPrint parentPrint;
    
    public FunctionFieldsNode(NodeType<?> type)
    {
        super(type);
        this.parentPrint = null;
    }
    
    public FunctionFieldsNode linkParentPrint(FunctionPrint parentPrint)
    {
        this.parentPrint = parentPrint;
        return this;
    }
    
    public abstract void addField(DataType<?> type);
    
    public abstract void removeField(int index);
    
    public abstract int getSize();
}
