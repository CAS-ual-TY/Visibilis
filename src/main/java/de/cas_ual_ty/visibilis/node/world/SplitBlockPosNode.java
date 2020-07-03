package de.cas_ual_ty.visibilis.node.world;

import de.cas_ual_ty.visibilis.node.Node;
import de.cas_ual_ty.visibilis.node.NodeType;
import de.cas_ual_ty.visibilis.node.field.Input;
import de.cas_ual_ty.visibilis.node.field.Output;
import de.cas_ual_ty.visibilis.print.provider.DataProvider;
import de.cas_ual_ty.visibilis.registries.VDataTypes;
import de.cas_ual_ty.visibilis.util.VUtility;
import net.minecraft.util.math.BlockPos;

public class SplitBlockPosNode extends Node
{
    public final Output<Integer> out1Integer;
    public final Output<Integer> out2Integer;
    public final Output<Integer> out3Integer;
    public final Input<BlockPos> in1BlockPos;
    
    protected double x, y, z;
    
    public SplitBlockPosNode(NodeType<?> type)
    {
        super(type);
        this.addOutput(this.out1Integer = new Output<>(this, VDataTypes.INTEGER, "out1"));
        this.addOutput(this.out2Integer = new Output<>(this, VDataTypes.INTEGER, "out2"));
        this.addOutput(this.out3Integer = new Output<>(this, VDataTypes.INTEGER, "out3"));
        this.addInput(this.in1BlockPos = new Input<>(this, VDataTypes.BLOCK_POS, "in1"));
    }
    
    @Override
    public boolean doCalculate(DataProvider context)
    {
        this.x = this.in1BlockPos.getValue().getX();
        this.y = this.in1BlockPos.getValue().getY();
        this.z = this.in1BlockPos.getValue().getZ();
        return true;
    }
    
    @Override
    public <O> O getOutputValue(Output<O> out)
    {
        if(out == this.out1Integer)
        {
            return VUtility.cast(this.x);
        }
        if(out == this.out2Integer)
        {
            return VUtility.cast(this.y);
        }
        if(out == this.out3Integer)
        {
            return VUtility.cast(this.z);
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
