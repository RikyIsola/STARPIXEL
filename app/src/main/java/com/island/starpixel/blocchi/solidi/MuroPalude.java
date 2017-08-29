package com.island.starpixel.blocchi.solidi;
import com.island.starpixel.*;
public class MuroPalude extends Solido
{
	public int vita()
	{
		return 75;
	}
	public int immagine()
	{
		return R.drawable.muropalude;
	}
	public String nomeItaliano()
	{
		return"MURO PALUDE";
	}
	public String nomeInglese()
	{
		return"SWAMP WALL";
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
