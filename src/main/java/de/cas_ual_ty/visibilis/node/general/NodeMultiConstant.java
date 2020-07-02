package de.cas_ual_ty.visibilis.node.general;

import de.cas_ual_ty.visibilis.datatype.DataType;
import de.cas_ual_ty.visibilis.node.NodeType;
import de.cas_ual_ty.visibilis.node.base.generic.NodeGenericConstant;
import de.cas_ual_ty.visibilis.node.field.Input;
import de.cas_ual_ty.visibilis.node.field.NodeField;
import de.cas_ual_ty.visibilis.node.field.Output;
import de.cas_ual_ty.visibilis.registries.VDataTypes;
import de.cas_ual_ty.visibilis.util.VUtility;

public class NodeMultiConstant extends NodeGenericConstant<Object>
{
    protected boolean disconnecting;
    
    public NodeMultiConstant(NodeType<?> type)
    {
        super(type);
        this.disconnecting = false;
    }
    
    @Override
    public DataType<Object> getDataType()
    {
        return VDataTypes.OBJECT;
    }
    
    @Override
    public Input<Object> createDynamicInput()
    {
        return super.createDynamicInput();
    }
    
    @Override
    public <I> void onInputConnect(Input<I> input)
    {
        if(this.disconnecting || !this.expansionInputs.contains(input))
        {
            return;
        }
        
        int id2 = this.expansionInputs.indexOf(input);
        
        if(id2 >= 0)
        {
            int id1 = input.getId();
            DataType<? extends Object> from = input.getConnection().getDataType();
            
            Input<Object> in2 = VUtility.cast(new Input<>(this, from, "in1"));
            Output<Object> out2 = VUtility.cast(new Output<>(this, from, "out1"));
            NodeField.connect(input.getConnection(), in2);
            
            this.expansionInputs.remove(id2);
            this.expansionOutputs.remove(id2);
            this.removeInput(id1);
            this.removeOutput(id1);
            
            this.addInput(in2, id1);
            this.addOutput(out2, id1);
            this.expansionInputs.add(id2, in2);
            this.expansionOutputs.add(id2, out2);
        }
    }
    
    @Override
    public <I> void onInputDisconnect(Input<I> input)
    {
        if(this.disconnecting || !this.expansionInputs.contains(input))
        {
            return;
        }
        
        int id2 = this.expansionInputs.indexOf(input);
        
        if(id2 >= 0)
        {
            int id1 = input.getId();
            Input<Object> in2 = this.createDynamicInput();
            Output<Object> out2 = this.createDynamicOutput();
            
            this.expansionInputs.remove(id2);
            this.expansionOutputs.remove(id2);
            this.removeInput(id1);
            this.removeOutput(id1);
            
            this.addInput(in2, id1);
            this.addOutput(out2, id1);
            this.expansionInputs.add(id2, in2);
            this.expansionOutputs.add(id2, out2);
        }
    }
    
    @Override
    public void disconnect()
    {
        this.disconnecting = true;
        super.disconnect();
    }
}
