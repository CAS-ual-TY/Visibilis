package de.cas_ual_ty.visibilis.util;

public class VUtility
{
    @SuppressWarnings("unchecked")
    public static <A> A[] createGenericArray(int length)
    {
        return (A[]) new Object[length];
    }
}
