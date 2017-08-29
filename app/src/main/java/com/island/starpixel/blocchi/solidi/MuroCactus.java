package com.island.starpixel.blocchi.solidi;
import com.island.starpixel.*;
public class MuroCactus extends Solido
{
	public int vita()
	{
		return 5;
	}
	public int immagine()
	{
		return R.drawable.murocactus;
	}
	public String nomeItaliano()
	{
		return"MURO CACTUS";
	}
	public String nomeInglese()
	{
		return"CACTUS WALL";
	}
	public String descrizioneItaliano()
	{
		return"MURO DEBOLE";
	}
	public String descrizioneInglese()
	{
		return"WEAK WALL";
	}
}
