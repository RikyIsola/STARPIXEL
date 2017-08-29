package com.island.starpixel.blocchi.solidi;
import com.island.starpixel.blocchi.*;
import com.island.starpixel.blocchi.terreni.*;
public abstract class Solido extends Blocco
{
	public Terreno[]riproduzione(Blocchi b)
	{
		return new Terreno[0];
	}
	public int riproduzione()
	{
		return 0;
	}
	public boolean coperto()
	{
		return false;
	}
}
