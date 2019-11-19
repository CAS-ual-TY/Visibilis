package de.cas_ual_ty.visibilis.test;

import de.cas_ual_ty.visibilis.print.impl.IPrintProvider;
import de.cas_ual_ty.visibilis.print.impl.item.ItemPrint;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Hand;
import net.minecraft.world.World;

public class VItemTest extends ItemPrint
{
    public VItemTest(Properties properties)
    {
        super(properties);
    }
    
    @Override
    public ActionResult<ItemStack> onItemRightClick(World worldIn, PlayerEntity playerIn, Hand handIn)
    {
        if (this.openGui(playerIn, handIn))
        {
            return new ActionResult<>(ActionResultType.SUCCESS, playerIn.getHeldItem(handIn));
        }
        
        return new ActionResult<>(ActionResultType.PASS, playerIn.getHeldItem(handIn));
    }
    
    @Override
    public IPrintProvider getHelper(ItemStack itemStack, Hand hand)
    {
        return new VPrintProviderTest(itemStack, hand);
    }
}
