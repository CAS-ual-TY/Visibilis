package de.cas_ual_ty.visibilis.node.general;

import de.cas_ual_ty.visibilis.datatype.DataType;
import de.cas_ual_ty.visibilis.node.NodeType;
import de.cas_ual_ty.visibilis.node.base.generic.NodeGenericConstant;
import de.cas_ual_ty.visibilis.node.field.Input;
import de.cas_ual_ty.visibilis.node.field.NodeField;
import de.cas_ual_ty.visibilis.node.field.Output;
import de.cas_ual_ty.visibilis.registries.VDataTypes;

public class NodeMultiConstant extends NodeGenericConstant<Object>
{
    public NodeMultiConstant(NodeType<?> type)
    {
        super(type);
    }
    
    @Override
    public DataType<Object> getDataType()
    {
        return VDataTypes.OBJECT;
    }
    
    @Override
    public Input<Object> createDynamicInput()
    {
        return super.createDynamicInput().setConnectCallable(this::onInputConnect);
    }
    
    @SuppressWarnings({ "unchecked", "rawtypes" })
    public void onInputConnect(NodeField<Object> in0)
    {
        Input<Object> input = (Input<Object>)in0;
        int id2 = this.expansionInputs.indexOf(input);
        
        if(id2 >= 0)
        {
            int id1 = input.getId();
            DataType<?> from = input.getConnection().getDataType();
            
            Input<Object> in2 = new Input(this, from, "in1").setDisconnectCallable((in) -> this.onInputDisconnect((NodeField<?>)in));
            Output<Object> out2 = new Output(this, from, "out2");
            NodeField.connect(input.getConnection(), in2);
            
            this.removeInput(id1);
            this.removeOutput(id1);
            this.expansionInputs.remove(id2);
            this.expansionOutputs.remove(id2);
            
            this.addInput(in2, id1);
            this.addOutput(out2, id1);
            this.expansionInputs.add(id2, in2);
            this.expansionOutputs.add(id2, out2);
        }
    }
    
    public void onInputDisconnect(NodeField<?> input)
    {
        int id2 = this.expansionInputs.indexOf(input);
        
        if(id2 >= 0)
        {
            int id1 = input.getId();
            Input<Object> in2 = this.createDynamicInput();
            Output<Object> out2 = this.createDynamicOutput();
            
            this.removeInput(id1);
            this.removeOutput(id1);
            this.expansionInputs.remove(id2);
            this.expansionOutputs.remove(id2);
            
            this.addInput(in2, id1);
            this.addOutput(out2, id1);
            this.expansionInputs.add(id2, in2);
            this.expansionOutputs.add(id2, out2);
        }
    }
}
