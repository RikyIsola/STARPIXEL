package com.island.starpixel.blocchi.solidi.mobili.ai.intelligenti;
import com.island.starpixel.blocchi.solidi.mobili.ai.*;
import com.island.starpixel.blocchi.*;
import com.island.starpixel.blocchi.strumenti.lanciati.*;
public abstract class Intelligente extends Ai
{
	public abstract int team(Blocchi b)
	public abstract Lanciato arma(Blocchi b)
	public int suono()
	{
		return 0;
	}
	public double velocita()
	{
		return 0.1;
	}
	public String descrizioneItaliano()
	{
		return"È UN TUO NEMICO";
	}
	public String descrizioneInglese()
	{
		return"HE IS ONE OF YOUR ENEMY";
	}
	public boolean boss()
	{
		return false;
	}
}
