package de.cas_ual_ty.visibilis.node;

import javax.annotation.Nullable;

import de.cas_ual_ty.visibilis.node.field.Output;
import de.cas_ual_ty.visibilis.registries.VDataTypes;

public interface INodeExec
{
    
    /**
     * After calculation, get the next output of the exec data type (basically just the next exec node to calculate). This is getting called repeatedly until <b>null</b> is returned.
     * 
     * @see de.cas_ual_ty.visibilis.print.Print#execute()
     * @param index
     *            The index of the sub nodes starting at 0, incremented after every call.
     * @return The next node in execution order. <b>null</b> if there are no more sub nodes.
     */
    @Nullable
    public Output<Object> getOutExec(int index);
    
    public default Node toNode()
    {
        return (Node)this;
    }
    
    public static float[] getColor()
    {
        return VDataTypes.EXEC.getColor();
    }
    
    public static float[] getTextColor()
    {
        return VDataTypes.EXEC.getTextColor();
    }
}
