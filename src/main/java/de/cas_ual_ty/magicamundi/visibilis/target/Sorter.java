package de.cas_ual_ty.magicamundi.visibilis.target;

import de.cas_ual_ty.magicamundi.visibilis.EnumMMDataType;
import de.cas_ual_ty.visibilis.EnumVDataType;
import de.cas_ual_ty.visibilis.node.Input;
import de.cas_ual_ty.visibilis.node.NodeExec;
import de.cas_ual_ty.visibilis.node.Output;

public abstract class Sorter extends NodeExec
{
    public final Output outExec;
    public final Output<TargetsList> outTargetsList1;
    public final Output<TargetsList> outTargetsList2;
    public final Input inExec;
    public final Input<TargetsList> inTargetsList;

    public TargetsList targetsList1;
    public final TargetsList targetsList2;

    public Sorter(int outputAmt, int inputAmt)
    {
        super(outputAmt, inputAmt);
        this.outExec = new Output(0, this, EnumVDataType.EXEC.dataTypeString, "exec");
        this.outTargetsList1 = new Output<TargetsList>(1, this, EnumMMDataType.TARGETS_LIST.dataTypeString, "targets_list");
        this.outTargetsList2 = new Output<TargetsList>(2, this, EnumMMDataType.TARGETS_LIST.dataTypeString, "targets_list");
        this.inExec = new Input(0, this, EnumVDataType.EXEC.dataTypeString, "exec");
        this.inTargetsList = new Input<TargetsList>(1, this, EnumMMDataType.TARGETS_LIST.dataTypeString, "targets_list");
        this.targetsList2 = new TargetsList();
    }

    public Sorter(int inputAmt)
    {
        this(3, inputAmt);
    }

    public Sorter()
    {
        this(2);
    }

    @Override
    public boolean doCalculate()
    {
        this.targetsList2.clear();
        this.targetsList1 = this.inTargetsList.getValue().clone();
        return this.sortOut(this.targetsList1, this.targetsList2);
    }

    /**
     * Sort out the targets from one list and add them to the other.
     * 
     * @param list1
     *            The TargetsList instance containing all Target instances to remove false targets from.
     * @param list2
     *            The TargetsList instance to add all removed targets to.
     * @return <b>false</b> if there was an error and the process could not be done (example: input variable was undefined or it's value not allowed or out of range).
     */
    public abstract boolean sortOut(TargetsList list1, TargetsList list2);

    @Override
    public <B> B getOutputValue(int index)
    {
        if (index == this.outTargetsList1.id)
        {
            return (B) this.targetsList1;
        }
        else if (index == this.outTargetsList2.id)
        {
            return (B) this.targetsList2;
        }

        return null;
    }

    @Override
    public Output getOutExec(int index)
    {
        return index == 0 ? this.outExec : null;
    }
}
