package com.island.starpixel.blocchi.strumenti.lanciati;
import com.island.starpixel.*;
public class PallaDiNeve extends Lanciato
{
	public int proiettile()
	{
		return R.drawable.arconeve;
	}
	public boolean luminoso()
	{
		return false;
	}
	public int immagine()
	{
		return R.drawable.arconeve;
	}
	public String nomeItaliano()
	{
		return"PALLA DI NEVE";
	}
	public String nomeInglese()
	{
		return"SNOW BALL";
	}
	public String descrizioneItaliano()
	{
		return"PUOI LANCIARLO E GIOCARE CON I TUOI AMICI";
	}
	public String descrizioneInglese()
	{
		return"YOU CAN THROW IT AND PLAY WITH YOUR FRIENDS";
	}
	public int danno()
	{
		return 1;
	}
}
