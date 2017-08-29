package com.island.starpixel.blocchi.strumenti.lanciati;
import com.island.starpixel.*;
public class ArcoLegno extends Lanciato
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
		return R.drawable.arcolegno;
	}
	public String nomeItaliano()
	{
		return"ARCO DI LEGNO";
	}
	public String nomeInglese()
	{
		return"WOODEN BOW";
	}
	public int danno()
	{
		return 5;
	}
}
