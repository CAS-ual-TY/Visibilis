package de.cas_ual_ty.visibilis.node.function;

import de.cas_ual_ty.visibilis.datatype.DataType;
import de.cas_ual_ty.visibilis.node.NodeType;
import de.cas_ual_ty.visibilis.node.field.Input;
import de.cas_ual_ty.visibilis.node.field.Output;
import de.cas_ual_ty.visibilis.print.provider.DataProvider;
import de.cas_ual_ty.visibilis.util.VUtility;

public class FunctionEndNode extends FunctionFieldsNode
{
    public FunctionEndNode(NodeType<?> type)
    {
        super(type);
    }
    
    @Override
    public void addField(DataType<?> type)
    {
        this.addInput(new Input<>(this, type, (f) -> type.getName()));
    }
    
    @Override
    public void removeField(int index)
    {
        this.removeInput(index);
    }
    
    @Override
    public int getSize()
    {
        return this.getInputAmt();
    }
    
    @Override
    public boolean doCalculate(DataProvider context)
    {
        return false;
    }
    
    @Override
    public <O> O getOutputValue(Output<O> out)
    {
        return VUtility.cast(this.getInputValue(this.getInput(out.getId())));
    }
}
