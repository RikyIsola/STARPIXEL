package com.island.starpixel.blocchi.strumenti.lanciati;
import com.island.starpixel.*;
public class ArcoCactus extends Lanciato
{
	public boolean luminoso()
	{
		return false;
	}
	public int proiettile()
	{
		return R.drawable.freccia;
	}
	public int immagine()
	{
		return R.drawable.arcocactus;
	}
	public String nomeItaliano()
	{
		return"ARCO DI CACTUS";
	}
	public String nomeInglese()
	{
		return"CACTUS BOW";
	}
	public int danno()
	{
		return 3;
	}
}
