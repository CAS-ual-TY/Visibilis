package de.cas_ual_ty.visibilis.node.general.bool;

import de.cas_ual_ty.visibilis.datatype.DataType;
import de.cas_ual_ty.visibilis.node.ExecProvider;
import de.cas_ual_ty.visibilis.node.general.NodeSingleC;

public abstract class NodeBoolC extends NodeSingleC<Boolean>
{
    public NodeBoolC()
    {
        super();
    }
    
    @Override
    public boolean doCalculate(ExecProvider provider)
    {
        this.values = new Boolean[this.getOutputAmt()];
        
        for (int i = 0; i < this.getOutputAmt(); ++i)
        {
            this.values[i] = this.getValue(i);
        }
        return true;
    }
    
    /**
     * @return The constant value.
     */
    protected abstract Boolean getValue(int index);
    
    @Override
    public DataType getDataType()
    {
        return DataType.BOOLEAN;
    }
}
