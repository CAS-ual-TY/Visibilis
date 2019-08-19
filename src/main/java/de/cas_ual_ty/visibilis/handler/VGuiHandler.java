package de.cas_ual_ty.visibilis.handler;

import de.cas_ual_ty.visibilis.GuiPrint;
import de.cas_ual_ty.visibilis.test.PrintTest;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.network.IGuiHandler;

public class VGuiHandler implements IGuiHandler
{
    public static final int ID_DEFAULT_GUI = 0;

    @Override
    public Object getServerGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z)
    {
        return null;
    }

    @Override
    public Object getClientGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z)
    {
        switch (ID)
        {
        case ID_DEFAULT_GUI:
            return new GuiPrint(PrintTest.printTest);
        }

        return null;
    }
}
