package com.island.starpixel.blocchi.solidi;
import com.island.starpixel.*;
public class MuroErba extends Solido
{
	public int vita()
	{
		return 75;
	}
	public int immagine()
	{
		return R.drawable.muroerba;
	}
	public String nomeItaliano()
	{
		return"MURO ERBA";
	}
	public String nomeInglese()
	{
		return"GRASS WALL";
	}
	public String descrizioneItaliano()
	{
		return"MURO MEDIAMENTE RESISTENTE";
	}
	public String descrizioneInglese()
	{
		return"MEDIUM STRONG WALL";
	}
}
