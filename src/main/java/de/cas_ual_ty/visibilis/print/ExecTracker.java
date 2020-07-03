package de.cas_ual_ty.visibilis.print;

import java.util.LinkedList;

import de.cas_ual_ty.visibilis.node.Node;
import de.cas_ual_ty.visibilis.node.field.Input;
import de.cas_ual_ty.visibilis.node.field.Output;

public class ExecTracker
{
    public LinkedList<Node> execNodes;
    public LinkedList<Output<?>> execOutputs;
    public LinkedList<Input<?>> execInputs;
    
    public ExecTracker()
    {
        this.execNodes = new LinkedList<>();
        this.execInputs = new LinkedList<>();
        this.execInputs = new LinkedList<>();
    }
    
    public void startNode(Node node)
    {
        this.execNodes.add(node);
    }
    
    public void exec(Node node, Output<?> from, Input<?> to)
    {
        this.execNodes.add(node);
        this.execOutputs.add(from);
        this.execInputs.add(to);
    }
}
