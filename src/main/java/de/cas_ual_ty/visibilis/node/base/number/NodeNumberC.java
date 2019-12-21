package de.cas_ual_ty.visibilis.node.base.number;

import de.cas_ual_ty.visibilis.datatype.DataType;
import de.cas_ual_ty.visibilis.node.ExecProvider;
import de.cas_ual_ty.visibilis.node.base.NodeSingleC;

public abstract class NodeNumberC extends NodeSingleC<Number>
{
    public NodeNumberC()
    {
        super();
    }
    
    @Override
    public boolean doCalculate(ExecProvider provider)
    {
        this.values = new Number[this.getOutputAmt()];
        
        for (int i = 0; i < this.getOutputAmt(); ++i)
        {
            this.values[i] = this.getValue(i);
        }
        return true;
    }
    
    /**
     * @return The constant value.
     */
    protected abstract Number getValue(int index);
    
    @Override
    public DataType getDataType()
    {
        return DataType.NUMBER;
    }
}
