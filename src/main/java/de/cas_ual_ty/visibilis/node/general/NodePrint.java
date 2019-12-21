package de.cas_ual_ty.visibilis.node.general;

import de.cas_ual_ty.visibilis.datatype.DataType;
import de.cas_ual_ty.visibilis.node.ExecProvider;
import de.cas_ual_ty.visibilis.node.INodeExec;
import de.cas_ual_ty.visibilis.node.base.NodeX;
import de.cas_ual_ty.visibilis.node.field.Input;
import de.cas_ual_ty.visibilis.node.field.Output;
import net.minecraft.util.text.StringTextComponent;

public class NodePrint extends NodeX implements INodeExec
{
    public Output out1;
    public Input in1;
    
    public String[] values;
    
    public NodePrint()
    {
        super();
        this.addOutput(this.out1 = new Output(this, DataType.EXEC, "out1"));
        this.addInput(this.in1 = new Input(this, DataType.EXEC, "in1"));
        this.addInput(this.createDynamicInput());
    }
    
    @Override
    public Output getOutExec(int index)
    {
        return index == 0 ? this.out1 : null;
    }
    
    @Override
    public boolean hasAllRequiredInputs(ExecProvider provider)
    {
        return true;
    }
    
    @Override
    public boolean doCalculate(ExecProvider provider)
    {
        for(int i = 1; i < this.getInputAmt(); ++i)
        {
            provider.getCommandSender().sendFeedback(new StringTextComponent(this.getInput(i).getValue().toString()), false);
        }
        
        return true;
    }
    
    @Override
    public Input createDynamicInput()
    {
        return new Input<String>(this, DataType.STRING, "in2");
    }
    
    @Override
    public <B> B getOutputValue(int index)
    {
        return index > 0 && index <= this.values.length ? (B) this.values[index - 1] : null;
    }
    
    @Override
    public String getID()
    {
        return "print";
    }
}
