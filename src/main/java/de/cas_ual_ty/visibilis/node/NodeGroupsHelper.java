package de.cas_ual_ty.visibilis.node;

import java.util.ArrayList;
import java.util.HashMap;

import net.minecraft.client.resources.I18n;
import net.minecraft.util.StringUtils;

public class NodeGroupsHelper
{
    public static final NodeGroupsHelper INSTANCE = new NodeGroupsHelper();
    
    private HashMap<NodeType<? extends Node>, ArrayList<String>> nodeToTags = new HashMap<>();
    
    private HashMap<NodeType<? extends Node>, String[]> nodeToGroups = new HashMap<>();
    private HashMap<String, String> groupToName = new HashMap<>();
    private HashMap<String, String[]> groupToTags = new HashMap<>();
    
    public boolean isTextMatchingNode(Node node, String text)
    {
        if(StringUtils.isNullOrEmpty(text))
        {
            return false;
        }
        
        text = text.toLowerCase();
        ArrayList<String> tags = this.getTagsForNode(node);
        
        for(String tag : tags)
        {
            if(tag.contains(text))
            {
                return true;
            }
        }
        
        return false;
    }
    
    public ArrayList<String> getTagsForNode(Node node)
    {
        NodeType<? extends Node> c = node.type;
        
        if(!this.nodeToTags.containsKey(c))
        {
            this.nodeToTags.put(c, this.createTagsForNode(node));
        }
        
        return this.getTagsForNode(c);
    }
    
    private ArrayList<String> getTagsForNode(NodeType<? extends Node> c)
    {
        return this.nodeToTags.get(c);
    }
    
    private ArrayList<String> createTagsForNode(Node node)
    {
        ArrayList<String> list = new ArrayList<>();
        
        String[] groups = this.getGroupsForNode(node);
        String groupId;
        
        String[] tags;
        String tag;
        
        int i;
        int j;
        
        for(i = 0; i < groups.length; ++i)
        {
            groupId = groups[i];
            tags = this.getTagsForGroup(groupId);
            
            for(j = 0; j < tags.length; ++j)
            {
                tag = tags[j];
                if(!list.contains(tag))
                {
                    list.add(tag);
                }
            }
        }
        
        tag = node.getID().trim().toLowerCase();
        if(!list.contains(tag))
        {
            list.add(tag);
        }
        
        tag = node.getName().trim().toLowerCase();
        if(!list.contains(tag))
        {
            list.add(tag);
        }
        
        return list;
    }
    
    public String[] getGroupsForNode(Node node)
    {
        NodeType<? extends Node> c = node.type;
        
        if(!this.nodeToGroups.containsKey(c))
        {
            this.nodeToGroups.put(c, NodeGroupsHelper.createGroupsForNode(node));
        }
        
        return this.getGroupsForNode(c);
    }
    
    private String[] getGroupsForNode(NodeType<? extends Node> c)
    {
        return this.nodeToGroups.get(c);
    }
    
    private static String[] createGroupsForNode(Node node)
    {
        return node.getGroup().trim().split(";");
    }
    
    public String[] getTagsForGroup(String groupId)
    {
        if(!this.groupToTags.containsKey(groupId))
        {
            this.groupToTags.put(groupId, NodeGroupsHelper.createTagsForGroup(groupId));
        }
        
        return this.groupToTags.get(groupId);
    }
    
    private static String[] createTagsForGroup(String groupId)
    {
        return I18n.format(NodeGroupsHelper.groupToTagsKey(groupId)).trim().toLowerCase().split(";");
    }
    
    public String getNameForGroup(String groupId)
    {
        if(!this.groupToName.containsKey(groupId))
        {
            this.groupToName.put(groupId, NodeGroupsHelper.createNameForGroup(groupId));
        }
        
        return groupId;
    }
    
    private static String createNameForGroup(String groupId)
    {
        return I18n.format(NodeGroupsHelper.groupToNameKey(groupId)).trim();
    }
    
    private static String groupToNameKey(String groupId)
    {
        return I18n.format("group." + groupId);
    }
    
    private static String groupToTagsKey(String groupId)
    {
        return I18n.format("group." + groupId + ".tags");
    }
}
