package de.cas_ual_ty.visibilis.node.base.dtvector3d;

import de.cas_ual_ty.visibilis.node.ExecContext;
import de.cas_ual_ty.visibilis.node.Node;
import de.cas_ual_ty.visibilis.node.field.Input;
import de.cas_ual_ty.visibilis.node.field.Output;
import de.cas_ual_ty.visibilis.registries.VDataTypes;
import de.cas_ual_ty.visibilis.util.VUtility;
import net.minecraft.util.math.Vec3d;

public class NodeVector3dSplit extends Node
{
    public final Output<Float> out1Float;
    public final Output<Float> out2Float;
    public final Output<Float> out3Float;
    public final Input<Vec3d> in1Vector3d;
    
    protected double x, y, z;
    
    public NodeVector3dSplit()
    {
        this.addOutput(this.out1Float = new Output<>(this, VDataTypes.FLOAT, "out1"));
        this.addOutput(this.out2Float = new Output<>(this, VDataTypes.FLOAT, "out2"));
        this.addOutput(this.out3Float = new Output<>(this, VDataTypes.FLOAT, "out3"));
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
        if(out == this.out1Float)
        {
            return VUtility.cast(this.x);
        }
        if(out == this.out2Float)
        {
            return VUtility.cast(this.y);
        }
        if(out == this.out3Float)
        {
            return VUtility.cast(this.z);
        }
        
        return null;
    }
    
    @Override
    public String getID()
    {
        return "vector3d_split";
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
