package de.cas_ual_ty.visibilis.proxy;

import java.util.function.Consumer;

import de.cas_ual_ty.visibilis.print.provider.PrintProvider;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.world.World;

public interface IVSidedProxy
{
    public default void openGuiPrint(PrintProvider helper)
    {
    }
    
    public default void doForClientPlayer(Consumer<PlayerEntity> consumer)
    {
    }
    
    public default World getClientWorld()
    {
        return null;
    }
    
    public default PlayerEntity getClientPlayer()
    {
        return null;
    }
}
