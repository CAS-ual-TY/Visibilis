package de.cas_ual_ty.visibilis.node.world;

import de.cas_ual_ty.visibilis.node.Node;
import de.cas_ual_ty.visibilis.node.NodeType;
import de.cas_ual_ty.visibilis.node.field.Input;
import de.cas_ual_ty.visibilis.node.field.Output;
import de.cas_ual_ty.visibilis.print.provider.DataProvider;
import de.cas_ual_ty.visibilis.registries.VDataTypes;
import de.cas_ual_ty.visibilis.util.VUtility;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class GetBlockNode extends Node
{
    public Output<Object> out1Exec;
    public Output<String> out2String;
    public Input<Object> in1Exec;
    public Input<World> in2World;
    public Input<BlockPos> in3BlockPos;
    
    protected String value;
    
    public GetBlockNode(NodeType<?> type)
    {
        super(type);
        this.addOutput(this.out1Exec = new Output<>(this, VDataTypes.EXEC, "out1"));
        this.addOutput(this.out2String = new Output<>(this, VDataTypes.STRING, "out2"));
        this.addInput(this.in1Exec = new Input<>(this, VDataTypes.EXEC, "in1"));
        this.addInput(this.in2World = new Input<>(this, VDataTypes.WORLD, "in2"));
        this.addInput(this.in3BlockPos = new Input<>(this, VDataTypes.BLOCK_POS, "in3"));
    }
    
    @Override
    public boolean doCalculate(DataProvider context)
    {
        World world = this.in2World.getValue();
        BlockPos pos = this.in3BlockPos.getValue();
        this.value = world.getBlockState(pos).toString().substring(6).replace("}", "");
        
        return true;
    }
    
    @Override
    public <O> O getOutputValue(Output<O> out)
    {
        return out == this.out2String ? VUtility.cast(this.value) : null;
    }
    
    @Override
    public Output<Object> getOutExec(int index)
    {
        return index == 0 ? this.out1Exec : null;
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
