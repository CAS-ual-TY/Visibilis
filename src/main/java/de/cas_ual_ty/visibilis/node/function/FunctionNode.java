package de.cas_ual_ty.visibilis.node.function;

import de.cas_ual_ty.visibilis.datatype.DataType;
import de.cas_ual_ty.visibilis.node.Node;
import de.cas_ual_ty.visibilis.node.NodeType;
import de.cas_ual_ty.visibilis.node.field.Input;
import de.cas_ual_ty.visibilis.node.field.Output;
import de.cas_ual_ty.visibilis.print.provider.DataProvider;
import de.cas_ual_ty.visibilis.util.VUtility;
import net.minecraft.nbt.CompoundNBT;

public class FunctionNode extends Node
{
    public FunctionPrint functionPrint;
    
    public int execInput;
    
    public FunctionNode(NodeType<?> type)
    {
        super(type);
        this.functionPrint = new FunctionPrint(this);
    }
    
    @Override
    public boolean doCalculate(DataProvider context)
    {
        //        boolean ret;
        
        if(context.getTracker().execNodes.getLast() == this)
        {
            //            ExecTracker tracker = context.getTracker();
            //            context.newTracker(new ExecTracker());
            this.execInput = context.getTracker().execInputs.getLast().getId();
            return this.functionPrint.exec(context);
            //            context.newTracker(tracker);
        }
        else
        {
            return this.functionPrint.calculate(context);
        }
        
        //        return ret;
    }
    
    @Override
    public Output<Object> getOutExec(int index)
    {
        return index == 0 ? VUtility.cast(this.getOutput(this.functionPrint.nodeOutputs.execInput)) : null;
    }
    
    public <O> void addOutput(DataType<O> type)
    {
        this.addOutput(new Output<>(this, type, (f) -> type.getName()));
    }
    
    public void addInput(DataType<?> type)
    {
        this.addInput(new Input<>(this, type, (f) -> type.getName()));
    }
    
    @Override
    public <O> O getOutputValue(Output<O> out)
    {
        return this.functionPrint.getOutputValue(out.getId());
    }
    
    @Override
    public <I> I getInputValue(Input<I> in)
    {
        return super.getInputValue(in);
    }
    
    @Override
    public void readNodeFromNBT(CompoundNBT nbt0)
    {
        super.readNodeFromNBT(nbt0);
        this.functionPrint.readFromNBT(nbt0, true);
    }
    
    @Override
    public void writeNodeToNBT(CompoundNBT nbt0)
    {
        super.writeNodeToNBT(nbt0);
        this.functionPrint.writeToNBT(nbt0, true);
    }
    
    @Override
    public boolean isForcedDynamic()
    {
        return super.isForcedDynamic() || this.functionPrint.nodeOutputs.isForcedDynamic();
    }
}
