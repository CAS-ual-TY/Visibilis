package de.cas_ual_ty.visibilis.node.exec;

import de.cas_ual_ty.visibilis.node.NodeType;
import de.cas_ual_ty.visibilis.node.base.ExpandableNode;
import de.cas_ual_ty.visibilis.node.field.Input;
import de.cas_ual_ty.visibilis.node.field.Output;
import de.cas_ual_ty.visibilis.print.provider.DataProvider;
import de.cas_ual_ty.visibilis.registries.VDataTypes;
import de.cas_ual_ty.visibilis.util.VUtility;

public class ThenNode extends ExpandableNode
{
    public ThenNode(NodeType<?> type)
    {
        super(type);
        this.addOutput(new Output<>(this, VDataTypes.EXEC, "out1"));
        this.addOutput(new Output<>(this, VDataTypes.EXEC, "out1"));
        this.addInput(new Input<>(this, VDataTypes.EXEC, "in1"));
    }
    
    @Override
    public void expand()
    {
        this.addOutput(new Output<>(this, VDataTypes.EXEC, "out1"));
    }
    
    @Override
    public void shrink()
    {
        this.removeOutput(this.getOutputAmt() - 1);
    }
    
    @Override
    public boolean doCalculate(DataProvider context)
    {
        return true;
    }
    
    @Override
    public <O> O getOutputValue(Output<O> out)
    {
        return null;
    }
    
    @Override
    public Output<Object> getOutExec(int index)
    {
        return index < this.getOutputAmt() ? VUtility.cast(this.getOutput(index)) : null;
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
}
