package de.cas_ual_ty.visibilis.node.exec;

import de.cas_ual_ty.visibilis.datatype.DataType;
import de.cas_ual_ty.visibilis.node.NodeType;
import de.cas_ual_ty.visibilis.node.base.bigeneric.NodeBiGenericP2;
import de.cas_ual_ty.visibilis.node.field.Input;
import de.cas_ual_ty.visibilis.node.field.Output;
import de.cas_ual_ty.visibilis.print.provider.DataProvider;
import de.cas_ual_ty.visibilis.registries.VDataTypes;

public class NodeMultiEqualsBranch extends NodeBiGenericP2<Object, Object>
{
    protected int amount;
    
    public NodeMultiEqualsBranch(NodeType<?> type)
    {
        super(type);
    }
    
    @Override
    public void resetValues()
    {
        this.amount = 0;
        super.resetValues();
    }
    
    @Override
    public void createBaseFields()
    {
        this.addInput(this.in2 = new Input<>(this, this.getIn2DataType(), "in2"));
        this.countBaseFields();
        this.addOutput(new Output<>(this, VDataTypes.EXEC, "out2"), 0);
        this.addInput(new Input<>(this, VDataTypes.EXEC, "in3"), 0);
        this.expand();
    }
    
    @Override
    public DataType<Object> getInDataType()
    {
        return VDataTypes.OBJECT;
    }
    
    @Override
    public DataType<Object> getOutDataType()
    {
        return VDataTypes.EXEC;
    }
    
    @Override
    protected Object calculate(Object input, Object in2)
    {
        return (Boolean)(this.getInDataType().equals(input, in2));
    }
    
    @Override
    public boolean doCalculate(DataProvider context)
    {
        boolean flag = super.doCalculate(context);
        
        Boolean b;
        for(int j = 0; j < this.values.length; ++j)
        {
            b = (Boolean)this.values[j];
            if(b)
            {
                ++this.amount;
            }
        }
        
        return flag;
    }
    
    @Override
    public Output<Object> getOutExec(int index)
    {
        if(index >= this.amount)
        {
            return null;
        }
        
        int i = 0;
        
        Boolean b;
        for(int j = 0; j < this.values.length; ++j)
        {
            b = (Boolean)this.values[j];
            
            if(b.booleanValue())
            {
                if(i++ == index)
                {
                    return this.expansionOutputs.get(j);
                }
            }
        }
        
        return null;
    }
}
