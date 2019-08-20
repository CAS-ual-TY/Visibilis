package de.cas_ual_ty.visibilis.node;

import java.util.ArrayList;

import de.cas_ual_ty.visibilis.datatype.VDataType;

public class Input<A> extends NodeField<A>
{
    /*
     * Clarifications: "Node": The node of this node field "Parent": The parent node of the node of this field
     */

    /**
     * The output this is connected to.
     */
    protected Output connection;

    public Input(int id, Node node, VDataType dataType, String name)
    {
        super(id, node, dataType, name);
    }

    /**
     * @return The output this is connected to (or null).
     */
    public Output getConnection()
    {
        return this.connection;
    }

    @Override
    protected boolean setConnectionTo(NodeField field)
    {
        if (field instanceof Output)
        {
            this.connection = (Output) field;
            return true;
        }

        return false;
    }

    @Override
    public A getValue()
    {
        return this.hasConnections() ? (A) this.connection.getValue() : null;
    }

    @Override
    public boolean hasConnections()
    {
        return this.connection != null;
    }

    @Override
    public boolean isOutput()
    {
        return false;
    }

    @Override
    public ArrayList<NodeField> getConnectionsList()
    {
        ArrayList<NodeField> list = new ArrayList<NodeField>();

        if (this.hasConnections())
        {
            list.add(this.connection);
        }

        return list;
    }

    @Override
    public void clearConnections()
    {
        this.connection = null;
    }

    @Override
    public void removeConnection(NodeField field)
    {
        if (this.connection == field)
        {
            this.connection = null;
        }
    }
}
