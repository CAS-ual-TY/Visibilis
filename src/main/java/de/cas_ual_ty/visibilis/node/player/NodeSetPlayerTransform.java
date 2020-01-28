package de.cas_ual_ty.visibilis.node.player;

import de.cas_ual_ty.visibilis.Visibilis;
import de.cas_ual_ty.visibilis.node.ExecContext;
import de.cas_ual_ty.visibilis.node.Node;
import de.cas_ual_ty.visibilis.node.NodeType;
import de.cas_ual_ty.visibilis.node.field.Input;
import de.cas_ual_ty.visibilis.node.field.NodeField;
import de.cas_ual_ty.visibilis.node.field.Output;
import de.cas_ual_ty.visibilis.registries.VDataTypes;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.util.math.Vec3d;
import net.minecraftforge.fml.network.NetworkDirection;

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
    public boolean preCalculate(ExecContext context)
    {
        if(!this.isStart())
        {
            NodeField<?> field0, field1;
            
            int i, j;
            
            // Loop through all Input<?> fields
            for(i = 0; i < this.getInputAmt(); ++i)
            {
                // Get the Input<?> field of this node
                field0 = this.getInput(i);
                
                // Check if it also has some connections (parent nodes)
                // Input<?> fields might also be disconnected
                if(field0.hasConnections())
                {
                    // Input<?> has connections, so loop through all of them
                    for(j = 0; j < field0.getConnectionsList().size(); ++j)
                    {
                        // Get the parent node field (Output<?> of parent node)
                        field1 = (NodeField<?>)field0.getConnectionsList().get(j);
                        
                        // Check if it is calculated
                        if(!field1.getNode().isCalculated())
                        {
                            // Calculate it, return false if it fails
                            if(!field1.getNode().calculate(context))
                            {
                                Visibilis.debug("[1]");
                                return false;
                            }
                        }
                    }
                }
            }
        }
        
        return true;
    }
    
    @Override
    public boolean hasAllRequiredInputs(ExecContext context)
    {
        Input<?> field0;
        int i;
        
        // loop through inputs
        for(i = 0; i < this.getInputAmt(); ++i)
        {
            // Get the Input<?> here
            field0 = (Input<?>)this.getInput(i);
            
            // Check if it needs connections or it currently returns nothing
            if(field0.getMustUseConnections() && !field0.hasConnections())
            {
                // no connections -> return false
                Visibilis.debug("[2]");
                return false;
            }
        }
        
        return true;
    }
    
    @Override
    public boolean doCalculate(ExecContext context)
    {
        Visibilis.debug("Transform Called!");
        
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
                    Visibilis.channel.sendTo(new MessagePlayerMotion(motion), ((ServerPlayerEntity)this.player).connection.getNetworkManager(), NetworkDirection.PLAY_TO_CLIENT);
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
