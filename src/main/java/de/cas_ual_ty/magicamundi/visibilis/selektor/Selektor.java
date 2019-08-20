package de.cas_ual_ty.magicamundi.visibilis.selektor;

import de.cas_ual_ty.magicamundi.visibilis.MMDataType;
import de.cas_ual_ty.magicamundi.visibilis.target.TargetsList;
import de.cas_ual_ty.visibilis.datatype.VDataType;
import de.cas_ual_ty.visibilis.node.Node;
import de.cas_ual_ty.visibilis.node.Output;

public abstract class Selektor extends Node
{
    public final Output outExec;
    public final Output<TargetsList> outTargetsList;

    public final TargetsList targetsList;

    public Selektor(int outputAmt, int inputAmt)
    {
        super(outputAmt, inputAmt);
        this.outExec = new Output(0, this, VDataType.EXEC, "exec");
        this.outTargetsList = new Output<TargetsList>(1, this, MMDataType.TARGETS_LIST, "targets_list");
        this.targetsList = new TargetsList();
    }

    public Selektor(int outputAmt)
    {
        this(outputAmt, 0);
    }

    public Selektor()
    {
        this(1);
    }

    @Override
    public boolean doCalculate()
    {
        this.targetsList.clear();
        return this.findTargets(this.targetsList);
    }

    /**
     * Find/select all targets and add them to the given TargetsList.
     * 
     * @param list
     *            The list to add all found/selected targets to.
     * @return <b>false</b> if there was an error and the process could not be done (example: input variable was undefined or it's value not allowed or out of range).
     */
    public abstract boolean findTargets(TargetsList list);

    @Override
    public <B> B getOutputValue(int index)
    {
        if (index == this.outTargetsList.id)
        {
            return (B) this.targetsList;
        }

        return null;
    }
}
