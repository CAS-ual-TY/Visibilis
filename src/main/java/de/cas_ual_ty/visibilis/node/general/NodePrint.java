package de.cas_ual_ty.visibilis.node.general;

import de.cas_ual_ty.visibilis.datatype.DataType;
import de.cas_ual_ty.visibilis.node.ExecProvider;
import de.cas_ual_ty.visibilis.node.INodeExec;
import de.cas_ual_ty.visibilis.node.base.NodeExpandable;
import de.cas_ual_ty.visibilis.node.field.Input;
import de.cas_ual_ty.visibilis.node.field.Output;
import net.minecraft.util.text.StringTextComponent;

public class NodePrint extends NodeExpandable implements INodeExec
{
    public Output<?> out1;
    public Input<?> in1;
    
    public String[] values;
    
    public NodePrint()
    {
        super();
        this.addOutput(this.out1 = new Output<>(this, DataType.EXEC, "out1"));
        this.addInput(this.in1 = new Input<>(this, DataType.EXEC, "in1"));
        this.expand();
    }
    
    @Override
    public Output<?> getOutExec(int index)
    {
        return index == 0 ? this.out1 : null;
    }
    
    @Override
    public boolean doCalculate(ExecProvider provider)
    {
        String s;
        
        for(int i = 1; i < this.getInputAmt(); ++i)
        {
            s = (String)this.getInput(i).getValue();
            
            provider.getCommandSender().sendFeedback(new StringTextComponent(s), true);
        }
        
        return true;
    }
    
    @Override
    public <B> B getOutputValue(int index)
    {
        return null;
    }
    
    @Override
    public void expand()
    {
        this.addInput(new Input<>(this, DataType.STRING, "in2"));
    }
    
    @Override
    public void shrink()
    {
        this.removeInput(this.getInputAmt() - 1);
    }
    
    @Override
    public float[] getColor()
    {
        return DataType.STRING.getColor();
    }
    
    @Override
    public float[] getTextColor()
    {
        return DataType.STRING.getTextColor();
    }
    
    @Override
    public String getID()
    {
        return "print";
    }
}
