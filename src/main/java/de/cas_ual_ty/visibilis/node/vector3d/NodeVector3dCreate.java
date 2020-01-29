package de.cas_ual_ty.visibilis.node.vector3d;

import de.cas_ual_ty.visibilis.node.ExecContext;
import de.cas_ual_ty.visibilis.node.Node;
import de.cas_ual_ty.visibilis.node.NodeType;
import de.cas_ual_ty.visibilis.node.field.Input;
import de.cas_ual_ty.visibilis.node.field.Output;
import de.cas_ual_ty.visibilis.registries.VDataTypes;
import de.cas_ual_ty.visibilis.util.VUtility;
import net.minecraft.util.math.Vec3d;

public class NodeVector3dCreate extends Node
{
    public final Output<Vec3d> out1Vector3d;
    public final Input<Double> in1Double;
    public final Input<Double> in2Double;
    public final Input<Double> in3Double;
    
    protected Vec3d value;
    
    public NodeVector3dCreate(NodeType<?> type)
    {
        super(type);
        this.addOutput(this.out1Vector3d = new Output<>(this, VDataTypes.VECTOR3D, "out1"));
        this.addInput(this.in1Double = new Input<>(this, VDataTypes.DOUBLE, "in1"));
        this.addInput(this.in2Double = new Input<>(this, VDataTypes.DOUBLE, "in2"));
        this.addInput(this.in3Double = new Input<>(this, VDataTypes.DOUBLE, "in3"));
    }
    
    @Override
    public boolean doCalculate(ExecContext context)
    {
        this.value = new Vec3d(this.in1Double.getValue(), this.in2Double.getValue(), this.in3Double.getValue());
        return true;
    }
    
    @Override
    public <O> O getOutputValue(Output<O> out)
    {
        if(out == this.out1Vector3d)
        {
            return VUtility.cast(this.value);
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
