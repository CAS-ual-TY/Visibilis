package de.cas_ual_ty.visibilis;

import java.util.ArrayList;

import javax.annotation.Nullable;

public abstract class NodeField<A>
{
	/*
	 * Clarifications:
	 * "Node": The node of this node field
	 * "Parent": The parent node of the node of this field
	 */
	
	/**
	 * The index of this node field in the node.
	 */
	public final int id;
	
	/**
	 * The parent node of this node field.
	 */
	public final Node node;
	
	/**
	 * The data type of this node field.
	 */
	public final String dataType;
	
	/**
	 * The name of this node field (before translation).
	 */
	public final String name;
	
	public NodeField(int id, Node node, String dataType, String name)
	{
		this.id = id;
		this.node = node;
		this.dataType = dataType;
		this.name = name;
		
		if(this.isOutput())
		{
			this.node.outputFields[this.id] = (Output) this;
		}
		else
		{
			this.node.inputFields[this.id] = (Input) this;
		}
	}
	
	/**
	 * Check if this node field can connect to another node field.
	 * @param field The other node field.
	 * @return <b>true</b>, if this node field can be connected to the other node field.
	 */
	public boolean canConnectTo(NodeField field)
	{
		return this.isOutput() != field.isOutput() && this.dataType == field.dataType;
	}
	
	/**
	 * Try to connect this node field to another node field.
	 * @param field The other node field.
	 * @return <b>true</b>, if this node field has been connected to the other node field (or the connection was already present).
	 */
	protected abstract boolean setConnectionTo(NodeField field);
	
	/**
	 * Try to connect this node field to another node field.
	 * @param field The other node field.
	 * @return <b>true</b>, if this node field has been connected to the other node field (or the connection was already present).
	 */
	public boolean tryConnectTo(NodeField field)
	{
		if(this.canConnectTo(field))
		{
			this.setConnectionTo(field);
			return true;
		}
		
		return false;
	}
	
	public abstract boolean isOutput();
	
	public boolean isInput()
	{
		return !this.isOutput();
	}
	
	/**
	 * @return The value this node field is currently representing.
	 */
	@Nullable
	public abstract A getValue();
	
	/**
	 * @return <b>true</b> if this node is connected to another node (or multiple).
	 */
	public abstract boolean hasConnections();
	
	/**
	 * Retrieve a list of all connections. Changing this list does not do anything to the node field. It can be empty.
	 * @return A list of all node fields this is node field is connected to.
	 */
	public abstract ArrayList<NodeField> getConnectionsList();
	
	/**
	 * Cut all connections to other fields (but not their connections to this field).
	 */
	public abstract void clearConnections();
	
	/**
	 * Cut the connection from a field to this field (but not from this field to the given field).
	 * @param field The node field to cut the connection from.
	 */
	public abstract void removeConnection(NodeField field);
	
	/**
	 * Cut all connections to other fields, and their connections to this field.
	 */
	public void cutConnections()
	{
		for(NodeField field : this.getConnectionsList())
		{
			field.removeConnection(this);
		}
		
		this.clearConnections();
	}
}
