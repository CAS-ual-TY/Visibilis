package de.cas_ual_ty.visibilis.node.general;

import de.cas_ual_ty.visibilis.datatype.DataType;
import de.cas_ual_ty.visibilis.node.ExecProvider;
import de.cas_ual_ty.visibilis.node.Input;
import de.cas_ual_ty.visibilis.node.Node;
import de.cas_ual_ty.visibilis.node.Output;
import net.minecraft.nbt.NBTTagCompound;

public abstract class NodeNumber2Xto1 extends Node
{
    public static final String KEY_EXPANSION = "expansion_status";
    
    public final Output<Number> out1;
    
    public int expansion;
    public Number value;
    
    public NodeNumber2Xto1(int outputAmt, int inputAmt)
    {
        super(outputAmt, inputAmt);
        this.out1 = new Output<Number>(this, DataType.NUMBER, "out1");
        new Input<Number>(this, DataType.NUMBER, "in");
        new Input<Number>(this, DataType.NUMBER, "in");
        
        this.expansion = 0;
    }
    
    public NodeNumber2Xto1()
    {
        this(1, 2);
    }
    
    @Override
    public boolean doCalculate(ExecProvider provider)
    {
        Number[] inputs = new Number[this.getInputAmt()];
        
        for (int i = 0; i < inputs.length; ++i)
        {
            inputs[i] = (Number) this.getInput(i).getValue();
        }
        
        if (!this.canCalculate(inputs))
        {
            return false;
        }
        
        this.value = this.calculate(inputs);
        
        return true;
    }
    
    /**
     * Can this node calculate or are there going to be errors (example: 1 / 0)?
     * 
     * @param inputs
     *            All inputs
     * @return <b>true</b> if this node can calculate.
     */
    protected boolean canCalculate(Number[] inputs)
    {
        return true;
    }
    
    /**
     * Calculate the result using the 2 input numbers.
     * 
     * @param inputs
     *            All inputs
     * @return The result.
     */
    protected abstract Number calculate(Number[] inputs);
    
    @Override
    public <B> B getOutputValue(int index)
    {
        if (index == this.out1.getId())
        {
            return (B) this.value;
        }
        
        return null;
    }
    
    @Override
    public float[] getColor()
    {
        return DataType.NUMBER.getColor();
    }
    
    @Override
    public float[] getTextColor()
    {
        return DataType.NUMBER.getTextColor();
    }
    
    @Override
    public boolean canExpand()
    {
        return true;
    }
    
    @Override
    public boolean canShrink()
    {
        return this.expansion > 0;
    }
    
    @Override
    public void expand()
    {
        new Input<Number>(this, DataType.NUMBER, "in");
        ++this.expansion;
    }
    
    @Override
    public void shrink()
    {
        this.removeInput((Input) this.getInput(this.getInputAmt() - 1));
        --this.expansion;
    }
    
    @Override
    public void readNodeFromNBT(NBTTagCompound nbt)
    {
        int expansion = nbt.getInteger(NodeNumber2Xto1.KEY_EXPANSION);
        
        for (int i = 0; i < expansion; ++i)
        {
            this.expand();
        }
        
        super.readNodeFromNBT(nbt);
    }
    
    @Override
    public void writeNodeToNBT(NBTTagCompound nbt)
    {
        nbt.setInteger(NodeNumber2Xto1.KEY_EXPANSION, this.expansion);
        
        super.writeNodeToNBT(nbt);
    }
}
