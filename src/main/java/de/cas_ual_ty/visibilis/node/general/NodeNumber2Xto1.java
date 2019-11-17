package de.cas_ual_ty.visibilis.node.general;

import de.cas_ual_ty.visibilis.datatype.DataType;
import de.cas_ual_ty.visibilis.node.ExecProvider;
import de.cas_ual_ty.visibilis.node.Input;
import de.cas_ual_ty.visibilis.node.Node;
import de.cas_ual_ty.visibilis.node.NodeAction;
import de.cas_ual_ty.visibilis.node.Output;
import net.minecraft.nbt.CompoundNBT;

public abstract class NodeNumber2Xto1 extends Node
{
    public static final String KEY_EXPANSION = "expansion_status";
    
    public final Output<Number> out1;
    
    public int expansion;
    public Number value;
    
    public NodeNumber2Xto1(int outputAmt, int inputAmt)
    {
        super(outputAmt, inputAmt);
        this.out1 = new Output<>(this, DataType.NUMBER, "out1");
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
    
    public boolean canExpand()
    {
        return true;
    }
    
    public boolean canShrink()
    {
        return this.expansion > 0;
    }
    
    public void expand()
    {
        new Input<Number>(this, DataType.NUMBER, "in");
        ++this.expansion;
    }
    
    public void shrink()
    {
        this.removeInput((Input) this.getInput(this.getInputAmt() - 1));
        --this.expansion;
    }
    
    @Override
    public NodeAction[] getActions()
    {
        if(canShrink() && canExpand())
        {
            return new NodeAction[] {
                            new NodeAction(this, NodeAction.LANG_EXPAND) {
                                @Override
                                public boolean clicked()
                                {
                                    NodeNumber2Xto1.this.expand();
                                    return true;
                                }},
                            new NodeAction(this, NodeAction.LANG_SHRINK) {
                                    @Override
                                    public boolean clicked()
                                    {
                                        NodeNumber2Xto1.this.shrink();
                                        return true;
                                    }}
            };
        }
        else if(canExpand())
        {
            return new NodeAction[] {
                            new NodeAction(this, NodeAction.LANG_EXPAND) {
                                @Override
                                public boolean clicked()
                                {
                                    NodeNumber2Xto1.this.expand();
                                    return true;
                                }}
            };
        }
        else if(canShrink())
        {
            return new NodeAction[] {
                            new NodeAction(this, NodeAction.LANG_SHRINK) {
                                @Override
                                public boolean clicked()
                                {
                                    NodeNumber2Xto1.this.shrink();
                                    return true;
                                }}
            };
        }
        else
        {
            return null;
        }
    }
    
    @Override
    public void readNodeFromNBT(CompoundNBT nbt)
    {
        super.readNodeFromNBT(nbt);
        int expansion = nbt.getInt(NodeNumber2Xto1.KEY_EXPANSION);
        
        for (int i = 0; i < expansion; ++i)
        {
            this.expand();
        }
    }
    
    @Override
    public void writeNodeToNBT(CompoundNBT nbt)
    {
        super.writeNodeToNBT(nbt);
        nbt.putInt(NodeNumber2Xto1.KEY_EXPANSION, this.expansion);
    }
}
