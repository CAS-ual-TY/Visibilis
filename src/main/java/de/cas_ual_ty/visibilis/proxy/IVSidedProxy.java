package de.cas_ual_ty.visibilis.proxy;

import java.util.function.Consumer;

import de.cas_ual_ty.visibilis.print.PrintProvider;
import net.minecraft.entity.player.PlayerEntity;

public interface IVSidedProxy
{
    public default void openGuiPrint(PrintProvider helper)
    {
    }
    
    public default void doForClientPlayer(Consumer<PlayerEntity> consumer)
    {
    }
}
