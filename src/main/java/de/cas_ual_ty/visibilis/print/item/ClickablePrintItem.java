package de.cas_ual_ty.visibilis.print.item;

import de.cas_ual_ty.visibilis.Visibilis;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Hand;
import net.minecraft.world.World;

public class ClickablePrintItem extends PrintItem
{
    /*
     * Item is editable via the "v edit" command (fires event)
     */
    
    public ClickablePrintItem(Properties properties)
    {
        super(properties);
    }
    
    @Override
    public ActionResult<ItemStack> onItemRightClick(World worldIn, PlayerEntity playerIn, Hand handIn)
    {
        ItemStack itemstack = playerIn.getHeldItem(handIn);
        
        if(worldIn.isRemote)
        {
            return new ActionResult<>(ActionResultType.PASS, itemstack);
        }
        
        if(this.executeRightClickEvent(worldIn, playerIn, handIn))
        {
            return new ActionResult<>(ActionResultType.SUCCESS, itemstack);
        }
        else
        {
            return new ActionResult<>(ActionResultType.FAIL, itemstack);
        }
    }
    
    public boolean executeRightClickEvent(World worldIn, PlayerEntity playerIn, Hand handIn)
    {
        return this.executeEvent(Visibilis.MOD_ID, "right_click", playerIn, playerIn.getHeldItem(handIn));
    }
}
