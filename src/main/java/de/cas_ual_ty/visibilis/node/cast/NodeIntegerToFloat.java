package de.cas_ual_ty.visibilis.node.cast;

import de.cas_ual_ty.visibilis.datatype.DataType;
import de.cas_ual_ty.visibilis.node.Input;
import de.cas_ual_ty.visibilis.node.Node;
import de.cas_ual_ty.visibilis.node.Output;

public class NodeIntegerToFloat extends Node
{
    public final Output<Integer> out1;
    public final Input<Float> in1;
    
    public float value;
    
    public NodeIntegerToFloat(int outputAmt, int inputAmt)
    {
        super(outputAmt, inputAmt);
        this.out1 = new Output<Integer>(0, this, DataType.INTEGER, "integer");
        this.in1 = new Input<Float>(0, this, DataType.FLOAT, "float");
    }
    
    @Override
    public boolean doCalculate()
    {
        this.value = this.in1.getValue();
        return true;
    }
    
    @Override
    public <B> B getOutputValue(int index)
    {
        if (index == this.out1.id)
        {
            return (B) (Float) this.value;
        }
        
        return null;
    }
    
    @Override
    public String getID()
    {
        return "integer_to_float";
    }
}