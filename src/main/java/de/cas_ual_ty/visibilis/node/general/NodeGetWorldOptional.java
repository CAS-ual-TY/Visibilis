package de.cas_ual_ty.visibilis.node.general;

import de.cas_ual_ty.visibilis.node.ExecContext;
import de.cas_ual_ty.visibilis.node.Node;
import de.cas_ual_ty.visibilis.node.NodeType;
import de.cas_ual_ty.visibilis.node.field.Input;
import de.cas_ual_ty.visibilis.node.field.Output;
import de.cas_ual_ty.visibilis.registries.VDataTypes;
import de.cas_ual_ty.visibilis.util.VUtility;
import net.minecraft.world.World;

public class NodeGetWorldOptional extends Node
{
    public Output<Object> out1Exec;
    public Output<World> out2World;
    public Output<Object> out3Exec;
    public Input<Object> in1Exec;
    
    protected World value;
    
    public NodeGetWorldOptional(NodeType<?> type)
    {
        super(type);
        this.addOutput(this.out1Exec = new Output<>(this, VDataTypes.EXEC, "out1"));
        this.addOutput(this.out2World = new Output<>(this, VDataTypes.WORLD, "out2"));
        this.addOutput(this.out3Exec = new Output<>(this, VDataTypes.EXEC, "out3"));
        this.addInput(this.in1Exec = new Input<>(this, VDataTypes.EXEC, "in1"));
    }
    
    @Override
    public boolean doCalculate(ExecContext context)
    {
        this.value = context.getData().getWorld();
        return true;
    }
    
    @Override
    public <O> O getOutputValue(Output<O> out)
    {
        return out == this.out2World ? VUtility.cast(this.value) : null;
    }
    
    @Override
    public Output<Object> getOutExec(int index)
    {
        return index == 0 ? (this.value != null ? this.out1Exec : this.out3Exec) : null;
    }
    
    @Override
    public float[] getColor()
    {
        return VDataTypes.WORLD.getColor();
    }
    
    @Override
    public float[] getTextColor()
    {
        return VDataTypes.WORLD.getTextColor();
    }
}
