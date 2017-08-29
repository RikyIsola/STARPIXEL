package com.island.starpixel.blocchi.strumenti.proiettili;
import com.island.starpixel.*;
public class Siluro2 extends Proiettile
{
	public int potenza()
	{
		return 50;
	}
	public int immagine()
	{
		return R.drawable.siluro2;
	}
	public String nomeItaliano()
	{
		return"SILURO 2";
	}
	public String nomeInglese()
	{
		return"TORPEDO 2";
	}
	public double velocita()
	{
		return 0.1;
	}
	public int suono()
	{
		return R.raw.machinegun_fired;
	}
}
