package com.island.starpixel.blocchi.strumenti.lanciati;
import com.island.starpixel.*;
import com.island.starpixel.blocchi.strumenti.*;
public abstract class Lanciato extends Strumento
{
	public abstract int danno();
	public abstract int proiettile();
	public boolean luminoso()
	{
		return true;
	}
	public double velocita()
	{
		return 0.5;
	}
	public int suono()
	{
		return R.raw.laser1;
	}
	public String descrizioneItaliano()
	{
		return"PUOI SPARARLO CON UN'ARMA";
	}
	public String descrizioneInglese()
	{
		return"YOU CAN FIRE IT WITH A GUN";
	}
}
