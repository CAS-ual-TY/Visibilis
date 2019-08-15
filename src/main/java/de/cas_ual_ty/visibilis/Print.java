package de.cas_ual_ty.visibilis;

import java.util.ArrayList;

public class Print
{
	/**
	 * All nodes in this print.
	 */
	protected final ArrayList<Node> nodes = new ArrayList<Node>();
	
	public Print()
	{
	}
	
	/**
	 * Add a node to this print. It will be added on top.
	 * @param node The node to be added.
	 * @return The print instance itself.
	 */
	public Print addNode(Node node)
	{
		if(this.containsNode(node))
		{
			this.removeNodeKeepConnections(node);
		}
		
		this.nodes.add(node);
		return this;
	}
	
	/**
	 * Remove a node from the print and disconnect it from all nodes. Used to delete a node safely.
	 * @param node The node to be removed and disconnected.
	 * @return <b>true</b> if this print contained the given node.
	 */
	public boolean removeNode(Node node)
	{
		node.disconnect();
		return this.nodes.remove(node);
	}
	
	/**
	 * Remove a node from the print but keep the connections. Used to reposition the node in the node list.
	 * @param node The node to be removed.
	 * @return <b>true</b> if this print contained the given node.
	 */
	public boolean removeNodeKeepConnections(Node node)
	{
		return this.nodes.remove(node);
	}
	
	/**
	 * @param node The node to check for.
	 * @return <b>true</b> if the given node is part of this print.
	 */
	public boolean containsNode(Node node)
	{
		return this.nodes.contains(node);
	}
	
	/**
	 * Runs {@link #exec(NodeExec)}. Then resets all nodes
	 */
	public boolean execute(NodeExec node)
	{
		boolean ret = this.exec(node);
		
		for(Node n : this.nodes)
		{
			n.resetValues();
		}
		
		return ret;
	}
	
	/**
	 * Recursive method. Executes all connected exec nodes in succession to the given parameter.
	 * @param node The node to start the exec chain from.
	 * @return <b>true</b> if the given parameter exec node and all child exec nodes could be calculated successfully.
	 */
	protected boolean exec(NodeExec node)
	{
		if(!node.calculate())
		{
			return false;
		}
		
		Output out;
		NodeExec next;
		int i = 0;
		
		while((out = node.getOutExec(i)) != null)
		{
			next = (NodeExec) out.getConnections().get(0);
			
			if(next != null)
			{
				if(!this.exec(next))
				{
					return false;
				}
			}
			
			++i;
		}
		
		return true;
	}
}
