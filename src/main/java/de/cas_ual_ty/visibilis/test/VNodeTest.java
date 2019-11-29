package de.cas_ual_ty.visibilis.test;

import de.cas_ual_ty.visibilis.Visibilis;
import de.cas_ual_ty.visibilis.datatype.DataType;
import de.cas_ual_ty.visibilis.node.ExecProvider;
import de.cas_ual_ty.visibilis.node.INodeExec;
import de.cas_ual_ty.visibilis.node.Node;
import de.cas_ual_ty.visibilis.node.field.Input;
import de.cas_ual_ty.visibilis.node.field.Output;

public class VNodeTest extends Node implements INodeExec
{
    public Input in1;
    public Input<Boolean> in2;
    public Input<Number> in3;
    
    public VNodeTest()
    {
        super();
        this.in1 = new Input(this, DataType.EXEC, "in1");
        this.in2 = new Input<>(this, DataType.BOOLEAN, "in2");
        this.in3 = new Input<>(this, DataType.NUMBER, "in3");
    }
    
    @Override
    public Output getOutExec(int index)
    {
        return null;
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
        Visibilis.debug("## " + this.getClass().getSimpleName() + " START");
        
        this.debugMessage(this.in2);
        this.debugMessage(this.in3);
        
        Visibilis.debug("## " + this.getClass().getSimpleName() + " END");
        
        return true;
    }
    
    public void debugMessage(Input in)
    {
        if (in.hasConnections() || in.hasDisplayValue())
        {
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
