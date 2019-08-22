package de.cas_ual_ty.visibilis.test;

import de.cas_ual_ty.visibilis.print.IPrintHelper;
import de.cas_ual_ty.visibilis.print.ItemPrint;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.world.World;

public class VItemTest extends ItemPrint
{
    public VItemTest()
    {
        this.setCreativeTab(CreativeTabs.COMBAT);
    }
    
    @Override
    public ActionResult<ItemStack> onItemRightClick(World worldIn, EntityPlayer playerIn, EnumHand handIn)
    {
        if (worldIn.isRemote)
        {
            this.openGui(playerIn, handIn);
            return new ActionResult<ItemStack>(EnumActionResult.SUCCESS, playerIn.getHeldItem(handIn));
        }
        
        return new ActionResult<ItemStack>(EnumActionResult.PASS, playerIn.getHeldItem(handIn));
    }
    
    @Override
    public IPrintHelper getHelper(ItemStack itemStack, EnumHand hand)
    {
        return new VPrintHelperTest(itemStack, hand);
    }
}
