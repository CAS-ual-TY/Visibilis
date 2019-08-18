package de.cas_ual_ty.visibilis;

/**
 * This will be gone! Just using this temporarily.
 * @see de.cas_ual_ty.visibilis.datatype.DataType
 */
@Deprecated
public enum EnumVDataType
{
	/*
	 * <s>Might remove this all together some day and just replace with strings in node fields.
	 * Might also change all floats to the Number class.</s>
	 */
	EXEC("exec", (byte)255, (byte)255, (byte)255),
	FLOAT("float", (byte)255, (byte)127, (byte)0),
	INTEGER("integer", (byte)127, (byte)255, (byte)0),
	BOOLEAN("boolean", (byte)255, (byte)127, (byte)127);
	
	public final String dataTypeString;
	public final float r;
	public final float g;
	public final float b;
	
	private EnumVDataType(String s, byte r, byte g, byte b)
	{
		this.dataTypeString = s;
		this.r = (r) / 255F;
		this.g = (g) / 255F;
		this.b = (b) / 255F;
	}
}
