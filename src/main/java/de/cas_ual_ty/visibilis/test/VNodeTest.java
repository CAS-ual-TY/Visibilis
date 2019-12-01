package de.cas_ual_ty.visibilis.test;

import de.cas_ual_ty.visibilis.Visibilis;
import de.cas_ual_ty.visibilis.datatype.DataType;
import de.cas_ual_ty.visibilis.node.ExecProvider;
import de.cas_ual_ty.visibilis.node.INodeExec;
import de.cas_ual_ty.visibilis.node.Node;
import de.cas_ual_ty.visibilis.node.field.Input;
import de.cas_ual_ty.visibilis.node.field.Output;
import net.minecraft.util.text.StringTextComponent;

public class VNodeTest extends Node implements INodeExec
{
    public Output out1;
    public Input in1;
    public Input<Boolean> in2;
    public Input<Number> in3;
    
    public VNodeTest()
    {
        super();
        this.out1 = new Output(this, DataType.EXEC, "out1");
        this.in1 = new Input(this, DataType.EXEC, "in1");
        this.in2 = new Input<>(this, DataType.BOOLEAN, "in2");
        this.in3 = new Input<>(this, DataType.NUMBER, "in3");
    }
    
    @Override
    public Output getOutExec(int index)
    {
        return index == 0 ? this.out1 : null;
    }
    
    @Override
    public boolean hasAllRequiredInputs(ExecProvider provider)
    {
        // We dont need any inputs, technically
        return true;
    }
    
    @Override
    public boolean doCalculate(ExecProvider provider)
    {
        this.debugMessage(provider, this.in2);
        this.debugMessage(provider, this.in3);
        
        return true;
    }
    
    public void debugMessage(ExecProvider provider, Input in)
    {
        if (in.hasConnections() || in.hasDisplayValue())
        {
            provider.getCommandSender().sendFeedback(new StringTextComponent(in.dataType.valueToString(in.getValue())), true);
            Visibilis.debug(in.getId() + ": " + in.getValue());
        }
        else
        {
            Visibilis.debug(in.getId() + ": " + "404 NO CONNECTIONS");
        }
    }
    
    @Override
    public <B> B getOutputValue(int index)
    {
        return null;
    }
    
    @Override
    public String getID()
    {
        return "vtest";
    }
}
