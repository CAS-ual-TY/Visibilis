package de.cas_ual_ty.visibilis.node.function;

import de.cas_ual_ty.visibilis.datatype.DataType;
import de.cas_ual_ty.visibilis.node.Node;
import de.cas_ual_ty.visibilis.node.NodeType;
import de.cas_ual_ty.visibilis.node.field.Output;
import de.cas_ual_ty.visibilis.print.provider.DataProvider;
import net.minecraft.nbt.CompoundNBT;

public class FunctionNode extends Node
{
    public FunctionPrint functionPrint;
    
    public FunctionNode(NodeType<?> type)
    {
        super(type);
        this.functionPrint = new FunctionPrint(this);
    }
    
    @Override
    public boolean doCalculate(DataProvider context)
    {
        return true;
    }
    
    public void addOutput(DataType<?> type)
    {
        
    }
    
    public void addInput(DataType<?> type)
    {
        
    }
    
    @Override
    public <O> O getOutputValue(Output<O> out)
    {
        return this.functionPrint.getOutputValue(out.getId());
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
}
