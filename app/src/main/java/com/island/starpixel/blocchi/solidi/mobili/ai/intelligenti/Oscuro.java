package com.island.starpixel.blocchi.solidi.mobili.ai.intelligenti;
import com.island.starpixel.*;
import com.island.starpixel.blocchi.*;
import com.island.starpixel.blocchi.strumenti.lanciati.*;
public class Oscuro extends Intelligente
{
	public int immagine()
	{
		return R.drawable.oscuri;
	}
	public String nomeItaliano()
	{
		return"OSCURO";
	}
	public String nomeInglese()
	{
		return"OSCURO";
	}
	public int vita()
	{
		return 200;
	}
	public int team(Blocchi b)
	{
		return b.oscuri;
	}
	public Lanciato arma(Blocchi b)
	{
		return b.Laser2;
	}
	public int suono()
	{
		return R.raw.grol_death;
	}
}
