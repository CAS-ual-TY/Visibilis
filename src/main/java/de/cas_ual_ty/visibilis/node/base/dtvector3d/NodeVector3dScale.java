package de.cas_ual_ty.visibilis.node.base.dtvector3d;

import de.cas_ual_ty.visibilis.datatype.DataType;
import de.cas_ual_ty.visibilis.node.NodeType;
import de.cas_ual_ty.visibilis.node.base.trigeneric.NodeTriGenericP2;
import de.cas_ual_ty.visibilis.registries.VDataTypes;
import net.minecraft.util.math.Vec3d;

public class NodeVector3dScale extends NodeTriGenericP2<Vec3d, Vec3d, Float>
{
    public NodeVector3dScale(NodeType<?> type)
    {
        super(type);
    }
    
    @Override
    public DataType<Vec3d> getOutDataType()
    {
        return VDataTypes.VECTOR3D;
    }
    
    @Override
    public DataType<Vec3d> getIn1DataType()
    {
        return VDataTypes.VECTOR3D;
    }
    
    @Override
    public DataType<Float> getIn2DataType()
    {
        return VDataTypes.FLOAT;
    }
    
    @Override
    protected Vec3d calculate(Vec3d input, Float in2)
    {
        return input.scale(in2.doubleValue());
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
