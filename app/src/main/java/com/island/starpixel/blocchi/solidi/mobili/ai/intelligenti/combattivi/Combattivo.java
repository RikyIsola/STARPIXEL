package com.island.starpixel.blocchi.solidi.mobili.ai.intelligenti.combattivi;
import com.island.starpixel.blocchi.*;
import com.island.starpixel.blocchi.solidi.mobili.ai.intelligenti.*;
import com.island.starpixel.blocchi.strumenti.lanciati.*;
public abstract class Combattivo extends Intelligente
{
	public Lanciato arma(Blocchi b)
	{
		return null;
	}
	public String descrizioneItaliano()
	{
		return"È UN TUO NEMICO E NON USA PISTOLE";
	}
	public String descrizioneInglese()
	{
		return"HE IS ONE OF YOUR ENEMY AND HE DOESN'T USE GUNS";
	}
	public int potenza()
	{
		return 1;
	}
}
