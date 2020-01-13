package de.cas_ual_ty.visibilis.util;

import java.util.ArrayList;

public class VUtility
{
    @SuppressWarnings("unchecked")
    public static <A> A[] createGenericArray(int length)
    {
        return (A[])new Object[length];
    }
    
    @SuppressWarnings("unchecked")
    public static <A, B> A cast(B b)
    {
        return (A)b;
    }
    
    @SuppressWarnings("unchecked")
    public static <A> ArrayList<A> cloneArrayList(ArrayList<A> list)
    {
        return (ArrayList<A>)list.clone();
    }
}
