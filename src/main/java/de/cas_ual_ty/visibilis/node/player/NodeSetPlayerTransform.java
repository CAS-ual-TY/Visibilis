package de.cas_ual_ty.visibilis.node.player;

import de.cas_ual_ty.visibilis.Visibilis;
import de.cas_ual_ty.visibilis.node.Node;
import de.cas_ual_ty.visibilis.node.NodeType;
import de.cas_ual_ty.visibilis.node.field.Input;
import de.cas_ual_ty.visibilis.node.field.Output;
import de.cas_ual_ty.visibilis.print.provider.DataProvider;
import de.cas_ual_ty.visibilis.registries.VDataTypes;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.util.math.Vec3d;
import net.minecraftforge.fml.network.PacketDistributor;

public class NodeSetPlayerTransform extends Node
{
    public Output<Object> out1Exec;
    
    public Input<Object> in1Exec;
    public Input<PlayerEntity> in2Player;
    public Input<Vec3d> in3Vector3d; // position
    public Input<Vec3d> in4Vector3d; // motion
    
    protected PlayerEntity player;
    
    public NodeSetPlayerTransform(NodeType<?> type)
    {
        super(type);
        this.addOutput(this.out1Exec = new Output<>(this, VDataTypes.EXEC, "out1"));
        this.addInput(this.in1Exec = new Input<>(this, VDataTypes.EXEC, "in1"));
        this.addInput(this.in2Player = new Input<>(this, VDataTypes.PLAYER, "in2"));
        this.addInput(this.in3Vector3d = new Input<>(this, VDataTypes.VECTOR3D, "in3").setValue(Vec3d.ZERO));
        this.addInput(this.in4Vector3d = new Input<>(this, VDataTypes.VECTOR3D, "in4").setValue(Vec3d.ZERO));
    }
    
    @Override
    public boolean doCalculate(DataProvider context)
    {
        this.player = this.in2Player.getValue();
        
        if(this.player == null)
        {
            return false;
        }
        
        if(this.in3Vector3d.hasConnections() || this.in4Vector3d.hasConnections())
        {
            Vec3d position = this.in3Vector3d.getValue();
            Vec3d motion = this.in4Vector3d.getValue();
            
            this.player.getServer().deferTask(() ->
            {
                if(this.in3Vector3d.hasConnections())
                {
                    this.player.setPositionAndUpdate(position.x, position.y, position.z);
                }
                if(this.in4Vector3d.hasConnections())
                {
                    this.player.setMotion(motion);
                    
                    if(!this.player.world.isRemote)
                    {
                        Visibilis.channel.send(PacketDistributor.PLAYER.with(() -> (ServerPlayerEntity)this.player), new MessagePlayerMotion(motion));
                    }
                }
            });
        }
        
        return true;
    }
    
    @Override
    public <O> O getOutputValue(Output<O> out)
    {
        return null;
    }
    
    @Override
    public Output<Object> getOutExec(int index)
    {
        return index == 0 ? this.out1Exec : null;
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
