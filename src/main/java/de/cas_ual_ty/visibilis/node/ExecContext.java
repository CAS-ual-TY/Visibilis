package de.cas_ual_ty.visibilis.node;

import de.cas_ual_ty.visibilis.node.dataprovider.DataProvider;
import de.cas_ual_ty.visibilis.print.Print;

public class ExecContext
{
    public final Print print;
    public final DataProvider data;
    
    public ExecContext(Print print, DataProvider data)
    {
        this.print = print;
        this.data = data;
    }
    
    public Print getPrint()
    {
        return this.print;
    }
    
    public DataProvider getData()
    {
        return this.data;
    }
}
