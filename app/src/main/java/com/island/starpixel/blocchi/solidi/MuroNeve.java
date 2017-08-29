package com.island.starpixel.blocchi.solidi;
import com.island.starpixel.*;
public class MuroNeve extends Solido
{
	public int vita()
	{
		return 25;
	}
	public int immagine()
	{
		return R.drawable.muroneve;
	}
	public String nomeItaliano()
	{
		return"MURO NEVE";
	}
	public String nomeInglese()
	{
		return"SNOW WALL";
	}
	public String descrizioneItaliano()
	{
		return"MURO MOLTO FRAGILE USATO PER COSTRUIRE IGLOO";
	}
	public String descrizioneInglese()
	{
		return"VERY WEAK WALL USED TO BUILD IGLOO";
	}
}
