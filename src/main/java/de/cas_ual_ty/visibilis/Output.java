package de.cas_ual_ty.visibilis;

import java.util.ArrayList;

public class Output<A> extends NodeField<A>
{
	/*
	 * Clarifications:
	 * "Node": The node of this node field
	 * "Parent": The parent node of the node of this field
	 */
	
	/**
	 * All inputs this is connected to.
	 */
	protected final ArrayList<Input> connections;
	
	public Output(int id, Node node, String dataType, String name)
	{
		super(id, node, dataType, name);
		this.connections = new ArrayList<Input>();
	}
	
	/**
	 * @return The list of all connections.
	 */
	public ArrayList<Input> getConnections()
	{
		return this.connections;
	}
	
	@Override
	protected boolean setConnectionTo(NodeField field)
	{
		if(field instanceof Input)
		{
			if(!this.connections.contains(field))
			{
				this.connections.add((Input) field);
			}
			
			return true;
		}
		
		return false;
	}
	
	@Override
	public A getValue()
	{
		return this.node.<A>getOutputValue(this.id);
	}
	
	@Override
	public boolean hasConnections()
	{
		return !this.connections.isEmpty();
	}
	
	@Override
	public boolean isOutput()
	{
		return true;
	}
	
	@Override
	public ArrayList<NodeField> getConnectionsList()
	{
		ArrayList<NodeField> list = new ArrayList<NodeField>();
		
		if(this.hasConnections())
		{
			for(Input input : this.connections)
			{
				list.add(input);
			}
		}
		
		return list;
	}
}
