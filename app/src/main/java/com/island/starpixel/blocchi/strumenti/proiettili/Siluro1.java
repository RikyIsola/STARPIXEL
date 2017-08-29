package com.island.starpixel.blocchi.strumenti.proiettili;
import com.island.starpixel.*;
public class Siluro1 extends Proiettile
{
	public int potenza()
	{
		return 30;
	}
	public int immagine()
	{
		return R.drawable.siluro1;
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
		return R.raw.laser_fired;
	}
}
