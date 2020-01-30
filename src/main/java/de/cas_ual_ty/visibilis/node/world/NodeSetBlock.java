package de.cas_ual_ty.visibilis.node.world;

import com.mojang.brigadier.StringReader;
import com.mojang.brigadier.exceptions.CommandSyntaxException;

import de.cas_ual_ty.visibilis.Visibilis;
import de.cas_ual_ty.visibilis.node.ExecContext;
import de.cas_ual_ty.visibilis.node.Node;
import de.cas_ual_ty.visibilis.node.NodeType;
import de.cas_ual_ty.visibilis.node.field.Input;
import de.cas_ual_ty.visibilis.node.field.Output;
import de.cas_ual_ty.visibilis.registries.VDataTypes;
import net.minecraft.block.BlockState;
import net.minecraft.command.arguments.BlockStateParser;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class NodeSetBlock extends Node
{
    public Output<Object> out1Exec;
    public Input<Object> in1Exec;
    public Input<World> in2World;
    public Input<BlockPos> in3BlockPos;
    public Input<String> in4String;
    
    protected World value;
    
    public NodeSetBlock(NodeType<?> type)
    {
        super(type);
        this.addOutput(this.out1Exec = new Output<>(this, VDataTypes.EXEC, "out1"));
        this.addInput(this.in1Exec = new Input<>(this, VDataTypes.EXEC, "in1"));
        this.addInput(this.in2World = new Input<>(this, VDataTypes.WORLD, "in2"));
        this.addInput(this.in3BlockPos = new Input<>(this, VDataTypes.BLOCK_POS, "in3"));
        this.addInput(this.in4String = new Input<>(this, VDataTypes.STRING, "in4"));
    }
    
    @Override
    public boolean doCalculate(ExecContext context)
    {
        BlockStateParser parser = new BlockStateParser(new StringReader(this.in4String.getValue()), true);
        
        try
        {
            parser.parse(true);
            
            BlockState state = parser.getState();
            
            Visibilis.debug(state.toString());
            
            if(state != null)
            {
                this.in2World.getValue().setBlockState(this.in3BlockPos.getValue(), state);
            }
        }
        catch (CommandSyntaxException e)
        {
            
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
        return VDataTypes.WORLD.getColor();
    }
    
    @Override
    public float[] getTextColor()
    {
        return VDataTypes.WORLD.getTextColor();
    }
}
