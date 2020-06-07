package de.cas_ual_ty.visibilis.print.capability;

import java.util.function.Consumer;

import de.cas_ual_ty.visibilis.print.Print;

public class PrintHolder implements IPrintHolder
{
    protected Print print;
    
    public PrintHolder()
    {
        this(new Print());
    }
    
    public PrintHolder(Print print)
    {
        this.print = print;
    }
    
    public PrintHolder doForPrint(Consumer<Print> consumer)
    {
        consumer.accept(this.getPrint());
        return this;
    }
    
    @Override
    public Print getPrint()
    {
        return this.print;
    }
    
    @Override
    public void setPrint(Print print)
    {
        this.print = print;
    }
}
