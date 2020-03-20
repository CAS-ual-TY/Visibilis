package de.cas_ual_ty.visibilis.node.world;

import de.cas_ual_ty.visibilis.node.Node;
import de.cas_ual_ty.visibilis.node.NodeType;
import de.cas_ual_ty.visibilis.node.field.Input;
import de.cas_ual_ty.visibilis.node.field.Output;
import de.cas_ual_ty.visibilis.print.provider.DataProvider;
import de.cas_ual_ty.visibilis.registries.VDataTypes;
import de.cas_ual_ty.visibilis.util.VUtility;
import net.minecraft.util.math.BlockPos;

public class NodeBlockPosCreate extends Node
{
    public final Output<BlockPos> out1BlockPos;
    public final Input<Integer> in1Integer;
    public final Input<Integer> in2Integer;
    public final Input<Integer> in3Integer;
    
    protected BlockPos value;
    
    public NodeBlockPosCreate(NodeType<?> type)
    {
        super(type);
        this.addOutput(this.out1BlockPos = new Output<>(this, VDataTypes.BLOCK_POS, "out1"));
        this.addInput(this.in1Integer = new Input<>(this, VDataTypes.INTEGER, "in1"));
        this.addInput(this.in2Integer = new Input<>(this, VDataTypes.INTEGER, "in2"));
        this.addInput(this.in3Integer = new Input<>(this, VDataTypes.INTEGER, "in3"));
    }
    
    @Override
    public boolean doCalculate(DataProvider context)
    {
        this.value = new BlockPos(this.in1Integer.getValue(), this.in2Integer.getValue(), this.in3Integer.getValue());
        return true;
    }
    
    @Override
    public <O> O getOutputValue(Output<O> out)
    {
        if(out == this.out1BlockPos)
        {
            return VUtility.cast(this.value);
        }
        
        return null;
    }
    
    @Override
    public float[] getColor()
    {
        return VDataTypes.BLOCK_POS.getColor();
    }
    
    @Override
    public float[] getTextColor()
    {
        return VDataTypes.BLOCK_POS.getTextColor();
    }
}
