package com.island.starpixel.blocchi.solidi.mobili.ai.intelligenti.combattivi;
import com.island.starpixel.*;
import com.island.starpixel.blocchi.*;
public class Golem extends Combattivo
{
	public int immagine()
	{
		return R.drawable.golem;
	}
	public String nomeItaliano()
	{
		return"GOLEM";
	}
	public String nomeInglese()
	{
		return"GOLEM";
	}
	public int vita()
	{
		return 1000;
	}
	public int team(Blocchi b)
	{
		return b.golem;
	}
	public int suono()
	{
		return R.raw.fireant_death;
	}
	public int potenza()
	{
		return 10;
	}
}
