package de.cas_ual_ty.visibilis;

public enum EnumVDataType
{
	/*
	 * Might remove this all together some day and just replace with strings in node fields.
	 * Might also change all floats to the Number class.
	 */
	EXEC("exec"), FLOAT("float"), INTEGER("integer"), BOOLEAN("boolean");
	
	public final String dataTypeString;
	
	private EnumVDataType(String s)
	{
		this.dataTypeString = s;
	}
}
