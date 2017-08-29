package com.island.starpixel.blocchi.solidi;
import com.island.starpixel.*;
public class MuroLegno extends Solido
{
	public int vita()
	{
		return 25;
	}
	public int immagine()
	{
		return R.drawable.murolegno;
	}
	public String nomeItaliano()
	{
		return"MURO LEGNO";
	}
	public String nomeInglese()
	{
		return"WOODEN WALL";
	}
	public String descrizioneItaliano()
	{
		return"MURO USATO PER COSTRUIRE CASE";
	}
	public String descrizioneInglese()
	{
		return"WALL USED TO BUILD HOUSES";
	}
}
