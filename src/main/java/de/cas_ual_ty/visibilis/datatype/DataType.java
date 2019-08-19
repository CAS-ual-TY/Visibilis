package de.cas_ual_ty.visibilis.datatype;

public class DataType<A>
{
    /*
     * This (classes instead of just Strings) is the planned solution for data types. Additionally there will be an external handler for conversions (eg. float -> int) and and
     * external handler for Inputs accepting different Output types (again, eg float -> int). All external so nothing is hardcoded.
     */

    public final String id;

    public DataType(String id)
    {
        this.id = id;
    }

    /**
     * Get the color of this data type, for connections and node field dots
     * 
     * @return
     */
    public float[] getColor()
    {
        return new float[] { 1F, 1F, 1F };
    }
}
