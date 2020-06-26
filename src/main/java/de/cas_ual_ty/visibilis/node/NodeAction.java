package de.cas_ual_ty.visibilis.node;

public abstract class NodeAction
{
    public static final String LANG_DELETE = "visibilis.action.delete";
    public static final String LANG_EXPAND = "visibilis.action.expand";
    public static final String LANG_SHRINK = "visibilis.action.shrink";
    public static final String LANG_PARALLELIZE = "visibilis.action.parallelize";
    public static final String LANG_UNPARALLELIZE = "visibilis.action.unparallelize";
    public static final String LANG_DYNAMIC = "visibilis.action.dynamic";
    public static final String LANG_STATIC = "visibilis.action.static";
    
    public final Node node;
    public final String text;
    
    /**
     * @param text The name of this action. By default the UI will try to translate,
     * so using lang keys is recommended here (but not required, as long as the value is not en existing lang key).
     */
    public NodeAction(Node node, String text)
    {
        this.node = node;
        this.text = text;
    }
    
    /**
     * What to do after right clicking the node.
     * @return <b>true</b> if something has been done successfully.
     */
    public abstract boolean clicked();
}
