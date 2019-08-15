package de.cas_ual_ty.magicamundi.visibilis.target;

import java.util.ArrayList;

public class TargetsList extends ArrayList<Target>
{
	//TODO Players, Blocks, Entities etc.
	//public ArrayList<Target> getAllPlayers
	
	@Override
	public TargetsList clone()
	{
		return (TargetsList) super.clone();
	}
}
