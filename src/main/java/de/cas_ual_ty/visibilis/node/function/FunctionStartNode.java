package de.cas_ual_ty.visibilis.node.function;

import de.cas_ual_ty.visibilis.datatype.DataType;
import de.cas_ual_ty.visibilis.node.NodeType;
import de.cas_ual_ty.visibilis.node.field.Output;
import de.cas_ual_ty.visibilis.print.provider.DataProvider;

public class FunctionStartNode extends FunctionFieldsNode
{
    public FunctionStartNode(NodeType<?> type)
    {
        super(type);
    }
    
    @Override
    public void addField(DataType<?> type)
    {
        this.addOutput(new Output<>(this, type, (f) -> type.getName()));
    }
    
    @Override
    public DataType<?> getField(int index)
    {
        return this.getOutput(index).getDataType();
    }
    
    @Override
    public void removeField(int index)
    {
        this.removeOutput(index);
    }
    
    @Override
    public int getSize()
    {
        return this.getOutputAmt();
    }
    
    @Override
    public boolean doCalculate(DataProvider context)
    {
        return true;
    }
    
    @Override
    public <O> O getOutputValue(Output<O> out)
    {
        return this.parentPrint.getInputValue(out.getId());
    }
    
    @Override
    public boolean isForcedDynamic()
    {
        return super.isForcedDynamic() || this.parentPrint.functionNode.isForcedDynamic();
    }
}
