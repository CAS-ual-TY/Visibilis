package de.cas_ual_ty.visibilis;

import javax.annotation.Nullable;

public abstract class Node
{
	/*
	 * Some explanations:
	 * The word "calculate" is described to calculate all output values of this node (output field nodes).
	 * The values are then stored so that multiple child nodes can get them without the need to calculate more than once.
	 * Once all nodes have been calculated, they will be "reset",
	 * meaning that the "isCalculated()" state will be false again, triggering recalculation on next call (and possibly other things).
	 */
	
	public int posX, posY;
	
	protected Output[] outputFields;
	protected Input[] inputFields;
	
	public Node(int posX, int posY, int outputAmt, int inputAmt)
	{
		this.posX = posX;
		this.posY = posY;
		this.outputFields = new Output[outputAmt];
		this.inputFields = new Input[inputAmt];
	}
	
	/**
	 * Did this node already calculate? If yes the result is already saved and can just be retrieved.
	 * Use {@link #isCalculated()} to check.
	 */
	protected boolean calculated = false;
	
	/**
	 * This is used to make sure that there are no endless loops.
	 * When this node starts calculating, this gets set to <b>true</b>.
	 * If it now gets called again before being done calculating, it will immediately be aborted.
	 * At the end of calculation this gets reset.
	 */
	private boolean isCalculating = false;
	
	/**
	 * Did this node already calculate? If yes the result is already saved and can just be retrieved.
	 * @return <b>true</b> if already calculated, <b>false</b> if calculation still needs to be done.
	 */
	public boolean isCalculated()
	{
		return this.calculated;
	}
	
	/**
	 * Calculate all parent nodes.
	 * @return <b>true</b> if all parent nodes have successfully been calculated, <false> if they could not be calculated.
	 */
	public boolean preCalculate()
	{
		//If this node has no inputs it also has no parents, so it can be calculated immediately
		//(or if none of the inputs have any connections)
		if(this.isStart())
		{
			NodeField field0, field1;
			
			int i, j;
			
			//Loop through all input fields
			for(i = 0; i < this.getInputAmt(); ++i)
			{
				//Get the input field of this node
				field0 = this.getInput(i);
				
				//Check if it also has some connections (parent nodes)
				//Input fields might also be disconnected
				if(field0.hasConnections())
				{
					//Input has connections, so loop through all of them
					for(j = 0; j < field0.getConnectionsList().size(); ++j)
					{
						//Get the parent node field (output of parent node)
						field1 = (NodeField) field0.getConnectionsList().get(j);
						
						//Check if it is calculated
						if(field1.node.isCalculated())
						{
							//Calculate it, return false if it fails
							if(!field1.node.calculate())
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
	 * @return <b>true</b> if all nodes have successfully been calculated, <false> if they could not be calculated.
	 */
	public boolean calculate()
	{
		//Make sure all parents are calculated
		if(!this.preCalculate())
		{
			//Abort if parents could not be calculated
			return false;
		}
		
		//Now try calculate this node
		return this.doCalculate();
	}
	
	/**
	 * Calculate this node (make sure all parents are calculated already).
	 * @return <b>true</b> if this node has successfully been calculated, <false> if it could not be calculated.
	 */
	public abstract boolean doCalculate();
	
	/**
	 * See if this node is a dead end with no further connections at the output.
	 * @return <b>true</b> if {@link #getOutputAmt()} equals 0 or none of the output fields have any connections.
	 */
	public boolean isEnd()
	{
		if(this.getOutputAmt() <= 0)
		{
			return true;
		}
		
		NodeField field0;
		
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
	 * @return <b>true</b> if {@link #getInputAmt()} equals 0 or none of the input fields have any connections.
	 */
	public boolean isStart()
	{
		//If there are no inputs, then this must be a "dead start"
		if(this.getInputAmt() <= 0)
		{
			return true;
		}
		
		NodeField field0;
		
		int i;
		
		//Otherwise, loop through inputs
		for(i = 0; i < this.getInputAmt(); ++i)
		{
			//Get the input here
			field0 = this.getInput(i);
			
			//Check if it has any connections
			if(field0.hasConnections())
			{
				//An input of this node has connections, this means that this has a parent node, so this is not a "dead start"
				return false;
			}
		}
		
		return true;
	}
	
	/**
	 * @return The total amount of output node fields.
	 */
	public int getOutputAmt()
	{
		return this.outputFields.length;
	}
	
	/**
	 * @return The total amount of input node fields.
	 */
	public int getInputAmt()
	{
		return this.inputFields.length;
	}
	
	/**
	 * Get the output node field of the specified index.
	 * @param index The index of the output node field.
	 * @return The node field at the specified index.
	 */
	public NodeField getOutput(int index)
	{
		return this.outputFields[index];
	}
	
	/**
	 * Get the input node field of the specified index.
	 * @param index The index of the input node field.
	 * @return The node field at the specified index.
	 */
	public NodeField getInput(int index)
	{
		return this.inputFields[index];
	}
	
	//Output.getValue() links to this. So the value must be stored inside the Node, not inside NodeField.
	/**
	 * Get the output value of the specified index stored in this node.
	 * @param index The index of the output node field to get the value from.
	 * @return The value of the specified index.
	 */
	@Nullable
	public abstract <B> B getOutputValue(int index);
	
	//This links to Input.getValue(), which links to the connected output's Output.GetValue(), which links to this node's parent node's Node.getOutputValue().
	//Deprecated since it is better to just call Input.getValue() directly.
	@Deprecated
	@Nullable
	public <B> B getInputValue(int index)
	{
		return (B) this.getInput(index).getValue();
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
		for(Output out : this.outputFields)
		{
			out.cutConnections();
		}
		
		for(Input in : this.inputFields)
		{
			in.cutConnections();
		}
	}
}
