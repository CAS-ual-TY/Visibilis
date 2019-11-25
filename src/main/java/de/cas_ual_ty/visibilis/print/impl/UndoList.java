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
    
    /*
     * 0 1 2 3 4 5 6   idx6 size7
     * 
     * idx4
     * 
     * --- call: add(Print)
     * 
     * idx5
     * 
     * out: 5-7 (5-6 inkl.)
     * 
     * left:
     * 
     * 0 1 2 3 4
     */
    
    public UndoList add(Print print)
    {
        if (this.canRedo())
        {
            this.list.subList(++this.index, this.list.size()).clear();
        }
        else
        {
            this.index++;
        }
        
        this.list.add(this.list.size() - 1, print);
        return this;
    }
    
    public UndoList setFirst(Print print)
    {
        this.index = 0;
        this.list.clear();
        this.list.add(print);
        return this;
    }
    
    public Print getCurrent()
    {
        return this.list.get(this.index);
    }
    
    public boolean canUndo()
    {
        return this.index > 0;
    }
    
    public boolean canRedo()
    {
        return this.index + 1 < this.list.size();
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
    
    public int getSize()
    {
        return this.list.size();
    }
}
