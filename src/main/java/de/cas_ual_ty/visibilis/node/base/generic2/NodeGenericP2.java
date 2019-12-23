package de.cas_ual_ty.visibilis.node.base.generic2;

import de.cas_ual_ty.visibilis.node.ExecProvider;
import de.cas_ual_ty.visibilis.node.field.Input;

public abstract class NodeGenericP2<A, C> extends NodeGeneric2P<A, C>
{
    public Input<A> in2;
    
    private A tempIn2;
    
    public NodeGenericP2()
    {
        super();
    }
    
    @Override
    public void createBaseFields()
    {
        this.addInput(this.in2 = new Input<A>(this, this.getInDataType(), "in2"));
        
        super.createBaseFields();
    }
    
    @Override
    public boolean doCalculate(ExecProvider provider)
    {
        this.tempIn2 = this.in2.getValue();
        
        return super.doCalculate(provider);
    }
    
    @Override
    protected boolean canCalculate(A input)
    {
        return this.canCalculate(input, this.tempIn2);
    }
    
    protected boolean canCalculate(A input, A in2)
    {
        return true;
    }
    
    @Override
    protected C calculate(A input)
    {
        return this.calculate(input, this.tempIn2);
    }
    
    protected abstract C calculate(A input, A in2);
}
