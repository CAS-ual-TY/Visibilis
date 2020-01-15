package de.cas_ual_ty.visibilis.node.base.bigeneric;

import de.cas_ual_ty.visibilis.node.ExecContext;
import de.cas_ual_ty.visibilis.node.field.Input;

public abstract class NodeBiGenericP2<O, I> extends NodeBiGenericP<O, I>
{
    public Input<I> in2;
    
    private I tempIn2;
    
    public NodeBiGenericP2()
    {
        super();
    }
    
    @Override
    public void createBaseFields()
    {
        this.addInput(this.in2 = new Input<>(this, this.getInDataType(), "in2"));
        
        super.createBaseFields();
    }
    
    @Override
    public boolean doCalculate(ExecContext context)
    {
        this.tempIn2 = this.in2.getValue();
        
        return super.doCalculate(context);
    }
    
    @Override
    protected boolean canCalculate(I input)
    {
        return this.canCalculate(input, this.tempIn2);
    }
    
    protected boolean canCalculate(I input, I in2)
    {
        return true;
    }
    
    @Override
    protected O calculate(I input)
    {
        return this.calculate(input, this.tempIn2);
    }
    
    protected abstract O calculate(I input, I in2);
}
