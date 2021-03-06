package de.cas_ual_ty.visibilis.node.player;

import de.cas_ual_ty.visibilis.node.Node;
import de.cas_ual_ty.visibilis.node.NodeType;
import de.cas_ual_ty.visibilis.node.field.Input;
import de.cas_ual_ty.visibilis.node.field.Output;
import de.cas_ual_ty.visibilis.print.provider.DataKey;
import de.cas_ual_ty.visibilis.print.provider.DataProvider;
import de.cas_ual_ty.visibilis.registries.VDataTypes;
import de.cas_ual_ty.visibilis.util.VUtility;
import net.minecraft.entity.player.PlayerEntity;

public class GetPlayerOptionalNode extends Node
{
    public Output<Object> out1Exec;
    public Output<PlayerEntity> out2Player;
    public Output<Object> out3Exec;
    public Input<Object> in1Exec;
    
    protected PlayerEntity value;
    
    public GetPlayerOptionalNode(NodeType<?> type)
    {
        super(type);
        this.addOutput(this.out1Exec = new Output<>(this, VDataTypes.EXEC, "out1"));
        this.addOutput(this.out2Player = new Output<>(this, VDataTypes.PLAYER, "out2"));
        this.addOutput(this.out3Exec = new Output<>(this, VDataTypes.EXEC, "out3"));
        this.addInput(this.in1Exec = new Input<>(this, VDataTypes.EXEC, "in1"));
    }
    
    @Override
    public boolean doCalculate(DataProvider context)
    {
        this.value = null;
        context.getDataOptional(DataKey.KEY_COMMAND_SOURCE).ifPresent((source) ->
        {
            if(source.getEntity() instanceof PlayerEntity)
            {
                this.value = (PlayerEntity)source.getEntity();
            }
            else
            {
                this.value = null;
            }
        });
        
        return true;
    }
    
    @Override
    public <O> O getOutputValue(Output<O> out)
    {
        return out == this.out2Player ? VUtility.cast(this.value) : null;
    }
    
    @Override
    public Output<Object> getOutExec(int index)
    {
        return index == 0 ? (this.value != null ? this.out1Exec : this.out3Exec) : null;
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
