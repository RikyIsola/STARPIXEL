package com.island.starpixel.blocchi.solidi;
import com.island.starpixel.*;

public class MuroPietra extends Solido
{
	public int vita()
	{
		return 100;
	}
	public int immagine()
	{
		return R.drawable.muropietra;
	}
	public String nomeItaliano()
	{
		return"MURO PIETRA";
	}
	public String nomeInglese()
	{
		return"STONE WALL";
	}
	public String descrizioneItaliano()
	{
		return"MURO RESISTENTE USATO PER COSTRUIRE CASE";
	}
	public String descrizioneInglese()
	{
		return"STRONG WALL USED TO BUILD HOUSE";
	}
}
