package de.cas_ual_ty.visibilis.node;

import java.util.ArrayList;

import javax.annotation.Nullable;

import de.cas_ual_ty.visibilis.NodesRegistry;
import de.cas_ual_ty.visibilis.node.field.Input;
import de.cas_ual_ty.visibilis.node.field.NodeField;
import de.cas_ual_ty.visibilis.node.field.Output;
import de.cas_ual_ty.visibilis.util.VNBTUtility;
import net.minecraft.client.resources.I18n;
import net.minecraft.nbt.CompoundNBT;

public abstract class Node
{
    /*
     * Some explanations: The word "calculate" is described to calculate all Output<?> values of this node (Output<?> field nodes). The values are then stored so that multiple child nodes can get them without the need to calculate more than once. Once all nodes have been calculated, they will be "reset", meaning that the "isCalculated()" state will be false again, triggering recalculation on next call (and possibly other things).
     * 
     * Empty constructor needed for instantiation of nodes.
     */
    
    // NBT Keys
    public static final String KEY_POS_X = "posX";
    public static final String KEY_POS_Y = "posY";
    public static final String KEY_DATA_OUT = "data_out_";
    public static final String KEY_DATA_IN = "data_in_";
    
    public static final float[] COLOR_DEFAULT = new float[] { 0.5F, 0.5F, 0.5F };
    public static final float[] COLOR_TEXT_DEFAULT = new float[] { 1F, 1F, 1F };
    
    private int posX, posY;
    
    protected ArrayList<Output<?>> outputFields;
    protected ArrayList<Input<?>> inputFields;
    
    public Node()
    {
        this.outputFields = new ArrayList<>();
        this.inputFields = new ArrayList<>();
        this.setPosition(0, 0); // Just to make sure they are always initialized
    }
    
    public int getPosX()
    {
        return this.posX;
    }
    
    public int getPosY()
    {
        return this.posY;
    }
    
    public void setPosX(int posX)
    {
        this.posX = posX;
    }
    
    public void setPosY(int posY)
    {
        this.posY = posY;
    }
    
    public Node setPosition(int x, int y)
    {
        this.setPosX(x);
        this.setPosY(y);
        return this;
    }
    
    /**
     * Did this node already calculate? If yes the result is already saved and can just be retrieved. Use {@link #isCalculated()} to check.
     */
    protected boolean calculated = false;
    
    /**
     * Did this node already calculate? If yes the result is already saved and can just be retrieved.
     * 
     * @return <b>true</b> if already calculated, <b>false</b> if calculation still needs to be done.
     */
    public boolean isCalculated()
    {
        return this.calculated;
    }
    
    /**
     * Calculate all parent nodes.
     * 
     * @return <b>true</b> if all parent nodes have successfully been calculated, <false> if they could not be calculated.
     */
    public boolean preCalculate(ExecProvider provider)
    {
        // If this node has no inputs it also has no parents, so it can be calculated immediately
        // (or if none of the inputs have any connections)
        if(!this.isStart())
        {
            NodeField<?> field0, field1;
            
            int i, j;
            
            // Loop through all Input<?> fields
            for(i = 0; i < this.getInputAmt(); ++i)
            {
                // Get the Input<?> field of this node
                field0 = this.getInput(i);
                
                // Check if it also has some connections (parent nodes)
                // Input<?> fields might also be disconnected
                if(field0.hasConnections())
                {
                    // Input<?> has connections, so loop through all of them
                    for(j = 0; j < field0.getConnectionsList().size(); ++j)
                    {
                        // Get the parent node field (Output<?> of parent node)
                        field1 = (NodeField<?>)field0.getConnectionsList().get(j);
                        
                        // Check if it is calculated
                        if(!field1.getNode().isCalculated())
                        {
                            // Calculate it, return false if it fails
                            if(!field1.getNode().calculate(provider))
                            {
                                return false;
                            }
                        }
                    }
                }
            }
        }
        
        return true;
    }
    
    /**
     * Calculate this node and all parent nodes.
     * 
     * @return <b>true</b> if all nodes have successfully been calculated, <false> if they could not be calculated.
     */
    public boolean calculate(ExecProvider provider)
    {
        // Make sure all parents are calculated
        if(!this.preCalculate(provider) || !this.hasAllRequiredInputs(provider))
        {
            // Abort if parents could not be calculated
            return false;
        }
        
        // Now try calculate this node
        return this.doCalculate(provider);
    }
    
    /**
     * Calculate this node (make sure all parents are calculated already).
     * 
     * @return <b>true</b> if this node has successfully been calculated, <false> if it could not be calculated.
     */
    public abstract boolean doCalculate(ExecProvider provider);
    
    /**
     * See if this node is a dead end with no further connections at the output.
     * 
     * @return <b>true</b> if {@link #getOutputAmt()} equals 0 or none of the Output<?> fields have any connections.
     */
    public boolean isEnd()
    {
        if(this.getOutputAmt() <= 0)
        {
            return true;
        }
        
        NodeField<?> field0;
        
        int i;
        
        for(i = 0; i < this.getOutputAmt(); ++i)
        {
            field0 = this.getOutput(i);
            
            if(field0.hasConnections())
            {
                return false;
            }
        }
        
        return true;
    }
    
    /**
     * See if this node is a dead <s>end</s> start with no further connections at the input.
     * 
     * @return <b>true</b> if {@link #getInputAmt()} equals 0 or none of the Input<?> fields have any connections.
     */
    public boolean isStart()
    {
        // If there are no inputs, then this must be a "dead start"
        if(this.getInputAmt() <= 0)
        {
            return true;
        }
        
        NodeField<?> field0;
        
        int i;
        
        // Otherwise, loop through inputs
        for(i = 0; i < this.getInputAmt(); ++i)
        {
            // Get the Input<?> here
            field0 = this.getInput(i);
            
            // Check if it has any connections
            if(field0.hasConnections())
            {
                // An Input<?> of this node has connections, this means that this has a parent node, so this is not a "dead start"
                return false;
            }
        }
        
        return true;
    }
    
    /**
     * Returns <b>true</b> if all inputs that are required for calculation are connected. Override if there are any optional inputs not required for calculation.
     */
    public boolean hasAllRequiredInputs(ExecProvider provider)
    {
        Input<?> field0;
        int i;
        
        // loop through inputs
        for(i = 0; i < this.getInputAmt(); ++i)
        {
            // Get the Input<?> here
            field0 = (Input<?>)this.getInput(i);
            
            // Check if it needs connections or it currently returns nothing
            if(field0.getMustUseConnections() && !field0.hasConnections())
            {
                // no connections -> return false
                return false;
            }
        }
        
        return true;
    }
    
    public int getOutputId(Output<?> out)
    {
        return this.outputFields.indexOf(out);
    }
    
    public int getInputId(Input<?> in)
    {
        return this.inputFields.indexOf(in);
    }
    
    public int addOutput(Output<?> out)
    {
        return this.addOutput(out, this.getOutputAmt());
    }
    
    public int addOutput(Output<?> out, int index)
    {
        if(out != null && !this.outputFields.contains(out))
        {
            this.outputFields.add(index, out);
            
            for(int i = index; i < this.getOutputAmt(); ++i)
            {
                this.getOutput(i).recalculateId();
            }
            
            return this.getOutputId(out);
        }
        
        return -1;
    }
    
    public int addInput(Input<?> in)
    {
        return this.addInput(in, this.getInputAmt());
    }
    
    public int addInput(Input<?> in, int index)
    {
        if(in != null && !this.inputFields.contains(in))
        {
            this.inputFields.add(index, in);
            
            for(int i = index; i < this.getInputAmt(); ++i)
            {
                this.getInput(i).recalculateId();
            }
            
            return this.getInputId(in);
        }
        
        return -1;
    }
    
    public void removeOutput(Output<?> out0)
    {
        if(this.outputFields.contains(out0))
        {
            out0.cutConnections();
            this.outputFields.remove(out0);
            
            for(Output<?> out : this.outputFields)
            {
                out.recalculateId();
            }
        }
    }
    
    public void removeOutput(int index)
    {
        this.outputFields.remove(index);
        
        for(int i = index; i < this.getOutputAmt(); ++i)
        {
            this.getOutput(i).recalculateId();
        }
    }
    
    public void removeInput(Input<?> in0)
    {
        if(this.inputFields.contains(in0))
        {
            in0.cutConnections();
            this.inputFields.remove(in0);
            
            for(Input<?> in : this.inputFields)
            {
                in.recalculateId();
            }
        }
    }
    
    public void removeInput(int index)
    {
        this.inputFields.remove(index);
        
        for(int i = index; i < this.getInputAmt(); ++i)
        {
            this.getInput(i).recalculateId();
        }
    }
    
    /**
     * @return The total amount of Output<?> node fields.
     */
    public int getOutputAmt()
    {
        return this.outputFields.size();
    }
    
    /**
     * @return The total amount of Input<?> node fields.
     */
    public int getInputAmt()
    {
        return this.inputFields.size();
    }
    
    /**
     * Get the Output<?> node field of the specified index.
     * 
     * @param index
     *            The index of the Output<?> node field.
     * @return The node field at the specified index.
     */
    public Output<?> getOutput(int index)
    {
        return this.outputFields.get(index);
    }
    
    /**
     * Get the Input<?> node field of the specified index.
     * 
     * @param index
     *            The index of the Input<?> node field.
     * @return The node field at the specified index.
     */
    public Input<?> getInput(int index)
    {
        return this.inputFields.get(index);
    }
    
    // Output.getValue() links to this. So the value must be stored inside the Node, not inside NodeField.
    /**
     * Get the Output<?> value of the specified index stored in this node.
     * 
     * @param out
     *            the Output node field to get the value from.
     * @return The value of the specified node field.
     */
    @Nullable
    public abstract <O> O getOutputValue(Output<O> out);
    
    // This links to Input.getValue(), which links to the connected output's Output.GetValue(), which links to this node's parent node's Node.getOutputValue().
    // It is better to just call Input.getValue() directly.
    @Nullable
    public <I> I getInputValue(Input<I> in)
    {
        return in.getValue();
    }
    
    /**
     * Reset all values of this node (if needed) and make it calculate again on next call.
     */
    public void resetValues()
    {
        this.calculated = false;
    }
    
    /**
     * Cut all connections of inputs and outputs (both directions).
     */
    public void disconnect()
    {
        for(Output<?> out : this.outputFields)
        {
            out.cutConnections();
        }
        
        for(Input<?> in : this.inputFields)
        {
            in.cutConnections();
        }
    }
    
    /**
     * Get the header color of this node
     */
    public float[] getColor()
    {
        return this instanceof INodeExec ? INodeExec.getColor() : Node.COLOR_DEFAULT;
    }
    
    /**
     * Get text color in the header of this node
     */
    public float[] getTextColor()
    {
        return this instanceof INodeExec ? INodeExec.getTextColor() : Node.COLOR_TEXT_DEFAULT;
    }
    
    /**
     * Used for translation. All lower case, '_' can be used. Use {@link NodesRegistry#getNameForNode(Class)} for a registry name instead.
     * 
     * @return A unique identifier (for translation only)
     */
    public abstract String getID();
    
    /**
     * Used for translating the name of this node.
     */
    public String getUnlocalizedName()
    {
        return "node." + this.getID();// + ".name";
    }
    
    /**
     * Used for translating the description of this node.
     */
    public String getUnlocalizedDesc()
    {
        return "node." + this.getID() + ".desc";
    }
    
    /**
     * Used for getting the node group (and tags) for this node.
     */
    public String getUnlocalizedGroup()
    {
        return "node." + this.getID() + ".groups";
    }
    
    /**
     * Used for translating the name of a field of this node.
     */
    public String getFieldUnlocalizedName(NodeField<?> field)
    {
        return "field." + this.getID() + "." + field.getName();// + ".name";
    }
    
    /**
     * Used for translating the description of a field of this node.
     */
    public String getFieldUnlocalizedDesc(NodeField<?> field)
    {
        return "field." + this.getID() + "." + field.getName() + ".desc";
    }
    
    public String getName()
    {
        return I18n.format(this.getUnlocalizedName());
    }
    
    public String getDesc()
    {
        return I18n.format(this.getUnlocalizedDesc());
    }
    
    public String getGroup()
    {
        return I18n.format(this.getUnlocalizedGroup());
    }
    
    public String getFieldName(NodeField<?> field)
    {
        return I18n.format(this.getFieldUnlocalizedName(field));
    }
    
    public String getFieldDesc(NodeField<?> field)
    {
        return I18n.format(this.getFieldUnlocalizedDesc(field));
    }
    
    /**
     * Read from NBT. Does not load everything, see {@link VNBTUtility#readNodeFromNBT(Node, NBTTagCompound)} for a proper method
     */
    public void readNodeFromNBT(CompoundNBT nbt0)
    {
        this.setPosX(nbt0.getInt(Node.KEY_POS_X));
        this.setPosY(nbt0.getInt(Node.KEY_POS_Y));
        
        CompoundNBT nbt;
        NodeField<?> f;
        
        for(int i = 0; i < this.getOutputAmt(); ++i)
        {
            if(nbt0.contains(Node.KEY_DATA_OUT + i))
            {
                f = this.getOutput(i);
                nbt = nbt0.getCompound(Node.KEY_DATA_OUT + f.getId());
                f.readFromNBT(nbt);
            }
        }
        
        for(int i = 0; i < this.getInputAmt(); ++i)
        {
            if(nbt0.contains(Node.KEY_DATA_IN + i))
            {
                f = this.getInput(i);
                nbt = nbt0.getCompound(Node.KEY_DATA_IN + f.getId());
                f.readFromNBT(nbt);
            }
        }
    }
    
    /**
     * Write to NBT. Does not write everything, see {@link VNBTUtility#writeNodeToNBT(Node, NBTTagCompound)} for a proper method
     */
    public void writeNodeToNBT(CompoundNBT nbt0)
    {
        nbt0.putInt(Node.KEY_POS_X, this.getPosX());
        nbt0.putInt(Node.KEY_POS_Y, this.getPosY());
        
        CompoundNBT nbt;
        
        for(NodeField<?> f : this.outputFields)
        {
            if(f.useNBT())
            {
                nbt = new CompoundNBT();
                f.writeToNBT(nbt);
                nbt0.put(Node.KEY_DATA_OUT + f.getId(), nbt);
            }
        }
        
        for(NodeField<?> f : this.inputFields)
        {
            if(f.useNBT())
            {
                nbt = new CompoundNBT();
                f.writeToNBT(nbt);
                nbt0.put(Node.KEY_DATA_IN + f.getId(), nbt);
            }
        }
    }
    
    /**
     * Can you right click this node?
     */
    public boolean hasActions()
    {
        return this.getActions() != null;
    }
    
    /**
     * Things you can do when right clicking this node. See {@link NodeAction#NodeAction(String)}
     */
    public ArrayList<NodeAction> getActions()
    {
        return new ArrayList<>();
    }
    
    public void triggerRecalculation(Input<?> input)
    {
        if(input.hasConnections())
        {
            this.triggerRecalculation(input.getConnection().getNode());
        }
    }
    
    public void triggerRecalculation(Node node)
    {
        if(node != null)
        {
            for(int i = 0; i < node.getInputAmt(); ++i)
            {
                this.triggerRecalculation(node.getInput(i));
            }
        }
    }
    
    /**
     * Returns a node clone. It is Disconnected.
     */
    @Override
    public Node clone()
    {
        //Yes very lazy I know :P But atleast we have a solution for now
        return VNBTUtility.loadNodeFromNBT(VNBTUtility.saveNodeToNBT(this));
    }
}
