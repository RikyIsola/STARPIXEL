package com.island.starpixel.blocchi.strumenti.lanciati;
import com.island.starpixel.*;
public class Laser1 extends Lanciato
{
	public int proiettile()
	{
		return R.drawable.laser1;
	}
	public int immagine()
	{
		return R.drawable.arma1;
	}
	public String nomeItaliano()
	{
		return"LASER";
	}
	public String nomeInglese()
	{
		return"LASER";
	}
	public int danno()
	{
		return 50;
	}
}
