package com.island.starpixel.blocchi.solidi.mobili.ai.intelligenti;
import com.island.starpixel.*;
import com.island.starpixel.blocchi.*;
import com.island.starpixel.blocchi.strumenti.lanciati.*;
public class Magon extends Intelligente
{
	public Lanciato arma(Blocchi b)
	{
		return b.Laser1;
	}
	public int immagine()
	{
		return R.drawable.magog;
	}
	public String nomeItaliano()
	{
		return"MAGON";
	}
	public String nomeInglese()
	{
		return"MAGON";
	}
	public int vita()
	{
		return 100;
	}
	public int team(Blocchi b)
	{
		return b.magon;
	}
	public int suono()
	{
		return R.raw.alien_antorian_death;
	}
}
