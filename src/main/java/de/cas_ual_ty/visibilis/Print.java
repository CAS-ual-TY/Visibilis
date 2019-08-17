package de.cas_ual_ty.visibilis;

import java.util.ArrayList;

import de.cas_ual_ty.visibilis.node.Input;
import de.cas_ual_ty.visibilis.node.Node;
import de.cas_ual_ty.visibilis.node.NodeExec;
import de.cas_ual_ty.visibilis.node.Output;

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
	 * Add a node to this print. It will be added on top. If the node is already present it will be moved to the top.
	 * @param node The node to be added.
	 * @return The print instance itself.
	 */
	public Print addNode(Node node)
	{
		if(this.containsNode(node))
		{
			//Remove node from the list to add it on top again
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
	 * Executes all connected exec nodes in succession to the given parameter ({@link #exec(NodeExec)}). Then resets all nodes.
	 * @param node The node to start the exec chain from.
	 * @return <b>true</b> if the given parameter exec node and all child exec nodes could be calculated successfully.
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
		//If the node can not be calculated, abort
		if(!node.calculate())
		{
			return false;
		}
		
		Output out;
		Input in;
		NodeExec next;
		int i = 0;
		
		/*
		 * Now to explain the following segment and loop...
		 * You might be asking: "Why a loop? Why not just a single connection?"
		 * And the answer is in the question: Loops!
		 * This way, you can make nodes which can actually loop.
		 * Because for every exec node it will keep looping through, until no further sub node can be found.
		 */
		
		//Loop through all outputs of the exec type
		while((out = node.getOutExec(i)) != null)
		{
			//Get the connected input of the next node
			in = (Input) out.getConnections().get(0);
			
			if(in != null)
			{
				//Get the node of the input
				next = (NodeExec) in.node;
				
				if(next != null)
				{
					//Check the the sub node has been successfully calculated
					if(!this.exec(next))
					{
						//Abort if false
						return false;
					}
				}
				
				//Increment to get the next sub node once the current one is finished calculating
				++i;
			}
		}
		
		return true;
	}
}
