package de.cas_ual_ty.visibilis.node.logic;

import de.cas_ual_ty.visibilis.datatype.DataType;
import de.cas_ual_ty.visibilis.node.ExecProvider;
import de.cas_ual_ty.visibilis.node.Input;
import de.cas_ual_ty.visibilis.node.Node;
import de.cas_ual_ty.visibilis.node.Output;

public class NodeNOT extends Node
{
    public final Output<Boolean> out1;
    public final Input<Boolean> in1;
    
    public boolean value;
    
    public NodeNOT(int outputAmt, int inputAmt)
    {
        super(outputAmt, inputAmt);
        this.out1 = new Output<Boolean>(this, DataType.BOOLEAN, "out1");
        this.in1 = new Input<Boolean>(this, DataType.BOOLEAN, "in1");
    }
    
    public NodeNOT()
    {
        this(1, 1);
    }
    
    @Override
    public boolean doCalculate(ExecProvider provider)
    {
        this.value = !this.in1.getValue();
        
        return true;
    }
    
    @Override
    public <B> B getOutputValue(int index)
    {
        if (index == this.out1.getId())
        {
            return (B) (Boolean) this.value;
        }
        
        return null;
    }
    
    @Override
    public String getID()
    {
        return "not";
    }
    
    @Override
    public float[] getColor()
    {
        return DataType.BOOLEAN.getColor();
    }
}
