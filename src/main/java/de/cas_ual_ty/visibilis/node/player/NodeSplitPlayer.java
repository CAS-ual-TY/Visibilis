package de.cas_ual_ty.visibilis.node.player;

import de.cas_ual_ty.visibilis.node.Node;
import de.cas_ual_ty.visibilis.node.NodeType;
import de.cas_ual_ty.visibilis.node.field.Input;
import de.cas_ual_ty.visibilis.node.field.Output;
import de.cas_ual_ty.visibilis.print.provider.DataProvider;
import de.cas_ual_ty.visibilis.registries.VDataTypes;
import de.cas_ual_ty.visibilis.util.VUtility;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

public class NodeSplitPlayer extends Node
{
    public Output<Vec3d> out1Vector3d; // position
    public Output<Vec3d> out2Vector3d; // motion
    public Output<World> out3World; // world
    public Input<PlayerEntity> in1Player;
    
    protected PlayerEntity player;
    protected Vec3d position;
    protected Vec3d motion;
    
    public NodeSplitPlayer(NodeType<?> type)
    {
        super(type);
        this.addOutput(this.out1Vector3d = new Output<>(this, VDataTypes.VECTOR3D, "out1"));
        this.addOutput(this.out2Vector3d = new Output<>(this, VDataTypes.VECTOR3D, "out2"));
        this.addOutput(this.out3World = new Output<>(this, VDataTypes.WORLD, "out3"));
        this.addInput(this.in1Player = new Input<>(this, VDataTypes.PLAYER, "in1"));
    }
    
    @Override
    public boolean doCalculate(DataProvider context)
    {
        this.player = this.in1Player.getValue();
        
        if(this.player == null)
        {
            return false;
        }
        
        this.position = this.player.getPositionVec();
        this.motion = this.player.getMotion();
        
        return true;
    }
    
    @Override
    public <O> O getOutputValue(Output<O> out)
    {
        return out == this.out1Vector3d ? VUtility.cast(this.position) : (out == this.out2Vector3d ? VUtility.cast(this.motion) : (out == this.out3World ? VUtility.cast(this.player.world) : null));
    }
    
    @Override
    public float[] getColor()
    {
        return VDataTypes.PLAYER.getColor();
    }
    
    @Override
    public float[] getTextColor()
    {
        return VDataTypes.PLAYER.getTextColor();
    }
}
