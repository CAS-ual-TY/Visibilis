package de.cas_ual_ty.visibilis.node.function;

import de.cas_ual_ty.visibilis.datatype.DataType;
import de.cas_ual_ty.visibilis.node.NodeType;
import de.cas_ual_ty.visibilis.node.field.Input;
import de.cas_ual_ty.visibilis.node.field.Output;
import de.cas_ual_ty.visibilis.print.provider.DataProvider;
import de.cas_ual_ty.visibilis.util.VUtility;

public class FunctionEndNode extends FunctionFieldsNode
{
    public int execInput;
    
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
    public DataType<?> getField(int index)
    {
        return this.getInput(index).getDataType();
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
        this.execInput = -1;
        
        if(context.getTracker().execNodes.getLast() == this)
        {
            this.execInput = context.getTracker().execInputs.getLast().getId();
        }
        
        return true;
    }
    
    @Override
    public <O> O getOutputValue(Output<O> out)
    {
        return VUtility.cast(this.getInputValue(this.getInput(out.getId())));
    }
    
    @Override
    public Output<Object> getOutExec(int index)
    {
        return null;
    }
}
