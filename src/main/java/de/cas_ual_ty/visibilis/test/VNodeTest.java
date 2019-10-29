package de.cas_ual_ty.visibilis.test;

import de.cas_ual_ty.visibilis.Visibilis;
import de.cas_ual_ty.visibilis.datatype.DataType;
import de.cas_ual_ty.visibilis.node.ExecProvider;
import de.cas_ual_ty.visibilis.node.Input;
import de.cas_ual_ty.visibilis.node.NodeExec;
import de.cas_ual_ty.visibilis.node.Output;

public class VNodeTest extends NodeExec
{
    public Input in0;
    public Input<Boolean> in1;
    public Input<Number> in2;
    
    public VNodeTest()
    {
        super(0, 3);
        this.in0 = new Input(this, DataType.EXEC, "test");
        this.in1 = new Input<Boolean>(this, DataType.BOOLEAN, "test");
        this.in2 = new Input<Number>(this, DataType.NUMBER, "test");
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
        
        this.debugMessage(this.in1);
        this.debugMessage(this.in2);
        
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
        return "test";
    }
    
    @Override
    public String getUnlocalizedName()
    {
        return "test";
    }
}
