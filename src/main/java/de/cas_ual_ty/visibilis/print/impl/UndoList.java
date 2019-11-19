package de.cas_ual_ty.visibilis.print.impl;

import java.util.LinkedList;

import de.cas_ual_ty.visibilis.print.Print;

public class UndoList
{
    private int index;
    public int max;
    
    private LinkedList<Print> list;
    
    public UndoList(int max)
    {
        this.index = -1;
        this.list = new LinkedList<Print>();
        this.max = max;
    }
    
    public UndoList()
    {
        this(25);
    }
    
    public UndoList saveChange(Print print)
    {
        return this.add(print.clone());
    }
    
    public UndoList add(Print print)
    {
        if (this.canRedo())
        {
            this.list.subList(++this.index, this.list.size()).clear();
        }
        else
        {
            ++this.index;
        }
        
        this.list.add(print);
        return this;
    }
    
    public Print getCurrent()
    {
        if (this.index == -1)
        {
            return null;
        }
        
        return this.list.get(this.index);
    }
    
    public boolean canUndo()
    {
        return this.index > 0;
    }
    
    public boolean canRedo()
    {
        return this.index < this.list.size() - 1;
    }
    
    public Print undo()
    {
        return this.list.get(--this.index);
    }
    
    public Print redo()
    {
        return this.list.get(++this.index);
    }
    
    public void cutToMax()
    {
        while (this.list.size() > this.max)
        {
            this.list.removeFirst();
        }
    }
    
    public int getIndex()
    {
        return this.index;
    }
}
