package com.island.starpixel.blocchi.solidi.mobili.ai.intelligenti.combattivi;
import com.island.starpixel.*;
import com.island.starpixel.blocchi.*;
public class OscuroReale extends Combattivo
{
	public int immagine()
	{
		return R.drawable.oscuri;
	}
	public String nomeItaliano()
	{
		return"OSCURO DISARMATO";
	}
	public String nomeInglese()
	{
		return"DISARMED OSCURO";
	}
	public int vita()
	{
		return 200;
	}
	public int team(Blocchi b)
	{
		return b.oscuri;
	}
	public int suono()
	{
		return R.raw.grol_death;
	}
}
