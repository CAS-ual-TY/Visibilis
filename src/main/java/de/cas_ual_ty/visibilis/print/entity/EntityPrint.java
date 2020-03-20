package de.cas_ual_ty.visibilis.print.entity;

import de.cas_ual_ty.visibilis.print.GuiPrint;
import de.cas_ual_ty.visibilis.print.Print;
import de.cas_ual_ty.visibilis.print.capability.CapabilityProviderPrint;
import de.cas_ual_ty.visibilis.print.provider.DataProvider;
import de.cas_ual_ty.visibilis.print.provider.NodeListProvider;
import de.cas_ual_ty.visibilis.print.provider.NodeListProviderBase;
import de.cas_ual_ty.visibilis.print.provider.PrintProvider;
import de.cas_ual_ty.visibilis.util.VUtility;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.Hand;
import net.minecraft.world.World;

public abstract class EntityPrint extends Entity
{
    public EntityPrint(EntityType<?> entityTypeIn, World worldIn)
    {
        super(entityTypeIn, worldIn);
    }
    
    /**
     * Opens the {@link GuiPrint} for the given player
     * 
     * @param player
     *            The player to open the Gui for
     * @return <b>true</b> if the Gui was opened
     */
    public boolean openGui(PlayerEntity player)
    {
        if(player.world.isRemote && player.world == this.world) // Make sure they are in the same dimension, synching fetches entity from same world player is in. Need custom PrintProvider otherwise which takes different dimensions into account
        {
            VUtility.openGuiForClient(this.getPrintProvider());
            return true;
        }
        
        return false;
    }
    
    /**
     * Returns an instance of {@link PrintProvider} which is used to open the {@link de.cas_ual_ty.visibilis.print.GuiPrint} in {@link #openGui(PlayerEntity, ItemStack, Hand)}
     */
    public PrintProvider getPrintProvider()
    {
        return new PrintProviderEntity(this.getNodeList(), this);
    }
    
    public NodeListProvider getNodeList()
    {
        return new NodeListProviderBase();
    }
    
    public Print getPrint()
    {
        return this.getCapability(CapabilityProviderPrint.CAPABILITY_PRINT).orElseThrow(() -> new IllegalArgumentException("LazyOptional must not be empty (5)!"));
    }
    
    public void setPrintTag(CompoundNBT nbt)
    {
        Print print = this.getPrint();
        print.overrideFromNBT(nbt);
    }
    
    public DataProvider createDataProvider()
    {
        return new DataProviderEntity(this);
    }
    
    public boolean executeEvent(String modId, String event)
    {
        return this.getPrint().executeEvent(modId, event, this.createDataProvider());
    }
    
    public boolean validate(Print print)
    {
        return true;
    }
}
