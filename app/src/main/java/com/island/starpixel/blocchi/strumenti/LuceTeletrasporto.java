package com.island.starpixel.blocchi.strumenti;
import com.island.starpixel.*;
public class LuceTeletrasporto extends Strumento
{
	public int immagine()
	{
		return R.drawable.luceteletrasporto;
	}
	public String nomeItaliano()
	{
		return"LUCE TELETRASPORTO";
	}
	public String nomeInglese()
	{
		return"TELEPORT LIGHT";
	}
	public String descrizioneItaliano()
	{
		return"QUANDO TU TELETRASPORTI QUALCOSA EMETTE UNA LUCE PER UN BREVE PERIODO DI TEMPO";
	}
	public String descrizioneInglese()
	{
		return"WHEN YOU TELEPORT SOMETHING IT EMIT A LIGHT FOR A SMALL TIME";
	}
	public int vita()
	{
		return 20;
	}
}
