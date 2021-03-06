package de.cas_ual_ty.visibilis.proxy;

import java.util.function.Consumer;

import de.cas_ual_ty.visibilis.print.PrintScreen;
import de.cas_ual_ty.visibilis.print.provider.PrintProvider;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.world.World;

public class VClientProxy implements IVSidedProxy
{
    @Override
    public void openGuiForClient(PrintProvider helper)
    {
        Minecraft.getInstance().displayGuiScreen(new PrintScreen(new StringTextComponent("Print"), helper));
    }
    
    @Override
    public void doForClientPlayer(Consumer<PlayerEntity> consumer)
    {
        PlayerEntity p = Minecraft.getInstance().player;
        if(p != null)
        {
            consumer.accept(Minecraft.getInstance().player);
        }
    }
    
    @Override
    public World getClientWorld()
    {
        return Minecraft.getInstance().world;
    }
    
    @Override
    public PlayerEntity getClientPlayer()
    {
        return Minecraft.getInstance().player;
    }
}
