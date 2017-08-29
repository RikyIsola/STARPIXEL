package com.island.starpixel.blocchi.solidi;
import com.island.starpixel.*;

public class BancaDati extends Solido
{
	public int immagine()
	{
		return R.drawable.bancadati;
	}
	public String nomeItaliano()
	{
		return"BANCA DATI";
	}
	public String nomeInglese()
	{
		return"DATA BANK";
	}
	public String descrizioneItaliano()
	{
		return"TOCCALO PER VEDERE TUTTI I SISTEMI CHE HAI GIA ESPLORATO";
	}
	public String descrizioneInglese()
	{
		return"TOUCH IT TO SEE ALL THE SYSTEM YOU HAVE ALREADY EXPLORED";
	}
	public int vita()
	{
		return 30;
	}
}
