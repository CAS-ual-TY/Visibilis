package de.cas_ual_ty.visibilis.node.vector3d;

import de.cas_ual_ty.visibilis.node.Node;
import de.cas_ual_ty.visibilis.node.NodeType;
import de.cas_ual_ty.visibilis.node.field.Input;
import de.cas_ual_ty.visibilis.node.field.Output;
import de.cas_ual_ty.visibilis.print.provider.ExecContext;
import de.cas_ual_ty.visibilis.registries.VDataTypes;
import de.cas_ual_ty.visibilis.util.VUtility;
import net.minecraft.util.math.Vec3d;

public class NodeVector3dSplit extends Node
{
    public final Output<Double> out1Double;
    public final Output<Double> out2Double;
    public final Output<Double> out3Double;
    public final Input<Vec3d> in1Vector3d;
    
    protected double x, y, z;
    
    public NodeVector3dSplit(NodeType<?> type)
    {
        super(type);
        this.addOutput(this.out1Double = new Output<>(this, VDataTypes.DOUBLE, "out1"));
        this.addOutput(this.out2Double = new Output<>(this, VDataTypes.DOUBLE, "out2"));
        this.addOutput(this.out3Double = new Output<>(this, VDataTypes.DOUBLE, "out3"));
        this.addInput(this.in1Vector3d = new Input<>(this, VDataTypes.VECTOR3D, "in1"));
    }
    
    @Override
    public boolean doCalculate(ExecContext context)
    {
        this.x = this.in1Vector3d.getValue().getX();
        this.y = this.in1Vector3d.getValue().getY();
        this.z = this.in1Vector3d.getValue().getZ();
        return true;
    }
    
    @Override
    public <O> O getOutputValue(Output<O> out)
    {
        if(out == this.out1Double)
        {
            return VUtility.cast(this.x);
        }
        if(out == this.out2Double)
        {
            return VUtility.cast(this.y);
        }
        if(out == this.out3Double)
        {
            return VUtility.cast(this.z);
        }
        
        return null;
    }
    
    @Override
    public float[] getColor()
    {
        return VDataTypes.VECTOR3D.getColor();
    }
    
    @Override
    public float[] getTextColor()
    {
        return VDataTypes.VECTOR3D.getTextColor();
    }
}
