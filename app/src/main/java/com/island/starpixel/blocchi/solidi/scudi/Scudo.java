package com.island.starpixel.blocchi.solidi.scudi;
import com.island.starpixel.blocchi.*;
import com.island.starpixel.blocchi.solidi.*;
import com.island.starpixel.blocchi.strumenti.scudi.*;
public abstract class Scudo extends Solido
{
	public abstract Scudi colore(Blocchi b);
	public boolean coperto()
	{
		return true;
	}
}
