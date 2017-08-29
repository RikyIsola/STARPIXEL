package com.island.starpixel.blocchi.solidi.liquidi;
import com.island.starpixel.blocchi.solidi.*;
import com.island.starpixel.blocchi.*;
import com.island.starpixel.blocchi.terreni.*;
public abstract class Liquido extends Solido
{
	public Terreno[]riproduzione(Blocchi b)
	{
		return null;
	}
	public int riproduzione()
	{
		return 7000;
	}
	public String descrizioneItaliano()
	{
		return"PUOI DEPURARLA E BERLA";
	}
	public String descrizioneInglese()
	{
		return"YOU CAN DEPURE AND DRINK IT";
	}
	public boolean coperto()
	{
		return true;
	}
}
