package com.island.starpixel.blocchi.terreni;
import com.island.starpixel.*;
public class TerrenoLegno extends Terreno
{
	public int vita()
	{
		return 25;
	}
	public String descrizioneInglese()
	{
		return"TERRAIN USED TO BUILD HOUSES";
	}
	public String descrizioneItaliano()
	{
		return"USATO PER COSTRUIRE CASE";
	}
	public String nomeItaliano()
	{
		return"TERRENO LEGNO";
	}
	public String nomeInglese()
	{
		return"WOODEN FLOR";
	}
	public int immagine()
	{
		return R.drawable.terrenolegno;
	}
}
