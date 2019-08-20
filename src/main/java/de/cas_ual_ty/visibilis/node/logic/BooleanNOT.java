package de.cas_ual_ty.visibilis.node.logic;

import de.cas_ual_ty.visibilis.datatype.VDataType;
import de.cas_ual_ty.visibilis.node.Input;
import de.cas_ual_ty.visibilis.node.Node;
import de.cas_ual_ty.visibilis.node.Output;

public class BooleanNOT extends Node
{
    public final Output<Boolean> out1;
    public final Input<Boolean> in1;
    
    public boolean value;
    
    public BooleanNOT(int outputAmt, int inputAmt)
    {
        super(outputAmt, inputAmt);
        this.out1 = new Output<Boolean>(0, this, VDataType.BOOLEAN, "boolean");
        this.in1 = new Input<Boolean>(0, this, VDataType.BOOLEAN, "boolean");
    }
    
    public BooleanNOT()
    {
        this(1, 1);
    }
    
    @Override
    public boolean doCalculate()
    {
        this.value = !this.in1.getValue();
        
        return true;
    }
    
    @Override
    public <B> B getOutputValue(int index)
    {
        if (index == this.out1.id)
        {
            return (B) (Boolean) this.value;
        }
        
        return null;
    }
    
    @Override
    public String getID()
    {
        return "logic_not";
    }
}
