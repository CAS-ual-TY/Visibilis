package de.cas_ual_ty.visibilis.node.field;

import java.util.ArrayList;
import java.util.function.Supplier;

import de.cas_ual_ty.visibilis.datatype.DataType;
import de.cas_ual_ty.visibilis.node.Node;
import de.cas_ual_ty.visibilis.registries.VDataTypes;

public class Output<O> extends NodeField<O>
{
    /*
     * Clarifications:
     * 
     * "Node": The node of this node field
     * "Parent": The parent node of the node of this field
     */
    
    /**
     * All inputs this is connected to.
     */
    protected final ArrayList<Input<?>> connections;
    
    public Output(Node node, Supplier<DataType<O>> dataType, Supplier<String> name)
    {
        super(node, dataType, name);
        this.connections = new ArrayList<>();
    }
    
    public Output(Node node, DataType<O> dataType, String name)
    {
        this(node, () -> dataType, () -> name);
    }
    
    @Override
    public Output<O> setTriggerRecalculation()
    {
        super.setTriggerRecalculation();
        return this;
    }
    
    /**
     * @return The list of all connections.
     */
    public ArrayList<Input<?>> getConnections()
    {
        return this.connections;
    }
    
    @Override
    protected boolean setConnectionTo(NodeField<?> field)
    {
        if(field instanceof Input)
        {
            if(!this.connections.contains(field))
            {
                // Make sure exec node fields can only have 1 connection (not output to multiple inputs)
                if(this.getDataType() == VDataTypes.EXEC)
                {
                    this.connections.clear();
                    this.connections.add((Input<?>)field);
                }
                else
                {
                    this.connections.add((Input<?>)field);
                }
            }
            
            return true;
        }
        
        return false;
    }
    
    @Override
    public O getValue()
    {
        return this.getNode().getOutputValue(this);
    }
    
    @Override
    public boolean hasConnections()
    {
        return !this.connections.isEmpty();
    }
    
    @Override
    public ArrayList<NodeField<?>> getConnectionsList()
    {
        ArrayList<NodeField<?>> list = new ArrayList<>();
        
        if(this.hasConnections())
        {
            for(Input<?> input : this.connections)
            {
                list.add(input);
            }
        }
        
        return list;
    }
    
    @Override
    public void clearConnections()
    {
        this.connections.clear();
    }
    
    @Override
    public void removeConnectionOneSided(NodeField<?> field)
    {
        this.connections.remove(field);
    }
}
