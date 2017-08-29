package com.island.starpixel.blocchi.solidi;
import com.island.starpixel.*;
public class Diamante extends Solido
{
	public int immagine()
	{
		return R.drawable.diamante;
	}
	public String nomeItaliano()
	{
		return"DIAMANTE";
	}
	public String nomeInglese()
	{
		return"DIAMONT";
	}
	public String descrizioneItaliano()
	{
		return"PIAZZANE NOVE IN UQUADRATO 3X3 PER CREARE IL GENERATORE DI BUCHI NERI";
	}
	public String descrizioneInglese()
	{
		return"PLACE 9 OF IT IN A 3X3 SQUARE TO CREATE THE BLACK HOLE GENERATOR";
	}
	public int vita()
	{
		return 100;
	}
}
