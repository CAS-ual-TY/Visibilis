package de.cas_ual_ty.visibilis.node.general;

import de.cas_ual_ty.visibilis.datatype.DataType;
import de.cas_ual_ty.visibilis.node.INodeExec;
import de.cas_ual_ty.visibilis.node.base.string.NodeStringXP2;
import de.cas_ual_ty.visibilis.node.field.Input;
import de.cas_ual_ty.visibilis.node.field.Output;

public class NodePrint extends NodeStringXP2 implements INodeExec
{
    public Output out1;
    public Input in1;
    
    public String[] values;
    
    public NodePrint()
    {
        super();
        this.out1 = new Output(this, DataType.EXEC, "out1");
        this.in1 = new Input(this, DataType.EXEC, "in2");
    }
    
    @Override
    public void createBaseFields()
    {
        this.addOutput(this.out1);
        this.addInput(this.in1);
        super.createBaseFields();
    }
    
    @Override
    public Output getOutExec(int index)
    {
        return index == 0 ? this.out1 : null;
    }
    
    @Override
    protected String calculate(String input, String in2)
    {
        return input + in2;
    }
    
    @Override
    protected String calculate(String[] inputs)
    {
        String s = "";
        
        for (String in : inputs)
        {
            s += in;
        }
        
        return s;
    }
    
    @Override
    public String getID()
    {
        return "print";
    }
}
