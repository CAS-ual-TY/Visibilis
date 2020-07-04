package de.cas_ual_ty.visibilis.node.function;

import de.cas_ual_ty.visibilis.Visibilis;
import de.cas_ual_ty.visibilis.datatype.DataType;
import de.cas_ual_ty.visibilis.node.Node;
import de.cas_ual_ty.visibilis.node.field.Output;
import de.cas_ual_ty.visibilis.print.Print;
import de.cas_ual_ty.visibilis.print.provider.DataProvider;
import de.cas_ual_ty.visibilis.registries.VNodeTypes;
import de.cas_ual_ty.visibilis.util.VNBTUtility;
import de.cas_ual_ty.visibilis.util.VUtility;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.ListNBT;
import net.minecraft.nbt.StringNBT;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.util.Constants;

public class FunctionPrint extends Print
{
    public static final String KEY_OUTPUTS = "outputs";
    public static final String KEY_INPUTS = "inputs";
    public static final String KEY_IN_DATA_TYPES = "inDataTypes";
    public static final String KEY_OUT_DATA_TYPES = "outDataTypes";
    
    public FunctionNode functionNode;
    
    public FunctionEndNode nodeOutputs;
    public FunctionStartNode nodeInputs;
    
    public boolean outputsChanged;
    public boolean inputsChanged;
    
    public FunctionPrint(FunctionNode functionNode)
    {
        this.functionNode = functionNode;
        this.nodeOutputs = (FunctionEndNode)new FunctionEndNode(VNodeTypes.FUNCTION_END).linkParentPrint(this);
        this.nodeInputs = (FunctionStartNode)new FunctionStartNode(VNodeTypes.FUNCTION_START).linkParentPrint(this);
        
        this.outputsChanged = false;
        this.inputsChanged = false;
    }
    
    public boolean exec(DataProvider context, int execInput)
    {
        Output<?> out = this.nodeInputs.getOutput(execInput);
        
        if(!out.hasConnections())
        {
            return true;
        }
        
        Node n = out.getConnections().get(0).getNode();
        return this.exec(n, context);
    }
    
    public boolean calculate(DataProvider context)
    {
        return this.nodeOutputs.calculate(context);
    }
    
    public void addOutput(DataType<?> type)
    {
        if(!this.outputsChanged)
        {
            this.outputsChanged = true;
        }
        this.nodeOutputs.addField(type);
    }
    
    public void addInput(DataType<?> type)
    {
        if(!this.inputsChanged)
        {
            this.inputsChanged = true;
        }
        this.nodeInputs.addField(type);
    }
    
    public void removeOutput(int index)
    {
        if(!this.outputsChanged)
        {
            this.outputsChanged = true;
        }
        this.nodeOutputs.removeField(index);
    }
    
    public void removeInput(int index)
    {
        if(!this.inputsChanged)
        {
            this.inputsChanged = true;
        }
        this.nodeInputs.removeField(index);
    }
    
    public void updateFunctionNodeFields()
    {
        if(this.outputsChanged)
        {
            this.functionNode.clearOutputs();
            
            for(int i = 0; i < this.nodeOutputs.getSize(); ++i)
            {
                this.functionNode.addOutput(this.nodeOutputs.getField(i));
            }
        }
        
        if(this.inputsChanged)
        {
            this.functionNode.clearInputs();
            
            for(int i = 0; i < this.nodeInputs.getSize(); ++i)
            {
                this.functionNode.addInput(this.nodeInputs.getField(i));
            }
        }
    }
    
    public <O> O getOutputValue(int index)
    {
        return VUtility.cast(this.nodeOutputs.getOutputValue(this.nodeOutputs.getOutput(index)));
    }
    
    public <I> I getInputValue(int index)
    {
        return VUtility.cast(this.functionNode.getInputValue(this.functionNode.getInput(index)));
    }
    
    @Override
    public void readFromNBT(CompoundNBT nbt0, boolean readVariables)
    {
        this.removeNode(this.nodeOutputs);
        this.removeNode(this.nodeInputs);
        
        VNBTUtility.readNodeFromNBT(this.nodeOutputs, nbt0.getCompound(FunctionPrint.KEY_OUTPUTS));
        VNBTUtility.readNodeFromNBT(this.nodeInputs, nbt0.getCompound(FunctionPrint.KEY_INPUTS));
        
        super.readFromNBT(nbt0, readVariables);
        
        ListNBT list = nbt0.getList(FunctionPrint.KEY_OUT_DATA_TYPES, Constants.NBT.TAG_STRING);
        for(int i = 0; i < list.size(); ++i)
        {
            this.addOutput(Visibilis.DATA_TYPES_REGISTRY.getValue(new ResourceLocation(list.getString(i))));
        }
        
        list = nbt0.getList(FunctionPrint.KEY_IN_DATA_TYPES, Constants.NBT.TAG_STRING);
        for(int i = 0; i < list.size(); ++i)
        {
            this.addInput(Visibilis.DATA_TYPES_REGISTRY.getValue(new ResourceLocation(list.getString(i))));
        }
        
        this.addNode(this.nodeOutputs);
        this.addNode(this.nodeInputs);
    }
    
    @Override
    public void writeToNBT(CompoundNBT nbt0, boolean writeVariables)
    {
        this.removeNode(this.nodeOutputs);
        this.removeNode(this.nodeInputs);
        
        nbt0.put(FunctionPrint.KEY_OUTPUTS, VNBTUtility.saveNodeToNBT(this.nodeOutputs));
        nbt0.put(FunctionPrint.KEY_INPUTS, VNBTUtility.saveNodeToNBT(this.nodeInputs));
        
        super.writeToNBT(nbt0, writeVariables);
        
        ListNBT list = new ListNBT();
        for(int i = 0; i < this.nodeOutputs.getSize(); ++i)
        {
            list.add(StringNBT.valueOf(this.nodeOutputs.getField(i).getRegistryName().toString()));
        }
        nbt0.put(FunctionPrint.KEY_OUT_DATA_TYPES, list);
        
        list = new ListNBT();
        for(int i = 0; i < this.nodeInputs.getSize(); ++i)
        {
            list.add(StringNBT.valueOf(this.nodeInputs.getField(i).getRegistryName().toString()));
        }
        nbt0.put(FunctionPrint.KEY_IN_DATA_TYPES, list);
        
        this.addNode(this.nodeOutputs);
        this.addNode(this.nodeInputs);
    }
    
    @Override
    public Print clone()
    {
        FunctionPrint p = new FunctionPrint(this.functionNode);
        VNBTUtility.readPrintFromNBT(p, VNBTUtility.savePrintToNBT(this, true), true);
        return p;
    }
}
