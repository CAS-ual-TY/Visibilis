package de.cas_ual_ty.visibilis.node.function;

import de.cas_ual_ty.visibilis.print.provider.NodeListProvider;
import de.cas_ual_ty.visibilis.print.provider.PrintProvider;

public class FunctionPrintProvider extends PrintProvider
{
    public FunctionNode node;
    
    public FunctionPrintProvider(FunctionNode node, NodeListProvider nodeListProvider)
    {
        super(nodeListProvider);
        this.node = node;
    }
    
    @Override
    public void init()
    {
        this.undoList.setFirst(this.node.functionPrint);
        super.init();
    }
    
    @Override
    public void onGuiClose()
    {
        this.node.functionPrint = (FunctionPrint)this.undoList.getCurrent();
        this.node.functionPrint.updateFunctionNodeFields();
        super.onGuiClose();
    }
}
