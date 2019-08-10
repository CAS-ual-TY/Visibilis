package de.cas_ual_ty.visibilis;

import java.util.ArrayList;

public class Print
{
	protected final ArrayList<Node> nodes = new ArrayList<Node>();
	
	public Print()
	{
	}
	
	public Print addNode(Node node)
	{
		if(this.containsNode(node))
		{
			this.removeNodeKeepConnections(node);
		}
		
		this.nodes.add(node);
		return this;
	}
	
	public boolean removeNode(Node node)
	{
		node.disconnect();
		return this.nodes.remove(node);
	}
	
	public boolean removeNodeKeepConnections(Node node)
	{
		return this.nodes.remove(node);
	}
	
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
	 * Recursive method. Executes all connected exec nodes in succession to the given parameter
	 * @param node The node to start the exec chain from
	 * @return <b>true</b> if the given parameter exec node and all child exec nodes could be calculated successfully
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
