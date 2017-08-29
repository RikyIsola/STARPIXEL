package com.island.starpixel.blocchi.solidi;
import com.island.starpixel.*;
public class MuroSabbia extends Solido
{
	public int vita()
	{
		return 50;
	}
	public int immagine()
	{
		return R.drawable.murosabbia;
	}
	public String nomeItaliano()
	{
		return"MURO SABBIA";
	}
	public String nomeInglese()
	{
		return"SAND WALL";
	}
	public String descrizioneItaliano()
	{
		return"MURO MOLTO FRAGILE";
	}
	public String descrizioneInglese()
	{
		return"VERY WEAK WALL";
	}
}
