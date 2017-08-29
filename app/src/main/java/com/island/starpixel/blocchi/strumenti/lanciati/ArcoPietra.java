package com.island.starpixel.blocchi.strumenti.lanciati;
import com.island.starpixel.*;
public class ArcoPietra extends Lanciato
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
		return R.drawable.arcopietra;
	}
	public String nomeItaliano()
	{
		return"ARCO DI PIETRA";
	}
	public String nomeInglese()
	{
		return"STONE BOW";
	}
	public int danno()
	{
		return 10;
	}
}
