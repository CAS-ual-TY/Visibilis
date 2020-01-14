package de.cas_ual_ty.visibilis.util;

import java.util.ArrayList;

public class VUtility
{
    @SuppressWarnings("unchecked")
    public static <A, B> A cast(B b)
    {
        return (A)b;
    }
    
    public static <A> A[] createGenericArray(int length)
    {
        return VUtility.cast(new Object[length]);
    }
    
    public static <A> ArrayList<A> cloneArrayList(ArrayList<A> list)
    {
        return VUtility.cast(list.clone());
    }
}
