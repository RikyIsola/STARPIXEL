package com.island.starpixel.blocchi.strumenti.lanciati;
import com.island.starpixel.*;
public class ArcoFerro extends Lanciato
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
		return R.drawable.arcoferro;
	}
	public String nomeItaliano()
	{
		return"ARCO DI FERRO";
	}
	public String nomeInglese()
	{
		return"IRON BOW";
	}
	public int danno()
	{
		return 20;
	}
}
