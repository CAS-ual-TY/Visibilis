package de.cas_ual_ty.visibilis.node.general;

import de.cas_ual_ty.visibilis.node.NodeType;
import de.cas_ual_ty.visibilis.node.base.ExpandableNode;
import de.cas_ual_ty.visibilis.node.field.Input;
import de.cas_ual_ty.visibilis.node.field.Output;
import de.cas_ual_ty.visibilis.print.provider.DataProvider;
import de.cas_ual_ty.visibilis.registries.VDataTypes;
import net.minecraft.util.text.StringTextComponent;

public class PrintOutNode extends ExpandableNode
{
    public Output<Object> out1Exec;
    public Input<Object> in1Exec;
    
    public String[] values;
    
    public PrintOutNode(NodeType<?> type)
    {
        super(type);
        this.addOutput(this.out1Exec = new Output<>(this, VDataTypes.EXEC, "out1"));
        this.addInput(this.in1Exec = new Input<>(this, VDataTypes.EXEC, "in1"));
        this.expand();
    }
    
    @Override
    public Output<Object> getOutExec(int index)
    {
        return index == 0 ? this.out1Exec : null;
    }
    
    @Override
    public boolean doCalculate(DataProvider context)
    {
        String s;
        
        for(int i = 1; i < this.getInputAmt(); ++i)
        {
            s = (String)this.getInput(i).getValue();
            
            context.sendFeedback(new StringTextComponent(s), true);
        }
        
        return true;
    }
    
    @Override
    public <O> O getOutputValue(Output<O> out)
    {
        return null;
    }
    
    @Override
    public void expand()
    {
        this.addInput(new Input<>(this, VDataTypes.STRING, "in2"));
    }
    
    @Override
    public void shrink()
    {
        this.removeInput(this.getInputAmt() - 1);
    }
    
    @Override
    public float[] getColor()
    {
        return VDataTypes.STRING.getColor();
    }
    
    @Override
    public float[] getTextColor()
    {
        return VDataTypes.STRING.getTextColor();
    }
}
