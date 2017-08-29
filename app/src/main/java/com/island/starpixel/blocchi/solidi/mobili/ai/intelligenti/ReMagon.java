package com.island.starpixel.blocchi.solidi.mobili.ai.intelligenti;
import com.island.starpixel.*;
import com.island.starpixel.blocchi.*;
import com.island.starpixel.blocchi.strumenti.lanciati.*;
public class ReMagon extends Intelligente
{
	public Lanciato arma(Blocchi b)
	{
		return b.Laser1;
	}
	public int immagine()
	{
		return R.drawable.remagog;
	}
	public String nomeItaliano()
	{
		return"RE MAGON";
	}
	public String nomeInglese()
	{
		return"KING MAGON";
	}
	public int vita()
	{
		return 1000;
	}
	public int team(Blocchi b)
	{
		return b.magon;
	}
	public int suono()
	{
		return R.raw.heavy_ant_death;
	}
	public boolean boss()
	{
		return true;
	}
}
