package de.cas_ual_ty.visibilis.node.field;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.function.Function;

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
    protected final List<Input<?>> connections;
    
    public Output(Node node, DataType<O> dataType, Function<NodeField<O>, String> name)
    {
        super(node, dataType, name);
        this.connections = new LinkedList<>();
    }
    
    public Output(Node node, DataType<O> dataType, String name)
    {
        this(node, dataType, (field) -> name);
    }
    
    @Override
    public Output<O> setForceDynamic()
    {
        super.setForceDynamic();
        return this;
    }
    
    /**
     * @return The list of all connections.
     */
    public List<Input<?>> getConnections()
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
    public final boolean isOutput()
    {
        return true;
    }
    
    @Override
    public final boolean isInput()
    {
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
    protected void clearConnections()
    {
        this.connections.clear();
    }
    
    @Override
    public void removeConnectionOneSided(NodeField<?> field)
    {
        this.connections.remove(field);
    }
    
    @Override
    public void onConnect()
    {
        this.getNode().updateDynamic();
        this.getNode().onOutputConnect(this);
    }
    
    @Override
    public void onDisconnect()
    {
        this.getNode().updateDynamic();
        this.getNode().onOutputDisconnect(this);
    }
}
